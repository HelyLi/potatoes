package triangle.little.potatoes.presentation.view.me.code;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * 邀请码
 * Created by dell on 2017/5/2.
 */

public class InviteCodeActivity extends BaseActivity {
    public static Intent getCallIntente(Context context) {
        Intent intent = new Intent(context, InviteCodeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InviteCodeFragment inviteCodeFragment = (InviteCodeFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (inviteCodeFragment == null) {
            inviteCodeFragment = InviteCodeFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), inviteCodeFragment, R.id.content_container);
        }
    }
}
