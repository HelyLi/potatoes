package triangle.little.potatoes.presentation.view.me.cash;

import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * Created by dell on 2017/4/18.
 */

interface AddBankCardContract {
    interface Presenter extends BasePresenter {
        void getRealName();
        void getAuthCode(String phone);
        void addBankCard(String bankName, String bankNo,String name,String authCode,String mobile);
    }

    interface View extends BaseView<Presenter> {
        void getRealNameSuccess(String msg);
        void getRealNameFailure(String msg);
        void getAuthCodeSuccess(String msg);
        void getAuthCodeFailure(String msg);
        void addBankCardSuccess(String msg);
        void addBankCardFailure(String msg);
    }
}
