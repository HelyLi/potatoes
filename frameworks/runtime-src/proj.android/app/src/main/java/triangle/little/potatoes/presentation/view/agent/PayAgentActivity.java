package triangle.little.potatoes.presentation.view.agent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;
import triangle.little.potatoes.presentation.view.immersive.IImmersiveApply;

/**
 * description :
 * Created by Flynn
 * 2017/4/24
 */

public class PayAgentActivity extends BaseActivity implements IImmersiveApply {


    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, PayAgentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PayAgentFragment fragment = (PayAgentFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (fragment == null) {
            fragment = PayAgentFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), fragment, R.id.content_container);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.include_toolbar_and_content;
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
