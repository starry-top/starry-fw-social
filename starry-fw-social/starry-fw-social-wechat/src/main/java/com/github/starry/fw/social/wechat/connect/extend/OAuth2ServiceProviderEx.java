package com.github.starry.fw.social.wechat.connect.extend;

import org.springframework.social.oauth2.OAuth2ServiceProvider;

public interface OAuth2ServiceProviderEx<A> extends OAuth2ServiceProvider<A> {

    /**
     * Returns an API interface allowing the client application to access protected resources on behalf of a user.
     * @param accessToken the API access token
     * @return a binding to the service provider's API
     */
    A getApi(String accessToken, String openId);

}
