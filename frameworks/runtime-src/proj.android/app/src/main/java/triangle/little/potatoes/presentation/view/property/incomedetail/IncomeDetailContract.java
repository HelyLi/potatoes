package triangle.little.potatoes.presentation.view.property.incomedetail;

import triangle.little.potatoes.data.net.protocol.user.AccPriceResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * description :
 * Created by Flynn
 * 2017/4/25
 */

public interface IncomeDetailContract {

    interface Presenter extends BasePresenter {

        void getAccPrice(String startDate, String endDate);
    }

    interface View extends BaseView<Presenter> {

        void getAccPriceSuccess(AccPriceResp.DataBeanX dataBean);

        void getAccPriceFailure(String msg);
    }

}
