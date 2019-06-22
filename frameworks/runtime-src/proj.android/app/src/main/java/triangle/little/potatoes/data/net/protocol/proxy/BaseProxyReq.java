package triangle.little.potatoes.data.net.protocol.proxy;

import triangle.little.potatoes.data.net.protocol.BaseReq;

import java.util.Map;

/**
 * description :
 * Created by Flynn
 * 2017/4/13
 */
public class BaseProxyReq extends BaseReq {
    /**
     * 用户ID
     */
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public Map<String, String> params() {
        Map<String, String> params = super.params();
        if (userId != null) {
            params.put("userid", userId);
        }
        return params;
    }
}
