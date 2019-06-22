package triangle.little.potatoes.presentation.view.property;

import triangle.little.potatoes.data.net.protocol.user.PriceResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public interface MyPropertyContract {

    interface Presenter extends BasePresenter {

        void getPriceData();

        void withdraw();

    }

    interface View extends BaseView<Presenter> {

        void getPriceSuccess(List<PriceResp.DataBeanX.DataBean> list);

        void getPriceFailure(String msg);

        void findIdCardFailure();

        void getPayPwdFailure();

        void withdrawSuccess();

        void withdrawFailure(String msg);
    }

}
