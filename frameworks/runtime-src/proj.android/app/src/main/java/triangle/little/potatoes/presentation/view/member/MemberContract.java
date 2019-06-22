package triangle.little.potatoes.presentation.view.member;

import triangle.little.potatoes.data.net.protocol.proxy.GetMembersRechargeSumResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public interface MemberContract {

    interface Presenter extends BasePresenter {

        void getMembersRechargeSum(int pageNo);

        void getMemberDaySum(int pageNo);

        void getMemberWeekSum(int pageNo);

        void getMemberMonthSum(int pageNo);

        void getRechargeByMemberDetail(String flag, int pageNo);

    }

    interface View extends BaseView<Presenter> {

        void getMembersRechargeSumSuccess(List<GetMembersRechargeSumResp.DataBean> list);

        void getMembersRechargeSumFailure(String msg);
    }

}
