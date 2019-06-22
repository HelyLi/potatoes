package triangle.little.potatoes.presentation.view.me.setting;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.LogoutReq;
import triangle.little.potatoes.data.net.protocol.user.LogoutResp;
import triangle.little.potatoes.data.net.protocol.user.PayPwdReq;
import triangle.little.potatoes.data.net.protocol.user.PayPwdResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * 账户设置presenterImpl
 * Created by dell on 2017/4/27.
 */

public class AccountSettingPresenterImpl implements AccountSettingContract.AccountSettingPresenter {
    private AccountSettingContract.AccountSettingView mView;
    private UserApi mUserApi;

    public AccountSettingPresenterImpl(AccountSettingContract.AccountSettingView view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getPayPsw() {
        PayPwdReq req = new PayPwdReq();
        mUserApi.getPayPwd(req.params())
                .compose(RxUtil.<PayPwdResp>io_main())
                .compose(mView.<PayPwdResp>bindToLife())
                .subscribe(new BaseSubscriber<PayPwdResp>() {
                    @Override
                    public void onNext(PayPwdResp resp) {
                        if (resp != null && resp.isOk() && resp.getData() != null && resp.getData().getData() != null) {
                            PayPwdResp.DataBeanX.DataBean dataBean = resp.getData().getData().get(0);
                            if (dataBean.getPASSWORD() != null && !"null".equals(dataBean.getPASSWORD())) {
                                mView.getPayPswSuccess(true);
                            } else {
                                mView.getPayPswSuccess(false);
                            }
                        } else {
                            mView.getPayPswFail(resp != null ? resp.getMsg() : mView.getContext().getString(R.string.partner_request_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:" + t.getMessage());
                        mView.getPayPswFail(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }

    @Override
    public void exit() {
        LogoutReq req = new LogoutReq();
        mUserApi.logout(req.params())
                .compose(RxUtil.<LogoutResp>io_main())
                .compose(mView.<LogoutResp>bindToLife())
                .subscribe(new BaseSubscriber<LogoutResp>() {
                    @Override
                    public void onNext(LogoutResp resp) {
                        if (resp != null && resp.isOk()) {
                            mView.exitSuccess(mView.getContext().getString(R.string.partner_personal_account_exit_success));
                        } else {
                            mView.exitFailure(mView.getContext().getString(R.string.partner_personal_account_exit_fail));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:" + t.getMessage());
                        mView.exitFailure(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
