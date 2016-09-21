package com.github.starry.fw.social.wechat.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.github.starry.fw.social.wechat.api.Wechat;

public class WechatConnectionFactory extends OAuth2ConnectionFactory<Wechat> {

    /**
     * Creates a FacebookConnectionFactory for the given application ID and secret.
     * Using this constructor, no application namespace is set (and therefore Facebook's Open Graph operations cannot be used).
     * @param appId The application's App ID as assigned by Facebook
     * @param appSecret The application's App Secret as assigned by Facebook
     */
    public WechatConnectionFactory(String appId, String appSecret) {
        this(appId, appSecret, null);
    }

    /**
     * Creates a FacebookConnectionFactory for the given application ID, secret, and namespace.
     * @param appId The application's App ID as assigned by Facebook
     * @param appSecret The application's App Secret as assigned by Facebook
     * @param appNamespace The application's App Namespace as configured with Facebook. Enables use of Open Graph operations.
     */
    public WechatConnectionFactory(String appId, String appSecret, String appNamespace) {
        super("starry", new WechatServiceProvider(appId, appSecret, appNamespace), new WechatAdapter());
    }

}
