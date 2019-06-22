package triangle.little.potatoes.presentation.view.activity;

import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.NoticeReq;
import triangle.little.potatoes.data.net.protocol.user.NoticeResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;

/**
 * 合作活动presenterImpl
 * Created by dell on 2017/4/15.
 */

public class CollaborateActivityPresenterImpl implements ActivityCenterContract.CollaborateActivityPresenter {
    private UserApi mUserApi;
    private ActivityCenterContract.CollaborateActivityView mView;

    public CollaborateActivityPresenterImpl(ActivityCenterContract.CollaborateActivityView view) {
        mView = view;
        view.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getActivities(int type) {
        final NoticeReq noticeReq = new NoticeReq();
        noticeReq.setType(type);
        mUserApi.getNotice(noticeReq.params())
                .compose(RxUtil.<NoticeResp>io_main())
                .compose(mView.<NoticeResp>bindToLife())
                .subscribe(new BaseSubscriber<NoticeResp>() {
                    @Override
                    public void onNext(NoticeResp noticeResp) {
                        if (noticeResp != null && noticeResp.isOk() && noticeResp.getData() != null
                                && noticeResp.getData().getPager() != null
                                && noticeResp.getData().getPager().getList() != null) {
                            mView.getActivitiesSuccess(noticeResp.getData().getPager().getList());
                        } else {
                            mView.getActivitiesFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.getActivitiesFailure();
                    }
                });
    }
}
