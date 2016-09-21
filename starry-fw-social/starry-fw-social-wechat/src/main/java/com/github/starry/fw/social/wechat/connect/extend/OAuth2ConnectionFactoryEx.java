package com.github.starry.fw.social.wechat.connect.extend;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

public class OAuth2ConnectionFactoryEx<S> extends OAuth2ConnectionFactory<S> {

    public OAuth2ConnectionFactoryEx(String providerId,
            OAuth2ServiceProviderEx<S> serviceProvider, ApiAdapter<S> apiAdapter) {
        super(providerId, serviceProvider, apiAdapter);
    }

    /**
     * Create a OAuth2-based {@link Connection} from the {@link AccessGrant} returned after {@link #getOAuthOperations() completing the OAuth2 flow}.
     * @param accessGrant the access grant
     * @return the new service provider connection
     * @see OAuth2Operations#exchangeForAccess(String, String, org.springframework.util.MultiValueMap)
     */
    public Connection<S> createConnection(AccessGrant accessGrant) {
        String openId = null;
        if(accessGrant instanceof AccessGrantEx) {
            openId = ((AccessGrantEx)accessGrant).getOpenId();
        }
        return new OAuth2ConnectionEx<S>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(), openId);
    }

    /**
     * Create a OAuth2-based {@link Connection} from the connection data.
     * @param data connection data from which to create the connection
     */
    public Connection<S> createConnection(ConnectionData data) {
        return new OAuth2ConnectionEx<S>((ConnectionDataEx)data, getOAuth2ServiceProvider(), getApiAdapter());
    }

    private OAuth2ServiceProviderEx<S> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProviderEx<S>) getServiceProvider();
    }

}
