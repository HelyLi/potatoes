package triangle.little.potatoes.presentation.view.agent;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.ProxyApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.proxy.MyProxyDataReq;
import triangle.little.potatoes.data.net.protocol.proxy.MyProxyDataResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/4/15
 */

public class AgentPresenter implements AgentContract.Presenter {

    private final AgentContract.View mView;
    private final ProxyApi mProxyApi;

    AgentPresenter(AgentContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mProxyApi = RetrofitManager.getInstance(ApiType.PROXY_API).getProxyApi();
    }

    @Override
    public void getMyProxyData(int orderBy, int pageNo) {
        MyProxyDataReq req = new MyProxyDataReq();
        req.setOrderby(orderBy);
        req.setPageNo(pageNo);
        mProxyApi.getMyProxyData(req.params())
                .compose(RxUtil.<MyProxyDataResp>io_main())
                .compose(mView.<MyProxyDataResp>bindToLife())
                .subscribe(new BaseSubscriber<MyProxyDataResp>() {
                    @Override
                    public void onNext(MyProxyDataResp myProxyDataResp) {
                        if (myProxyDataResp.isOk()) {
                            mView.getMyProxyDataSuccess(myProxyDataResp.getData());
                        } else {
                            mView.getMyProxyDataFailure(myProxyDataResp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.getMyProxyDataFailure(mView.getContext().getString(R.string.partner_request_error));
                        Logger.e(t.getMessage());
                    }
                });

    }
}
