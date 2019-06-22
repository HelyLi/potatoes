package triangle.little.potatoes.presentation.view.account;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;
import triangle.little.potatoes.presentation.view.immersive.IImmersiveApply;

/**
 * description :
 * Created by Flynn
 * 2017/4/14
 */

public class ResetPwdActivity extends BaseActivity implements IImmersiveApply {

    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        return intent;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ResetPwdFragment resetPwdFragment = (ResetPwdFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (resetPwdFragment == null) {
            resetPwdFragment = ResetPwdFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), resetPwdFragment, R.id.content_container);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public boolean applyImmersive() {
        return true;
    }

    @Override
    public boolean applyScroll() {
        return false;
    }

    @Override
    public float initAlpha() {
        return 1;
    }
}
