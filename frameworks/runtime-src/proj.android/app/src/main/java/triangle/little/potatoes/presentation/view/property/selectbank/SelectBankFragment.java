package triangle.little.potatoes.presentation.view.property.selectbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.protocol.user.BankResp;
import triangle.little.potatoes.presentation.utils.ToastUtil;
import triangle.little.potatoes.presentation.view.BaseFragment;
import triangle.little.potatoes.presentation.view.me.cash.AddBankCardActivity;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description :
 * Created by Flynn
 * 2017/5/2
 */

public class SelectBankFragment extends BaseFragment implements SelectBankContract.View {

    @BindView(R.id.bank_tv)
    TextView mBankTv;
    @BindView(R.id.select_new_bank_tv)
    TextView mSelectNewBankTv;

    private SelectBankContract.Presenter mPresenter;

    public static SelectBankFragment newInstance() {
        Bundle args = new Bundle();
        SelectBankFragment fragment = new SelectBankFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void viewCreated(View view, Bundle savedInstanceState) {
        new SelectBankPresenter(this);
        fetchData(false);
        RxView.clicks(mSelectNewBankTv)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        startActivityForResult(AddBankCardActivity.getCallIntent(getActivity()), 100);
                    }
                });
    }

    @Override
    protected void fetchData(boolean isLoadMore) {
        mPresenter.getBank();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_bank;
    }

    @Override
    public void setPresenter(SelectBankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getBankSuccess(List<BankResp.DataBeanX.DataBean> list) {
        if (list != null && list.size() > 0 && !TextUtils.isEmpty(list.get(0).getACC_NO()) && !TextUtils.isEmpty(list.get(0).getBANK_ID())) {
            mBankTv.setText(list.get(0).getBANK_ID()
                    + " ("
                    + list.get(0).getACC_NO().substring(list.get(0).getACC_NO().length() - 4, list.get(0).getACC_NO().length())
                    + ")");
            mBankTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getBankFailure(String msg) {
        ToastUtil.showShort(getActivity(), msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fetchData(false);
    }
}
