package triangle.little.potatoes.presentation.view.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * 产品详情
 * Created by dell on 2017/4/25.
 */

public class ProductDetailsActivity extends BaseActivity {
    public static Intent getCallIntent(Context context, int gameId, String titleName) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("gameId", gameId);
        intent.putExtra("titleName", titleName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductDetailsFragment detailsFragment = (ProductDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (detailsFragment == null) {
            Intent intent = getIntent();
            detailsFragment = ProductDetailsFragment.newInstance(intent.getIntExtra("gameId", 0),
                    intent.getStringExtra("titleName"));
            ActivityUtil.addFragment(getSupportFragmentManager(), detailsFragment, R.id.content_container);
        }
    }
}
