package triangle.little.potatoes.presentation.view.home;

import triangle.little.potatoes.data.net.protocol.proxy.IndexDataResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * description :
 * Created by Flynn
 * 2017/4/14
 */

public interface HomeContract {

    interface Presenter extends BasePresenter {

        void getIndexData();
    }

    interface View extends BaseView<Presenter> {
        void getIndexDataSuccess(IndexDataResp.DataBean dataBean);

        void getIndexDataFailure(String msg);
    }

}
