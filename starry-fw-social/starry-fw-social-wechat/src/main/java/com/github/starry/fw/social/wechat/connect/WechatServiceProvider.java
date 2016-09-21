package com.github.starry.fw.social.wechat.connect;

import org.springframework.social.oauth2.OAuth2Template;

import com.github.starry.fw.social.wechat.api.Wechat;
import com.github.starry.fw.social.wechat.api.impl.WechatTemplate;
import com.github.starry.fw.social.wechat.connect.extend.AbstractOAuth2ServiceProviderEx;
import com.github.starry.fw.social.wechat.connect.extend.WechatOAuth2Template;

public class WechatServiceProvider extends AbstractOAuth2ServiceProviderEx<Wechat> {

    private String appNamespace;

    /**
     * Creates a WechatServiceProvider for the given application ID, secret, and namespace.
     * @param appId The application's App ID as assigned by Wechat
     * @param appSecret The application's App Secret as assigned by Wechat
     * @param appNamespace The application's App Namespace as configured with Wechat. Enables use of Open Graph operations.
     */
    public WechatServiceProvider(String appId, String appSecret, String appNamespace) {
        super(getOAuth2Template(appId, appSecret));
        this.appNamespace = appNamespace;
    }

    private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
        OAuth2Template oAuth2Template = new WechatOAuth2Template(appId, appSecret,
                "https://open.weixin.qq.com/connect/oauth2/authorize",
                "https://api.weixin.qq.com/sns/oauth2/access_token");
        oAuth2Template.setUseParametersForClientAuthentication(false);
        return oAuth2Template;
    }

    public Wechat getApi(String accessToken, String openId) {
        return new WechatTemplate(accessToken, appNamespace);
    }

}