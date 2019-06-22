package triangle.little.potatoes.presentation.view.product;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.GameApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.game.GetIndexlbtReq;
import triangle.little.potatoes.data.net.protocol.game.GetIndexlbtResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;

/**
 * Created by dell on 2017/4/24.
 */

public class ProductBannerPresenterImpl implements ProductContract.BannerPresenter {
    private ProductContract.BannerView mView;
    private GameApi mGameApi;

    public ProductBannerPresenterImpl(ProductContract.BannerView view) {
        mView = view;
        mGameApi = RetrofitManager.getInstance(ApiType.GAME_API).getGameApi();
    }

    @Override
    public void getBannerInfo() {
        GetIndexlbtReq getIndexlbtReq = new GetIndexlbtReq();
        getIndexlbtReq.setPageNo(1);
        getIndexlbtReq.setTerminal(2);
        mGameApi.getIndexlbt(getIndexlbtReq.params())
                .compose(RxUtil.<GetIndexlbtResp>io_main())
                .compose(mView.<GetIndexlbtResp>bindToLife())
                .subscribe(new BaseSubscriber<GetIndexlbtResp>() {
                    @Override
                    public void onNext(GetIndexlbtResp resp) {
                        if (resp != null && resp.getData() != null && resp.getData().getList() != null) {
                            mView.getBannerInfoSuccess(resp.getData().getList());
                        } else {
                            mView.getBannerInfoFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.getBannerInfoFailure(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
