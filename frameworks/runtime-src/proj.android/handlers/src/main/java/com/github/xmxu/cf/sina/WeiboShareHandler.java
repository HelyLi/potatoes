package com.github.xmxu.cf.sina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.github.xmxu.cf.Callback;
import com.github.xmxu.cf.Config;
import com.github.xmxu.cf.Result;
import com.github.xmxu.cf.ShareRequest;
import com.github.xmxu.cf.ShareResult;
import com.github.xmxu.cf.SimpleShareHandler;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

/**
 * 微信分享处理器
 * Created by Simon on 2016/11/22.
 */

public class WeiboShareHandler extends SimpleShareHandler<ShareResult> {//IWeiboHandler.Response

    private IWeiboShareAPI mWeiboShareApi;

    private Callback<ShareResult> mCallback;
    private Object mTag;

    WeiboShareHandler() {}

    public static WeiboShareHandler get() {
        return new WeiboShareHandler();
    }

    private Activity mActivity;

    @Override
    public void share(final Activity activity, ShareRequest request, Callback<ShareResult> callback, Object tag) {

        mCallback = callback;
        mTag = tag;
        mActivity = activity;
        mWeiboShareApi = WeiboShareSDK.createWeiboAPI(activity.getApplicationContext(), Config.get().getWeiboAppId());
        mWeiboShareApi.registerApp();
//        mWeiboShareApi.handleWeiboResponse(activity.getIntent(), (TourActivity)activity);

        final WeiboMultiMessage weiboMsg = new WeiboMultiMessage();
        final SendMultiMessageToWeiboRequest weiboRequest = new SendMultiMessageToWeiboRequest();
        final TextObject textObject = new TextObject();
        textObject.text = request.getTitle() + "\n" + request.getContent() + "\n" + request.getLink();
        if (!TextUtils.isEmpty(request.getImageUrl())) {
            final ImageObject imageObject = new ImageObject();
            String imageUrl = request.getImageUrl();
            if (imageUrl.startsWith("http:") || imageUrl.startsWith("https:")) {
                //remote
                ImageLoaderAsyncTask imageLoaderAsyncTask = new ImageLoaderAsyncTask();
                imageLoaderAsyncTask.execute(imageUrl);
                imageLoaderAsyncTask.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully(Object data) {
                        weiboMsg.mediaObject = getImageObj((Bitmap)data);
                        weiboMsg.textObject = textObject;
                        weiboRequest.multiMessage = weiboMsg;
                        weiboRequest.transaction = String.valueOf(System.currentTimeMillis());
                        sendRequest(activity, weiboRequest);
                    }

                    @Override
                    public void onDataFailed() {
                        weiboMsg.textObject = textObject;
                        weiboRequest.multiMessage = weiboMsg;
                        weiboRequest.transaction = String.valueOf(System.currentTimeMillis());
                        sendRequest(activity, weiboRequest);
                    }
                });
            } else if (imageUrl.startsWith("file:") || imageUrl.startsWith("content:")) {
                //local
                imageObject.imagePath = imageUrl;
                weiboMsg.textObject = textObject;
                weiboMsg.imageObject = imageObject;
//            weiboMsg.mediaObject = webpageObject;
                weiboRequest.multiMessage = weiboMsg;
                weiboRequest.transaction = String.valueOf(System.currentTimeMillis());
                sendRequest(activity, weiboRequest);
            }
        } else if (request.getImageBitmap() != null) {
            weiboMsg.mediaObject = getImageObj(request.getImageBitmap());
            weiboMsg.textObject = textObject;
            weiboRequest.multiMessage = weiboMsg;
            weiboRequest.transaction = String.valueOf(System.currentTimeMillis());
            sendRequest(activity, weiboRequest);
        } else {
            weiboMsg.textObject = textObject;
            weiboRequest.multiMessage = weiboMsg;
            weiboRequest.transaction = String.valueOf(System.currentTimeMillis());
            sendRequest(activity, weiboRequest);
        }

    }

    private void sendRequest(Activity activity, SendMultiMessageToWeiboRequest request) {
        mWeiboShareApi.sendRequest(activity, request);
    }

    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("TAG", "onNewIntent.this=" + this);
        if (mWeiboShareApi == null) {
            return;
        }
        boolean result = mWeiboShareApi.handleWeiboResponse(intent, (IWeiboHandler.Response)mActivity);
        if (!result) {
            //cancel
            if (mCallback != null) {
                mCallback.onFailure(new Result(Result.Code.CANCEL, "Cancel", mTag));
            }
            mCallback = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

}
