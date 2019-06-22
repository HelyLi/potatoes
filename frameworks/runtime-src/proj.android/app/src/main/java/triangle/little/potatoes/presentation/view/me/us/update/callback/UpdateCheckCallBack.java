package triangle.little.potatoes.presentation.view.me.us.update.callback;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import triangle.little.potatoes.R;
import triangle.little.potatoes.application.ActivityStackManager;
import triangle.little.potatoes.data.net.protocol.update.CheckUpdateResp;
import triangle.little.potatoes.presentation.utils.ToastUtil;
import triangle.little.potatoes.presentation.view.me.us.update.dialog.UpdateVersionDialog;


/**
 * 版本更新callback
 * Created by dell on 2017/5/17.
 */
public final class UpdateCheckCallBack implements IUpdateCheckCallBack {

    @Override
    public void hasUpdate(CheckUpdateResp resp, boolean isMustUpdate) {
        Activity curActivity = ActivityStackManager.get().getTopActivity();
        UpdateVersionDialog dialog = UpdateVersionDialog.newInstance(resp);
        dialog.show(((FragmentActivity) curActivity).getSupportFragmentManager(), "dialog");
    }

    @Override
    public void noUpdate(boolean isShowToast) {
        if (isShowToast) {
            Activity curActivity = ActivityStackManager.get().getTopActivity();
            ToastUtil.showShort(curActivity, curActivity.getString(R.string.partner_update_no_update));
        }
    }

    @Override
    public void onCheckError(String msg) {
        Activity curActivity = ActivityStackManager.get().getTopActivity();
        ToastUtil.showShort(curActivity, msg);
    }
}
