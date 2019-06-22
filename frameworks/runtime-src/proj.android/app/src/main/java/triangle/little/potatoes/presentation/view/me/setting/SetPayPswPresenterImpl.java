package triangle.little.potatoes.presentation.view.me.setting;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.ResetPayPwdReq;
import triangle.little.potatoes.data.net.protocol.user.UpdatePayPwdResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.MD5;
import triangle.little.potatoes.presentation.utils.RxUtil;

/**
 * 设置支付密码presenterImpl
 * Created by dell on 2017/4/20.
 */

public class SetPayPswPresenterImpl implements AccountSettingContract.SetPayPswPresenter {
    private AccountSettingContract.SetPayPswView mView;
    private UserApi mUserApi;

    public SetPayPswPresenterImpl(AccountSettingContract.SetPayPswView view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void setPayPsw(String payPsw) {
        ResetPayPwdReq resetPayPwdReq = new ResetPayPwdReq();
        resetPayPwdReq.setPwd(new MD5(payPsw).getMd5_32());
        mUserApi.updatePayPwd(resetPayPwdReq.params())
                .compose(RxUtil.<UpdatePayPwdResp>io_main())
                .compose(mView.<UpdatePayPwdResp>bindToLife())
                .subscribe(new BaseSubscriber<UpdatePayPwdResp>() {
                    @Override
                    public void onNext(UpdatePayPwdResp resp) {
                        if (resp != null && resp.isOk()) {
                            mView.setPayPswSuccess(mView.getContext().getString(R.string.partner_set_success));
                        } else {
                            mView.setPayPswSuccess(mView.getContext().getString(R.string.partner_set_failure));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.setPayPswFailure(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
