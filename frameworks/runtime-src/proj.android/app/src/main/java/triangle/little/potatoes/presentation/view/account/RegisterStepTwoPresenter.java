package triangle.little.potatoes.presentation.view.account;

import android.text.TextUtils;
import android.widget.TextView;
import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.CheckPartnerNoReq;
import triangle.little.potatoes.data.net.protocol.user.CheckPartnerNoResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/5/25
 */

public class RegisterStepTwoPresenter implements RegisterStepTwoContract.Presenter {

    private final UserApi mUserApi;
    private RegisterStepTwoContract.View mView;

    public RegisterStepTwoPresenter(RegisterStepTwoContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void checkPartnerNo(final String pwd, final String partnerNo) {
        CheckPartnerNoReq req = new CheckPartnerNoReq();
        req.setPartnerNo(partnerNo);
        mUserApi.checkPartnerNo(req.params())
                .compose(RxUtil.<CheckPartnerNoResp>io_main())
                .compose(mView.<CheckPartnerNoResp>bindToLife())
                .subscribe(new BaseSubscriber<CheckPartnerNoResp>() {
                    @Override
                    public void onNext(CheckPartnerNoResp resp) {
                        if (resp.isOk()
                                && resp.getData() != null
                                && resp.getData().getData() != null
                                && resp.getData().getData().size() > 0
                                && !TextUtils.isEmpty(resp.getData().getData().get(0).getPARTNER_NO())) {
                            mView.checkPartnerNoSuccess(pwd, partnerNo);
                        } else {
                            mView.checkPartnerNoFailure(mView.getContext().getString(R.string.register_partner_no_err));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        mView.checkPartnerNoFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }
}
