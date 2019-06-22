package triangle.little.potatoes.presentation.view.agent;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.ProxyApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.proxy.RechargeByUnderAgentsDetailReq;
import triangle.little.potatoes.data.net.protocol.proxy.RechargeByUnderAgentsDetailResp;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public class PayAgentPresenter implements PayAgentContract.Presenter {

    private final ProxyApi mProxyApi;
    private PayAgentContract.View mView;

    public PayAgentPresenter(PayAgentContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mProxyApi = RetrofitManager.getInstance(ApiType.PROXY_API).getProxyApi();
    }


    @Override
    public void getRechargeByUnderAgentsDetail(String flag, int pageNo) {
        RechargeByUnderAgentsDetailReq req = new RechargeByUnderAgentsDetailReq();
        req.setFlag(flag);
        req.setPageNo(pageNo);
        mProxyApi.getRechargeByUnderAgentsDetail(req.params())
                .compose(mView.<RechargeByUnderAgentsDetailResp>bindToLife())
                .compose(RxUtil.<RechargeByUnderAgentsDetailResp>io_main())
                .subscribe(new BaseSubscriber<RechargeByUnderAgentsDetailResp>() {
                    @Override
                    public void onNext(RechargeByUnderAgentsDetailResp resp) {
                        if (resp.isOk()) {
                            mView.getRechargeByUnderAgentsDetailSuccess(resp.getData());
                        } else {
                            mView.getRechargeByUnderAgentsDetailFailure(resp.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getRechargeByUnderAgentsDetailFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }
}
