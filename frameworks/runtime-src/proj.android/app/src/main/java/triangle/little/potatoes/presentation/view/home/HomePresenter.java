package triangle.little.potatoes.presentation.view.home;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.ProxyApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.UserApi;
import triangle.little.potatoes.data.net.protocol.proxy.IndexDataReq;
import triangle.little.potatoes.data.net.protocol.proxy.IndexDataResp;
import triangle.little.potatoes.data.net.protocol.proxy.MyProxyDataReq;
import triangle.little.potatoes.data.net.protocol.proxy.MyProxyDataResp;
import triangle.little.potatoes.data.net.protocol.user.*;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.MD5;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/4/14
 */

public class HomePresenter implements HomeContract.Presenter {

    private final ProxyApi mProxyApi;
    private final UserApi mUserApi;
    private HomeContract.View mView;


    public HomePresenter(HomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mProxyApi = RetrofitManager.getInstance(ApiType.PROXY_API).getProxyApi();
        mUserApi = RetrofitManager.getInstance(ApiType.USER_API).getUserApi();


    }

    @Override
    public void getIndexData() {
        IndexDataReq req = new IndexDataReq();
        mProxyApi.getIndexData(req.params())
                .compose(RxUtil.<IndexDataResp>io_main())
                .compose(mView.<IndexDataResp>bindToLife())
                .subscribe(new BaseSubscriber<IndexDataResp>() {
                    @Override
                    public void onNext(IndexDataResp indexDataResp) {
                        if (indexDataResp.isOk()) {
                            mView.getIndexDataSuccess(indexDataResp.getData());
                        } else {
                            mView.getIndexDataFailure(indexDataResp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getIndexDataFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });

        NoticeByIdReq noticeByIdReq = new NoticeByIdReq();
        noticeByIdReq.setId(0);
        //        mUserApi.getNotice()
    }
}
