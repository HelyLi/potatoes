package triangle.little.potatoes.presentation.view.account;

import triangle.little.potatoes.presentation.view.BaseLoadingView;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * Created by dell on 2017/4/13.
 */

public interface RegisterContract {
    interface SmsCodePresenter extends BasePresenter {
        /**
         * 请求验证码
         *
         * @param phone
         */
        void requestVerificationCode(String phone);

        /**
         * 确认验证码是否正确
         *
         * @param phone
         * @param authCode
         */
        void confirmAuthCode(String phone, String authCode);
    }

    interface SmsCodeView extends BaseView<SmsCodePresenter>, BaseLoadingView {
        void onGetCodeSuccess(String msg);

        void onGetCodeFailure(String msg);

        void onCheckCodeSuccess(String msg);

        void onCheckCodeFailure(String msg);
    }

}
