package triangle.little.potatoes.presentation.view.me.cash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * Created by dell on 2017/4/19.
 */

public class AuthenticationActivity extends BaseActivity {
    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthenticationFragment fragment = (AuthenticationFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (fragment == null) {
            fragment = AuthenticationFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), fragment, R.id.content_container);
        }
    }
}
