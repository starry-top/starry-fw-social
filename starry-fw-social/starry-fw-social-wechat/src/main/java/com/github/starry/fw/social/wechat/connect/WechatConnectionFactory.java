package com.github.starry.fw.social.wechat.connect;

import com.github.starry.fw.social.wechat.api.Wechat;
import com.github.starry.fw.social.wechat.connect.extend.OAuth2ConnectionFactoryEx;

public class WechatConnectionFactory extends OAuth2ConnectionFactoryEx<Wechat> {

    /**
     * Creates a WechatConnectionFactory for the given application ID and secret.
     * @param appId The application's App ID as assigned by Wechat
     * @param appSecret The application's App Secret as assigned by Wechat
     */
    public WechatConnectionFactory(String appId, String appSecret) {
        this(appId, appSecret, null);
    }

    /**
     * Creates a WechatConnectionFactory for the given application ID, secret, and namespace.
     * @param appId The application's App ID as assigned by Wechat
     * @param appSecret The application's App Secret as assigned by Wechat
     * @param appNamespace The application's App Namespace as configured with Wechat. Enables use of Open Graph operations.
     */
    public WechatConnectionFactory(String appId, String appSecret, String appNamespace) {
        super("wechat", new WechatServiceProvider(appId, appSecret, appNamespace), new WechatAdapter());
    }

}
