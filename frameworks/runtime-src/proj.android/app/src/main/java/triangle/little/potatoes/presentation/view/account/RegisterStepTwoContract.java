package triangle.little.potatoes.presentation.view.account;

import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * description :
 * Created by Flynn
 * 2017/5/25
 */

public interface RegisterStepTwoContract {

    interface Presenter extends BasePresenter {
        void checkPartnerNo(String pwd ,String partnerNo);
    }

    interface View extends BaseView<Presenter> {
        void checkPartnerNoSuccess(String pwd, String partnerNo);

        void checkPartnerNoFailure(String msg);
    }

}
