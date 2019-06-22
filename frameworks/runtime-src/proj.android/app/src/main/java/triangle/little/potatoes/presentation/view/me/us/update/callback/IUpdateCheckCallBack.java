package triangle.little.potatoes.presentation.view.me.us.update.callback;


import triangle.little.potatoes.data.net.protocol.update.CheckUpdateResp;

/**
 * 检查版本更新callback
 */
public interface IUpdateCheckCallBack {

    void hasUpdate(CheckUpdateResp resp,boolean isMustUpdate);

    void noUpdate(boolean isShowToast);

    void onCheckError(String msg);

}
