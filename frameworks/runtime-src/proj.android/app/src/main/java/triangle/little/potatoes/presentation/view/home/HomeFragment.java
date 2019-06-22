package triangle.little.potatoes.presentation.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.lua.AppActivity;

import java.util.ArrayList;

import butterknife.BindView;
import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.protocol.proxy.IndexDataResp;
import triangle.little.potatoes.presentation.utils.DisplayUtil;
import triangle.little.potatoes.presentation.utils.ToastUtil;
import triangle.little.potatoes.presentation.view.BaseFragment;
import triangle.little.potatoes.presentation.view.home.headview.HomeHeadView;
import triangle.little.potatoes.presentation.view.property.MyPropertyActivity;
import triangle.little.potatoes.presentation.view.proxycount.ProxyMainActivity;
import triangle.little.potatoes.presentation.view.rule.AgentRuleActivity;
import triangle.little.potatoes.presentation.view.salary.SalaryModelModifyActivity;
import triangle.little.potatoes.presentation.view.salary.SalaryModelModifyFragment;
import triangle.little.potatoes.presentation.view.widget.RecycleViewItemDivider;

/**
 * description
 * Created by dell on 2017/4/12.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.home_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private HomeContract.Presenter mPresenter;
    private HomeHeadView mHomeHeadView;
    private ArrayList<HomeItem> mData;
    private HomeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new HomePresenter(this);
    }

    @Override
    protected void viewCreated(View view, Bundle savedInstanceState) {
        mRefreshLayout.setColorSchemeResources(R.color.color_a965f4, R.color.color_3c4444, R.color.color_f60808);
        mRefreshLayout.setProgressViewOffset(false, -DisplayUtil.dip2px(getActivity(), 40), getResources().getDimensionPixelSize(R.dimen.home_refresh_offset));
        mRefreshLayout.setOnRefreshListener(this);

        initRecycleView();

        fetchData(false);

    }

    private void initRecycleView() {
        mData = new ArrayList<>();
        mData.add(new HomeItem(R.drawable.ic_my_proxy, R.string.partner_home_item_my_proxy, R.string.partner_home_item_my_proxy_en));
        mData.add(new HomeItem(R.drawable.ic_data_statistics, R.string.partner_home_item_data, R.string.partner_home_item_data_en));
        mData.add(new HomeItem(R.drawable.ic_my_assets, R.string.partner_home_item_my_assets, R.string.partner_home_item_my_assets_en));
        mData.add(new HomeItem(R.drawable.ic_proxy_rules, R.string.partner_home_item_proxy_rule, R.string.partner_home_item_proxy_rule_en));
        mData.add(new HomeItem(R.drawable.ic_generalize, R.string.partner_home_item_extension, R.string.partner_home_item_extension_en));
        mData.add(new HomeItem(R.drawable.ic_commission_model, R.string.partner_home_item_rebate, R.string.partner_home_item_rebate_en));
        mAdapter = new HomeAdapter(mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new RecycleViewItemDivider().setDividerWith(2));

        //        View headView = View.inflate(getActivity(), R.layout.include_home_head, null);
        mHomeHeadView = new HomeHeadView(getActivity());
        mAdapter.addHeaderView(mHomeHeadView);
        ViewGroup.LayoutParams lp = mHomeHeadView.getLayoutParams();
        lp.height = DisplayUtil.dip2px(getActivity(), 225f);
        mHomeHeadView.setLayoutParams(lp);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onRecycleViewItemClick(adapter, view, position);
            }
        });
    }

    private void onRecycleViewItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                //startActivity(AgentActivity.getCallIntent(getActivity()));
                startActivity(AppActivity.getCallIntent(getActivity()));
                break;
            case 1:
                startActivity(ProxyMainActivity.getCallIntent(getActivity(), ProxyMainActivity.INCOMES));
                break;
            case 2:
                startActivity(MyPropertyActivity.getCallIntent(getActivity()));
                break;
            case 3:
                startActivity(AgentRuleActivity.getCallIntent(getActivity(), AgentRuleActivity.RULE));
                break;
            case 4:
                startActivity(AgentRuleActivity.getCallIntent(getActivity(), AgentRuleActivity.EXTENSION));
                break;
            case 5:
                Bundle bundle = new Bundle();
                bundle.putInt(SalaryModelModifyFragment.KEY_TYPE, SalaryModelModifyFragment.REBATE);
                startActivity(SalaryModelModifyActivity.getCallIntent(getActivity(), bundle));
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void fetchData(boolean isLoadMore) {
        mRefreshLayout.setRefreshing(!isLoadMore);
        mPresenter.getIndexData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        fetchData(false);
    }


    @Override
    public void getIndexDataSuccess(IndexDataResp.DataBean dataBean) {
        mRefreshLayout.setRefreshing(false);
        if (dataBean == null) {
            return;
        }
        mHomeHeadView.updateData(String.format("%.2f", dataBean.getIncomeMoney()),
                String.valueOf(dataBean.getAgentsNum()),
                String.valueOf(dataBean.getAgentProductNum()),
                String.valueOf(dataBean.getPayNum()));

    }

    @Override
    public void getIndexDataFailure(String msg) {
        mRefreshLayout.setRefreshing(false);
        ToastUtil.showShort(getActivity(), msg);
    }

}
