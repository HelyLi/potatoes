package triangle.little.potatoes.presentation.view.me.cash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * Created by dell on 2017/4/17.
 */

public class AddBankCardActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddBankCardFragment addBankCardFragment = (AddBankCardFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (addBankCardFragment == null) {
            addBankCardFragment = AddBankCardFragment.newInstance();
            addBankCardFragment.setUserVisibleHint(true);
            ActivityUtil.addFragment(getSupportFragmentManager(), addBankCardFragment, R.id.content_container);
        }
    }

    public static Intent getCallIntent(Context context) {
        Intent intent=new Intent(context,AddBankCardActivity.class);
        return intent;
    }
}
