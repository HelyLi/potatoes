package triangle.little.potatoes.presentation.view.property.transaction;

import triangle.little.potatoes.data.net.protocol.user.WithdrawalInfoResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public interface TransactionContract {

    interface Presenter extends BasePresenter {
        void getWithdrawalInfo();
    }

    interface View extends BaseView<Presenter> {
        void getWithdrawalInfoSuccess(WithdrawalInfoResp.DataBeanX data);

        void getWithdrawalInfoFailure(String msg);
    }

}
