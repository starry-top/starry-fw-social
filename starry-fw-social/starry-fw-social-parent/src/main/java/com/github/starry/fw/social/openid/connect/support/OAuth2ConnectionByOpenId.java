package com.github.starry.fw.social.openid.connect.support;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.github.starry.fw.social.openid.connect.ApiByOpenId;

public class OAuth2ConnectionByOpenId<A extends ApiByOpenId> extends OAuth2Connection<A> {

    public OAuth2ConnectionByOpenId(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime,
            OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter, String openId) {
        super(providerId, providerUserId, accessToken, refreshToken, expireTime, serviceProvider, apiAdapter);
        this.getApi().setOpenId(openId);
    }

    public OAuth2ConnectionByOpenId(ConnectionData data, OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter, String openId) {
        super(data, serviceProvider, apiAdapter);
        this.getApi().setOpenId(openId);
    }
}
