package triangle.little.potatoes.presentation.view.member;

import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.ApiType;
import triangle.little.potatoes.data.net.ProxyApi;
import triangle.little.potatoes.data.net.RetrofitManager;
import triangle.little.potatoes.data.net.protocol.proxy.GetMembersRechargeSumReq;
import triangle.little.potatoes.data.net.protocol.proxy.GetMembersRechargeSumResp;
import triangle.little.potatoes.data.net.protocol.proxy.RechargeByMemberDetailReq;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.RxUtil;
import com.orhanobut.logger.Logger;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public class MemberPresenter implements MemberContract.Presenter {

    private final ProxyApi mProxyApi;
    private MemberContract.View mView;

    public MemberPresenter(MemberContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mProxyApi = RetrofitManager.getInstance(ApiType.PROXY_API).getProxyApi();
    }

    @Override
    public void getMembersRechargeSum(int pageNo) {
        final GetMembersRechargeSumReq req = new GetMembersRechargeSumReq();
        req.setPageNo(pageNo);
        mProxyApi.getMembersRechargeSum(req.params())
                .compose(mView.<GetMembersRechargeSumResp>bindToLife())
                .compose(RxUtil.<GetMembersRechargeSumResp>io_main())
                .subscribe(new BaseSubscriber<GetMembersRechargeSumResp>() {
                    @Override
                    public void onNext(GetMembersRechargeSumResp resp) {
                        if (resp.isOk()) {
                            mView.getMembersRechargeSumSuccess(resp.getData());
                        } else {
                            mView.getMembersRechargeSumFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getMembersRechargeSumFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }

    @Override
    public void getMemberDaySum(int pageNo) {
        final GetMembersRechargeSumReq req = new GetMembersRechargeSumReq();
        req.setPageNo(pageNo);
        mProxyApi.getMemberDaySum(req.params())
                .compose(mView.<GetMembersRechargeSumResp>bindToLife())
                .compose(RxUtil.<GetMembersRechargeSumResp>io_main())
                .subscribe(new BaseSubscriber<GetMembersRechargeSumResp>() {
                    @Override
                    public void onNext(GetMembersRechargeSumResp resp) {
                        if (resp.isOk()) {
                            mView.getMembersRechargeSumSuccess(resp.getData());
                        } else {
                            mView.getMembersRechargeSumFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getMembersRechargeSumFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }

    @Override
    public void getMemberWeekSum(int pageNo) {
        final GetMembersRechargeSumReq req = new GetMembersRechargeSumReq();
        req.setPageNo(pageNo);
        mProxyApi.getMemberWeekSum(req.params())
                .compose(mView.<GetMembersRechargeSumResp>bindToLife())
                .compose(RxUtil.<GetMembersRechargeSumResp>io_main())
                .subscribe(new BaseSubscriber<GetMembersRechargeSumResp>() {
                    @Override
                    public void onNext(GetMembersRechargeSumResp resp) {
                        if (resp.isOk()) {
                            mView.getMembersRechargeSumSuccess(resp.getData());
                        } else {
                            mView.getMembersRechargeSumFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getMembersRechargeSumFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }

    @Override
    public void getMemberMonthSum(int pageNo) {
        final GetMembersRechargeSumReq req = new GetMembersRechargeSumReq();
        req.setPageNo(pageNo);
        mProxyApi.getMemberMonthSum(req.params())
                .compose(mView.<GetMembersRechargeSumResp>bindToLife())
                .compose(RxUtil.<GetMembersRechargeSumResp>io_main())
                .subscribe(new BaseSubscriber<GetMembersRechargeSumResp>() {
                    @Override
                    public void onNext(GetMembersRechargeSumResp resp) {
                        if (resp.isOk()) {
                            mView.getMembersRechargeSumSuccess(resp.getData());
                        } else {
                            mView.getMembersRechargeSumFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getMembersRechargeSumFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });
    }

    @Override
    public void getRechargeByMemberDetail(String flag, int pageNo) {
        final RechargeByMemberDetailReq req = new RechargeByMemberDetailReq();
        req.setFlag(flag);
        req.setPageNo(pageNo);
        mProxyApi.getRechargeByMemberDetail(req.params())
                .compose(mView.<GetMembersRechargeSumResp>bindToLife())
                .compose(RxUtil.<GetMembersRechargeSumResp>io_main())
                .subscribe(new BaseSubscriber<GetMembersRechargeSumResp>() {
                    @Override
                    public void onNext(GetMembersRechargeSumResp resp) {
                        if (resp.isOk()) {
                            mView.getMembersRechargeSumSuccess(resp.getData());
                        } else {
                            mView.getMembersRechargeSumFailure(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        mView.getMembersRechargeSumFailure(mView.getContext().getString(R.string.partner_request_network_error));
                    }
                });

    }
}
