package triangle.little.potatoes.presentation.view.activity;

import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.GameApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.game.GetIndexlbtReq;
import triangle.little.potatoes.data.net.protocol.game.GetIndexlbtResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * 官方活动presenterImpl
 * Created by dell on 2017/4/24.
 */

public class OfficialActivityPresenterImpl implements ActivityCenterContract.OfficialActivityPresenter {
    private ActivityCenterContract.OfficialActivityView mView;
    private GameApi mGameApi;

    public OfficialActivityPresenterImpl(ActivityCenterContract.OfficialActivityView view) {
        mView = view;
        mView.setPresenter(this);
        mGameApi = RetrofitManager.getInstance(ApiType.GAME_API).getGameApi();
    }

    @Override
    public void getActivities(int pageNo) {
        GetIndexlbtReq getIndexlbtReq = new GetIndexlbtReq();
        getIndexlbtReq.setPageNo(pageNo);
        getIndexlbtReq.setTerminal(2);
        mGameApi.getIndexlbt(getIndexlbtReq.params())
                .compose(RxUtil.<GetIndexlbtResp>io_main())
                .compose(mView.<GetIndexlbtResp>bindToLife())
                .subscribe(new BaseSubscriber<GetIndexlbtResp>() {
                    @Override
                    public void onNext(GetIndexlbtResp resp) {
                        if (resp != null && resp.getData() != null && resp.getData().getList() != null) {
                            mView.getActivitiesSuccess(resp.getData().getList(),resp.getData().getTotalPages());
                        } else {
                            mView.getActivitiesFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:"+t.getMessage());
                        mView.getActivitiesFailure();
                    }
                });
    }
}
