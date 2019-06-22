package triangle.little.potatoes.presentation.view.activity;

import triangle.little.potatoes.data.net.protocol.game.GetIndexlbtResp;
import triangle.little.potatoes.data.net.protocol.user.NoticeByIdResp;
import triangle.little.potatoes.data.net.protocol.user.NoticeResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * Created by dell on 2017/4/15.
 */

interface ActivityCenterContract {
    interface CollaborateActivityPresenter extends BasePresenter{
        void getActivities(int type);
    }
    interface CollaborateActivityView extends BaseView<CollaborateActivityPresenter>{
        void getActivitiesSuccess(List<NoticeResp.DataBean.PagerBean.ListBean> respList);
        void getActivitiesFailure();
    }

    interface OfficialActivityPresenter extends BasePresenter{
        void getActivities(int pageNo);
    }
    interface OfficialActivityView extends BaseView<OfficialActivityPresenter>{
        void getActivitiesSuccess(List<GetIndexlbtResp.DataBean.ListBean> respList,int totalPages);
        void getActivitiesFailure();
    }

    interface ActivityDetailsPresenter extends BasePresenter{
        void getActivityDetailsInfo(int activityId);
    }

    interface ActivityDetailsView extends BaseView<ActivityCenterContract.ActivityDetailsPresenter>{
        void getActivityDetailsInfoSuccess(NoticeByIdResp.DataBean.NoticeBean noticeBean);
        void getActivityDetailsInfoFail(String msg);
    }
}
