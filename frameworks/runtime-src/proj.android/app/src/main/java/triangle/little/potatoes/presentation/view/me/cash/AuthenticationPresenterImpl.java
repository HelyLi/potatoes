package triangle.little.potatoes.presentation.view.me.cash;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.user.IdCardReq;
import triangle.little.potatoes.data.net.protocol.user.IdCardResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;

/**
 * Created by dell on 2017/4/19.
 */

public class AuthenticationPresenterImpl implements AuthenticationContract.Presenter {
    private AuthenticationContract.View mView;
    private UserApi mUserApi;

    public AuthenticationPresenterImpl(AuthenticationContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();
    }

    @Override
    public void submitAuthentication(String name, String idNum) {
        IdCardReq idCardReq = new IdCardReq();
        idCardReq.setCardName(name);
        idCardReq.setCardNo(idNum);
        mUserApi.getIdCard(idCardReq.params()).compose(RxUtil.<IdCardResp>io_main())
                .compose(mView.<IdCardResp>bindToLife())
                .subscribe(new BaseSubscriber<IdCardResp>() {
                    @Override
                    public void onNext(IdCardResp idCardResp) {
                        if (idCardResp != null && idCardResp.isOk() && idCardResp.getData() != null) {
                            String same = mView.getContext().getString(R.string.partner_personal_auth_same);
                            if (same.equals(idCardResp.getData().getIdcardResult()) &&
                                    same.equals(idCardResp.getData().getNameResult())) {
                                mView.submitAuthenticationSuccess(mView.getContext().getString(R.string.partner_personal_auth_success));
                            } else {
                                mView.submitAuthenticationFailure(mView.getContext().getString(R.string.partner_personal_auth_not_match));
                            }
                        } else {
                            mView.submitAuthenticationFailure(mView.getContext().getString(R.string.partner_personal_auth_fail));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.submitAuthenticationFailure(mView.getContext().getString(R.string.partner_request_error));
                    }
                });
    }
}
