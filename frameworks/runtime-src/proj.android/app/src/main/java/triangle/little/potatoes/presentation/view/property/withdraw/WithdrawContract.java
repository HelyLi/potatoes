package triangle.little.potatoes.presentation.view.property.withdraw;

import triangle.little.potatoes.data.net.protocol.user.BankResp;
import triangle.little.potatoes.data.net.protocol.user.GetWithdraWalResp;
import triangle.little.potatoes.data.net.protocol.user.PriceResp;
import triangle.little.potatoes.data.net.protocol.user.WithdrawalResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/5/2
 */

public interface WithdrawContract {

    interface Presenter extends BasePresenter {
        void getBank();

        void getPriceData();

        void getWithdraWal();

        void addWithdrawal(Double withdrawalPrice, String pwd);
    }

    interface View extends BaseView<Presenter> {
        void getBankSuccess(List<BankResp.DataBeanX.DataBean> list);

        void getBankFailure(String msg);

        void getPriceSuccess(List<PriceResp.DataBeanX.DataBean> list);

        void getPriceFailure(String msg);

        void getWithdraWalSuccess(GetWithdraWalResp.CurrentWithdrawalConfBean bean);

        void getWithdraWalFailure(String msg);

        void addWithdrawalSuccess(WithdrawalResp.DataBean dataBean);

        void addWithdrawalFailure(String msg);

        void findWithdraWalFailure(String msg);

        void getPayPwdFailure(String msg);
    }

}
