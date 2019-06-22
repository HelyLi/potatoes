package triangle.little.potatoes.presentation.view.account;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.LoginReq;
import triangle.little.potatoes.data.net.protocol.user.LoginResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.MD5;
import triangle.little.potatoes.presentation.utils.RxUtil;
import triangle.little.potatoes.presentation.utils.UserPrefsUtil;
import com.orhanobut.logger.Logger;

/**
 * login
 * Created by dell on 2017/4/13.
 */

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View mView;
    private UserApi mUserApi;

    public LoginPresenterImpl(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void login(final String account, final String password) {
        final LoginReq loginReq = new LoginReq();
        loginReq.setAccount(account);
        loginReq.setPassword(new MD5(password).getMd5_32());
        mUserApi.login(loginReq.params())
                .compose(RxUtil.<LoginResp>io_main())
                .compose(mView.<LoginResp>bindToLife())
                .subscribe(new BaseSubscriber<LoginResp>() {

                    @Override
                    public void onNext(LoginResp loginResp) {
                        if (loginResp != null && loginResp.isOk()) {
                            mView.doLoginSuccess();
                            UserPrefsUtil.getInstance().saveUserNamePwd(mView.getContext(), account, password);
                        } else {
                            mView.doLoginFail(loginResp != null ? loginResp.getMsg() : mView.getContext().getString(R.string.partner_request_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.doLoginFail(mView.getContext().getString(R.string.partner_request_error));
                    }
                });

    }

    @Override
    public void thirdLogin(String accessToken, int type, String appId) {

    }
}
