package triangle.little.potatoes.presentation.view.extension;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.GameApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.game.AddBannerReq;
import triangle.little.potatoes.data.net.protocol.game.AddBannerResp;
import triangle.little.potatoes.data.net.protocol.game.GetBannerListReq;
import triangle.little.potatoes.data.net.protocol.game.GetBannerListResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * 自定义推广添加banner presenterImpl
 * Created by dell on 2017/5/6.
 */

public class AddBannerPresenterImpl implements ExtensionContract.AddBannerPresenter {
    private ExtensionContract.AddBannerView mView;
    private GameApi mGameApi;

    public AddBannerPresenterImpl(ExtensionContract.AddBannerView view) {
        mView = view;
        mView.setPresenter(this);
        mGameApi = RetrofitManager.getInstance(ApiType.GAME_API).getGameApi();
    }

    @Override
    public void getBannerList(int page) {
        GetBannerListReq req = new GetBannerListReq();
        req.setPage(page);
        mGameApi.getBannerList(req.params())
                .compose(RxUtil.<GetBannerListResp>io_main())
                .compose(mView.<GetBannerListResp>bindToLife())
                .subscribe(new BaseSubscriber<GetBannerListResp>() {
                    @Override
                    public void onNext(GetBannerListResp resp) {
                        if (resp != null && resp.isOk() && resp.getData() != null && resp.getData().getData() != null) {
                            mView.getBannerListSuccess(resp.getData().getData());
                        } else {
                            mView.getBannerListFail(resp != null ? resp.getMsg() :
                                    mView.getContext().getString(R.string.partner_request_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:" + t.getMessage());
                        mView.getBannerListFail(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }

    @Override
    public void addBanner(String gameId) {
        AddBannerReq req = new AddBannerReq();
        req.setGameId(gameId);
        // TODO: 2017/5/6  android typeId=?
        req.setTypeId(1);
        mGameApi.addBanner(req.params())
                .compose(RxUtil.<AddBannerResp>io_main())
                .compose(mView.<AddBannerResp>bindToLife())
                .subscribe(new BaseSubscriber<AddBannerResp>() {
                    @Override
                    public void onNext(AddBannerResp resp) {
                        if (resp != null && resp.isOk()) {
                            mView.addBannerSuccess();
                        } else {
                            mView.addBannerFail(resp != null ? resp.getMsg() :
                                    mView.getContext().getString(R.string.partner_request_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:" + t.getMessage());
                        mView.addBannerFail(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
