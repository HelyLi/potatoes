package triangle.little.potatoes.presentation.view.product;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.GameApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.game.GameDataInfoReq;
import triangle.little.potatoes.data.net.protocol.game.GameDataInfoResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * 产品详情presenterImpl
 * Created by dell on 2017/4/25.
 */

public class ProductDetailsPresenterImpl implements ProductContract.DetailsPresenter {
    private ProductContract.DetailsView mView;
    private GameApi mGameApi;

    public ProductDetailsPresenterImpl(ProductContract.DetailsView view) {
        mView = view;
        mView.setPresenter(this);
        mGameApi = RetrofitManager.getInstance(ApiType.GAME_API).getGameApi();
    }

    @Override
    public void getProductDetailsInfo(int gameId) {
        GameDataInfoReq req = new GameDataInfoReq();
        req.setGameId(gameId);
        mGameApi.getGameDataInfo(req.params())
                .compose(RxUtil.<GameDataInfoResp>io_main())
                .compose(mView.<GameDataInfoResp>bindToLife())
                .subscribe(new BaseSubscriber<GameDataInfoResp>() {
                    @Override
                    public void onNext(GameDataInfoResp resp) {
                        if (resp!=null&&resp.isOk()&&resp.getData()!=null){
                            mView.getProductDetailsInfoSuccess(resp.getData());
                        }else {
                            mView.getProductDetailsInfoFailure(resp!=null?resp.getMsg():mView.getContext().getString(R.string.partner_request_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:" + t.getMessage());
                        mView.getProductDetailsInfoFailure(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
