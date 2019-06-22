package triangle.little.potatoes.presentation.view.extension;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.BaseReq;
import triangle.little.potatoes.data.net.protocol.user.GetUserIdResp;
import triangle.little.potatoes.data.net.protocol.user.MobileReq;
import triangle.little.potatoes.data.net.protocol.user.MobileResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/5/3
 */

public class ExtensionPresenter implements ExtensionContract.Presenter {

    private final UserApi mUserApi;
    private ExtensionContract.View mView;

    public ExtensionPresenter(ExtensionContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getMobile() {
        MobileReq req = new MobileReq();
        mUserApi.getMobile(req.params())
                .compose(mView.<MobileResp>bindToLife())
                .compose(RxUtil.<MobileResp>io_main())
                .subscribe(new BaseSubscriber<MobileResp>() {
                    @Override
                    public void onNext(MobileResp resp) {
                        if (resp.isOk()) {
                            mView.getMobileSuccess(resp.getData().getData());
                        } else {
                            mView.getMobileFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getMobileFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }

    @Override
    public void getUserId() {
        BaseReq req = new BaseReq();
        mUserApi.getUserId(req.params())
                .compose(mView.<GetUserIdResp>bindToLife())
                .compose(RxUtil.<GetUserIdResp>io_main())
                .subscribe(new BaseSubscriber<GetUserIdResp>() {
                    @Override
                    public void onNext(GetUserIdResp resp) {
                        if (resp.isOk()) {
                            mView.getUserIdSuccess(resp.getUserid());
                        } else {
                            mView.getUserIdFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getUserIdFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });


    }
}
