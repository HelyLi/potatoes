package triangle.little.potatoes.presentation.view.account;


import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * Created by dell on 2017/4/13.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter{
        void login(String account,String password);
        void thirdLogin(String accessToken, int type, String appId);
    }
    interface View extends BaseView<Presenter>{
        void doLoginSuccess();
        void doLoginFail(String msg);
    }
}
