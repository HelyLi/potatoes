package triangle.little.potatoes.presentation.view.property.details;

import triangle.little.potatoes.data.net.protocol.user.AccPriceInfoResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/27
 */

public interface IncomeDetailsContract {

    interface Presenter extends BasePresenter {
        void getAccPriceInfo(int type, String date);
    }

    interface View extends BaseView<Presenter> {
        void getAccPriceInfoSuccess(List<AccPriceInfoResp.DataBeanX.DataBean> dataBean);

        void getAccPriceInfoFailure(String msg);
    }
}
