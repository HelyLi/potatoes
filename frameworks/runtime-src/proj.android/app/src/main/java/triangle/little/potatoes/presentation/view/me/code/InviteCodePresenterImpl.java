package triangle.little.potatoes.presentation.view.me.code;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.MobileReq;
import triangle.little.potatoes.data.net.protocol.user.MobileResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.CollectionUtil;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * 邀请码presenterImpl
 * Created by dell on 2017/5/2.
 */

public class InviteCodePresenterImpl implements InviteCodeContract.Presenter {
    private InviteCodeContract.View mView;
    private UserApi mUserApi;

    public InviteCodePresenterImpl(InviteCodeContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void getInviteCode() {
        MobileReq req = new MobileReq();
        mUserApi.getMobile(req.params())
                .compose(RxUtil.<MobileResp>io_main())
                .compose(mView.<MobileResp>bindToLife())
                .subscribe(new BaseSubscriber<MobileResp>() {
                    @Override
                    public void onNext(MobileResp resp) {
                        if (resp != null && resp.isOk() && resp.getData() != null
                                && CollectionUtil.isNotEmpty(resp.getData().getData())) {
                            mView.getInviteCodeSuccess(resp.getData().getData().get(0).getPARTNER_NO());
                        } else {
                            mView.getInviteCodeFail(mView.getContext().getString(R.string.partner_request_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e("onError:" + t.getMessage());
                        mView.getInviteCodeFail(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
