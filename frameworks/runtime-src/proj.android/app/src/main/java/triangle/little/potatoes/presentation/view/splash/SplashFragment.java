package triangle.little.potatoes.presentation.view.splash;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.BaseSubscriber;
import triangle.little.potatoes.presentation.utils.ToastUtil;
import triangle.little.potatoes.presentation.utils.UserPrefsUtil;
import triangle.little.potatoes.presentation.view.BaseFragment;
import triangle.little.potatoes.presentation.view.account.LoginContract;
import triangle.little.potatoes.presentation.view.account.LoginOrRegisterActivity;
import triangle.little.potatoes.presentation.view.account.LoginPresenterImpl;
import triangle.little.potatoes.presentation.view.main.MainTabActivity;
import triangle.little.potatoes.presentation.view.widget.SimpleButton;

/**
 * description :
 * Created by Flynn
 * 2017/5/10
 */

public class SplashFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.sb_skip)
    SimpleButton mSbSkip;

    private boolean mIsSkip = false;

    LoginContract.Presenter mPresenter;
    private Integer loginStat;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    public static final int NORMAL = 3;

    private int TIME = 3;

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void viewCreated(View view, Bundle savedInstanceState) {
        new LoginPresenterImpl(this);
        fetchData(false);
        Flowable.interval(0, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLife())
                .take(TIME + 1)
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        if (TIME == aLong.intValue()) {
                            mSbSkip.setVisibility(View.GONE);
                        }
                        mSbSkip.setText(getString(R.string.partner_skip) + " " + (TIME - aLong.intValue()));
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        _doSkip_();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        //_doSkip();
                        _doSkip_();
                    }
                });
    }

    @Override
    protected void fetchData(boolean isLoadMore) {
        String userName = UserPrefsUtil.getInstance().getUserName(getActivity());
        String pwd = UserPrefsUtil.getInstance().getUserPwd(getActivity());
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(pwd)) {
            mPresenter.login(userName, pwd);
        } else {
            loginStat = NORMAL;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void doLoginSuccess() {
        loginStat = SUCCESS;
        //        _doSkip();
    }

    @Override
    public void doLoginFail(String msg) {
        loginStat = FAILURE;
        //        _doSkip();
    }
    private void _doSkip_() {
        startActivity(MainTabActivity.getCallIntent(getActivity()));
        getActivity().finish();
    }
    private void _doSkip() {
        if (!mIsSkip) {
            if (loginStat == null) {
                startActivity(LoginOrRegisterActivity.getCallIntent(getActivity()));
                getActivity().finish();
                return;
            }
            mIsSkip = true;
            switch (loginStat) {
                case SUCCESS:
                    startActivity(MainTabActivity.getCallIntent(getActivity()));
                    break;
                case FAILURE:
                case NORMAL:
                    startActivity(LoginOrRegisterActivity.getCallIntent(getActivity()));
                    break;
            }
            getActivity().finish();
        }
    }

    private void skip() {
        if (loginStat == null) {
            //TODO 文字待写
            ToastUtil.showShort(getActivity(), "自动登录中...");
            return;
        }
        mIsSkip = true;
        switch (loginStat) {
            case SUCCESS:
                startActivity(MainTabActivity.getCallIntent(getActivity()));
                break;
            case FAILURE:
            case NORMAL:
                startActivity(LoginOrRegisterActivity.getCallIntent(getActivity()));
                break;
        }
    }


    @OnClick(R.id.sb_skip)
    public void onClick() {
        //        mIsSkip = true;
        //        _doSkip();
        skip();
    }
}
