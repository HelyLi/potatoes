package triangle.little.potatoes.presentation.view.agent;

import triangle.little.potatoes.data.net.protocol.proxy.MyProxyDataResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/15
 */

public interface AgentContract {

    interface Presenter extends BasePresenter {
        void getMyProxyData(int orderBy,int pageNo);
    }

    interface View extends BaseView<Presenter> {
        void getMyProxyDataSuccess(List<MyProxyDataResp.DataBean> mData);

        void getMyProxyDataFailure(String msg);
    }

}
