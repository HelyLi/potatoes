package triangle.little.potatoes.presentation.view.agent;

import triangle.little.potatoes.data.net.protocol.proxy.RechargeByUnderAgentsDetailResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public interface PayAgentContract {

    interface Presenter extends BasePresenter {


        void getRechargeByUnderAgentsDetail(String flag, int pageNo);

    }

    interface View extends BaseView<Presenter> {

        void getRechargeByUnderAgentsDetailSuccess(List<RechargeByUnderAgentsDetailResp.DataBean> list);

        void getRechargeByUnderAgentsDetailFailure(String msg);
    }

}
