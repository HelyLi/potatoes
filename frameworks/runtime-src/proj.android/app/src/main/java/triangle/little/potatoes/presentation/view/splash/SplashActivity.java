package triangle.little.potatoes.presentation.view.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * description :
 * Created by dell on 2017/4/11.
 */

public class SplashActivity extends BaseActivity {

    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashFragment agentFragment = (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (agentFragment == null) {
            agentFragment = SplashFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), agentFragment, R.id.content_container);
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }
}
