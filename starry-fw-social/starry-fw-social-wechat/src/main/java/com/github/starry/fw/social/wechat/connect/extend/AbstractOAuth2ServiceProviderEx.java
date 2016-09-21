package com.github.starry.fw.social.wechat.connect.extend;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

public abstract class AbstractOAuth2ServiceProviderEx<S> extends AbstractOAuth2ServiceProvider<S> implements OAuth2ServiceProviderEx<S> {

    public AbstractOAuth2ServiceProviderEx(OAuth2Operations oauth2Operations) {
       super(oauth2Operations);
    }


    public S getApi(String accessToken) {
        return null;
    }

    public abstract S getApi(String accessToken, String openId);

}
