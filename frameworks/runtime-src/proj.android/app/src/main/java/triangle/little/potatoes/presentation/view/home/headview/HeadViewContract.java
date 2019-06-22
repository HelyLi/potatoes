package triangle.little.potatoes.presentation.view.home.headview;

import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * description :
 * Created by Flynn
 * 2017/4/15
 */

public class HeadViewContract {

    interface Presenter extends BasePresenter {
        void getIndexData();
    }

    interface View extends BaseView<Presenter> {
        void getIndexDataSuccess();

        void getIndexDataFailure(String msg);
    }

}
