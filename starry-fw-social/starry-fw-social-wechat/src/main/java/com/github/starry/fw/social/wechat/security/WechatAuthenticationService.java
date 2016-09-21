package com.github.starry.fw.social.wechat.security;

import org.springframework.social.security.provider.OAuth2AuthenticationService;

import com.github.starry.fw.social.wechat.api.Wechat;
import com.github.starry.fw.social.wechat.connect.WechatConnectionFactory;

public class WechatAuthenticationService extends OAuth2AuthenticationService<Wechat> {

    public WechatAuthenticationService(String apiKey, String appSecret) {
        super(new WechatConnectionFactory(apiKey, appSecret));
    }

    public WechatAuthenticationService(String apiKey, String appSecret, String appNamespace) {
        super(new WechatConnectionFactory(apiKey, appSecret, appNamespace));
    }

}
