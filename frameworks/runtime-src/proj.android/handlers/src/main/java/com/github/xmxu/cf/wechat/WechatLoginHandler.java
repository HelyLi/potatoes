package com.github.xmxu.cf.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.github.xmxu.cf.Callback;
import com.github.xmxu.cf.Config;
import com.github.xmxu.cf.LoginResult;
import com.github.xmxu.cf.Result;
import com.github.xmxu.cf.SimpleLoginHandler;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 微信登录处理器
 * Created by Simon on 2016/11/22.
 */

public class WechatLoginHandler extends SimpleLoginHandler<LoginResult> implements IWXAPIEventHandler {

    private IWXAPI api;
    private Callback<LoginResult> mCallback;
    private Object mTag;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    WechatLoginHandler() {

    }

    public static WechatLoginHandler get() {
        return new WechatLoginHandler();
    }

    @Override
    public void login(Activity activity, Callback<LoginResult> callback, Object tag) {

        mCallback = callback;
        mTag = tag;
        WechatHelper.getInstance().setCurrentHandler(this);

        api = WXAPIFactory.createWXAPI(activity, Config.get().getWechatAppId(), true);
        if (!api.isWXAppInstalled()) {
            if (callback != null) {
                callback.onFailure(new Result(Result.Code.NO_CLIENT, "未安装微信客户端", tag));
            }
            return;
        }

        api.registerApp(Config.get().getWechatAppId());

        final SendAuth.Req req = new SendAuth.Req();
        req.state = activity.getPackageName() + "_auth";
        req.scope = Config.get().getWechatScope();
        if (api != null) {
            api.sendReq(req);
        }

        //如果没回调onResp，八成是这句没有写
//        api.handleIntent(getIntent(), this);

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (api != null) {
            api.handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp) {
        if (baseResp instanceof SendAuth.Resp) {
            Log.d("wechat", "SendAuth");
            //登录
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("wechat", "run");

                            requestToken(((SendAuth.Resp)baseResp).code);
                        }
                    });
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    if (mCallback != null) {
                        mCallback.onFailure(new Result(Result.Code.CANCEL, "Cancel", mTag));
                    }
                    mCallback = null;
                    break;
                default:
                    if (mCallback != null) {
                        mCallback.onFailure(new Result(baseResp.errCode, baseResp.errStr, mTag));
                    }
                    mCallback = null;
                    break;
            }
        }
    }


    private void requestToken (String code) {
        //https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID
        // &client_secret=YOUR_CLIENT_SECRET
        // &grant_type=refresh_token
        // &redirect_uri=YOUR_REGISTERED_REDIRECT_URI
        // &refresh_token=

        String urlString = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                Config.get().getWechatAppId(), Config.get().getWechatAppKey(), code);
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            final int status = urlConnection.getResponseCode();

            Log.d("wechat", "status=" + status);

            if (status / 100 == 2) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), Charset.forName("utf-8")));
                String line = "";
                StringBuilder contentSb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    contentSb.append(line);
                }

                JSONObject object = new JSONObject(contentSb.toString());
                String accessToken = object.optString("access_token");
                String refreshToken = object.optString("refresh_token");
                String openId = object.optString("openid");
                String uid = object.optString("unionid");

                Log.d("wechat", "accessToken=" + accessToken + ",openId=" + openId);

                final LoginResult loginResult = new LoginResult();
                loginResult.setUid(uid);
                loginResult.setOpenId(openId);
                loginResult.setAccessToken(accessToken);
                loginResult.setRefreshToken(refreshToken);
                loginResult.setOriginal(contentSb.toString());
                loginResult.setTag(mTag);
                loginResult.setAppId(Config.get().getWechatAppId());

                postAction(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback != null) {
                            mCallback.onComplete(loginResult);
                        }
                        mCallback = null;
                    }
                });
            } else {
                postAction(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback != null) {
                            mCallback.onFailure(new Result(status, "", mTag));
                        }
                        mCallback = null;
                    }
                });

            }

        } catch (Exception e) {
            postAction(new Runnable() {
                @Override
                public void run() {
                    if (mCallback != null) {
                        mCallback.onFailure(new Result(Integer.MAX_VALUE, "", mTag));
                    }
                    mCallback = null;
                }
            });

        }
    }

    private void postAction(Runnable runnable) {
        mHandler.post(runnable);
    }

    private Executor mExecutor = Executors.newSingleThreadExecutor();


}
