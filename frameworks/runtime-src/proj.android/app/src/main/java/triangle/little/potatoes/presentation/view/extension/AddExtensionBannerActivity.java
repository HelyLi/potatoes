package triangle.little.potatoes.presentation.view.extension;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * 自定义推广页添加banner
 * Created by dell on 2017/5/5.
 */

public class AddExtensionBannerActivity extends BaseActivity {
    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, AddExtensionBannerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddExtensionBannerFragment bannerFragment = (AddExtensionBannerFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (bannerFragment == null) {
            bannerFragment = AddExtensionBannerFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), bannerFragment, R.id.content_container);
        }
    }
}
