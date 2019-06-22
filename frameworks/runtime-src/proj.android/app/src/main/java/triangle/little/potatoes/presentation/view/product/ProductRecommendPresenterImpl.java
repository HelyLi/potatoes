package triangle.little.potatoes.presentation.view.product;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.GameApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.game.GameDataReq;
import triangle.little.potatoes.data.net.protocol.game.GameDataResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;

/**
 * Created by dell on 2017/4/21.
 */

public class ProductRecommendPresenterImpl implements ProductContract.Presenter {
    private ProductContract.RecommendView mView;
    private GameApi mGameApi;

    public ProductRecommendPresenterImpl(ProductContract.RecommendView view) {
        mView = view;
        mView.setPresenter(this);
        mGameApi = RetrofitManager.getInstance(ApiType.GAME_API).getGameApi();
    }

    @Override
    public void getGameData(int gameType) {
        GameDataReq gameDataReq = new GameDataReq();
        gameDataReq.setPlatType(2);
        gameDataReq.setGameType(gameType);
        mGameApi.getGameData(gameDataReq.params())
                .compose(RxUtil.<GameDataResp>io_main())
                .compose(mView.<GameDataResp>bindToLife())
                .subscribe(new BaseSubscriber<GameDataResp>() {
                    @Override
                    public void onNext(GameDataResp gameDataResp) {
                        if (gameDataResp != null && gameDataResp.isOk() && gameDataResp.getData() != null) {
                            mView.getGameDataSuccess(gameDataResp.getData());
                        } else {
                            mView.getGameDataFailure(mView.getContext().getString(R.string.partner_no_data));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.getGameDataFailure(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
