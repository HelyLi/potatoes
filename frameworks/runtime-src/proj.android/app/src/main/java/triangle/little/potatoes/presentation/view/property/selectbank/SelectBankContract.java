package triangle.little.potatoes.presentation.view.property.selectbank;

import triangle.little.potatoes.data.net.protocol.user.BankResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/5/2
 */

public interface SelectBankContract {
    interface Presenter extends BasePresenter {
        void getBank();
    }

    interface View extends BaseView<Presenter> {
        void getBankSuccess(List<BankResp.DataBeanX.DataBean> list);

        void getBankFailure(String msg);
    }
}
