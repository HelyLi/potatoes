package triangle.little.potatoes.presentation.view.activity;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.NoticeByIdReq;
import triangle.little.potatoes.data.net.protocol.user.NoticeByIdResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * 活动详情presenter
 * Created by dell on 2017/4/25.
 */

public class ActivityDetailsPresenterImpl implements ActivityCenterContract.ActivityDetailsPresenter {
    private ActivityCenterContract.ActivityDetailsView mView;
    private UserApi mUserApi;

    public ActivityDetailsPresenterImpl(ActivityCenterContract.ActivityDetailsView view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getActivityDetailsInfo(int activityId) {
        NoticeByIdReq noticeByIdReq = new NoticeByIdReq();
        noticeByIdReq.setId(activityId);
        mUserApi.getNoticeById(noticeByIdReq.params())
                .compose(RxUtil.<NoticeByIdResp>io_main())
                .compose(mView.<NoticeByIdResp>bindToLife())
                .subscribe(new BaseSubscriber<NoticeByIdResp>() {
                    @Override
                    public void onNext(NoticeByIdResp resp) {
                        if (resp != null && resp.isOk() && resp.getData() != null && resp.getData().getNotice() != null) {
                            mView.getActivityDetailsInfoSuccess(resp.getData().getNotice());
                        } else {
                            mView.getActivityDetailsInfoFail(resp.getData() != null ? resp.getData().getMsgX() : "");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.d("onError:" + t.getMessage());
                        mView.getActivityDetailsInfoFail(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
