package triangle.little.potatoes.presentation.view.property.details;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.AccPriceInfoReq;
import triangle.little.potatoes.data.net.protocol.user.AccPriceInfoResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/4/27
 */

public class IncomeDetailsPresenter implements IncomeDetailsContract.Presenter {

    private final UserApi mUserApi;
    private IncomeDetailsContract.View mView;

    public IncomeDetailsPresenter(IncomeDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getAccPriceInfo(int type, String date) {
        AccPriceInfoReq req = new AccPriceInfoReq();
        req.setStart(date);
        req.setType(type);
        mUserApi.getAccPriceInfo(req.params())
                .compose(mView.<AccPriceInfoResp>bindToLife())
                .compose(RxUtil.<AccPriceInfoResp>io_main())
                .subscribe(new BaseSubscriber<AccPriceInfoResp>() {
                    @Override
                    public void onNext(AccPriceInfoResp resp) {

                        if (resp.isOk()) {
                            mView.getAccPriceInfoSuccess(resp.getData().getData());
                        } else {
                            mView.getAccPriceInfoFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getAccPriceInfoFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }
}
