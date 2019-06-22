package triangle.little.potatoes.presentation.view.property.transaction;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.WithdrawalInfoReq;
import triangle.little.potatoes.data.net.protocol.user.WithdrawalInfoResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public class TransactionPresenter implements TransactionContract.Presenter {

    private final UserApi mUserApi;
    private TransactionContract.View mView;

    public TransactionPresenter(TransactionContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getWithdrawalInfo() {
        final WithdrawalInfoReq req = new WithdrawalInfoReq();
        mUserApi.getWithdrawalInfo(req.params())
                .compose(mView.<WithdrawalInfoResp>bindToLife())
                .compose(RxUtil.<WithdrawalInfoResp>io_main())
                .subscribe(new BaseSubscriber<WithdrawalInfoResp>() {
                    @Override
                    public void onNext(WithdrawalInfoResp resp) {
                        if (resp.isOk()) {
                            mView.getWithdrawalInfoSuccess(resp.getData());
                        } else {
                            mView.getWithdrawalInfoFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getWithdrawalInfoFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }
}
