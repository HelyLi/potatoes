package triangle.little.potatoes.presentation.view.product;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.GameApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.game.GameDataReq;
import triangle.little.potatoes.data.net.protocol.game.GameTypeDataResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;

/**
 * Created by dell on 2017/4/21.
 */

public class ProductCommonPresenterImpl implements ProductContract.Presenter {
    private ProductContract.CommonView mView;
    private GameApi mGameApi;

    public ProductCommonPresenterImpl(ProductContract.CommonView view) {
        mView = view;
        mView.setPresenter(this);
        mGameApi = RetrofitManager.getInstance(ApiType.GAME_API).getGameApi();
    }

    @Override
    public void getGameData(int gameType) {
        GameDataReq gameDataReq = new GameDataReq();
        gameDataReq.setGameType(gameType);
        mGameApi.getGameDataByType(gameDataReq.params())
                .compose(RxUtil.<GameTypeDataResp>io_main())
                .compose(mView.<GameTypeDataResp>bindToLife())
                .subscribe(new BaseSubscriber<GameTypeDataResp>() {
                    @Override
                    public void onNext(GameTypeDataResp resp) {
                        if (resp != null && resp.isOk() && resp.getData() != null) {
                            mView.getGameDataSuccess(resp.getData());
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
