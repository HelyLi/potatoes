package triangle.little.potatoes.presentation.view.me.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import triangle.little.potatoes.R;
import triangle.little.potatoes.presentation.utils.ActivityUtil;
import triangle.little.potatoes.presentation.view.BaseActivity;

/**
 * 通知
 * Created by dell on 2017/5/2.
 */

public class NoticeActivity extends BaseActivity {
    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoticeFragment noticeFragment = (NoticeFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (noticeFragment == null) {
            noticeFragment = NoticeFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(), noticeFragment, R.id.content_container);
        }
    }
}
