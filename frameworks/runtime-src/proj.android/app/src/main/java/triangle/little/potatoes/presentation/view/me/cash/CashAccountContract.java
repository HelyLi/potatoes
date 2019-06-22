package triangle.little.potatoes.presentation.view.me.cash;

import triangle.little.potatoes.data.net.protocol.user.BankResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * Created by dell on 2017/4/17.
 */

public interface CashAccountContract {
    interface Presenter extends BasePresenter{
        void getBankCardInfo();
        void checkAccountIsAuthed();
    }
    interface View extends BaseView<Presenter>{
        void getBankCardInfoSuccess(List<BankResp.DataBeanX.DataBean> beanList);
        void getBankCardInfoFailure();

        void onCheckIsAuthedSuccess(boolean isAuthed);
        void onCheckIsAuthedFailure(String msg);
    }
}
