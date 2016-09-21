package com.github.starry.fw.social.wechat.connect.extend;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.core.GenericTypeResolver;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.AbstractConnection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class OAuth2ConnectionEx<A> extends AbstractConnection<A> {

    private static final long serialVersionUID = 4057584084077577480L;

    private transient final OAuth2ServiceProviderEx<A> serviceProvider;

    private String accessToken;

    private String refreshToken;

    private Long expireTime;

    private transient A api;

    private transient A apiProxy;

    private String openId;

    /**
     * Creates a new {@link OAuth2Connection} from a access grant response.
     * Designed to be called to establish a new {@link OAuth2Connection} after receiving an access grant successfully.
     * The providerUserId may be null in this case: if so, this constructor will try to resolve it using the service API obtained from the {@link OAuth2ServiceProvider}.
     * @param providerId the provider id e.g. "facebook".
     * @param providerUserId the provider user id (may be null if not returned as part of the access grant)
     * @param accessToken the granted access token
     * @param refreshToken the granted refresh token
     * @param expireTime the access token expiration time
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public OAuth2ConnectionEx(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime,
            OAuth2ServiceProviderEx<A> serviceProvider, ApiAdapter<A> apiAdapter, String openId) {
        super(apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(accessToken, refreshToken, expireTime, openId);
        initApi();
        initApiProxy();
        initKey(providerId, providerUserId);
    }

    /**
     * Creates a new {@link OAuth2Connection} from the data provided.
     * Designed to be called when re-constituting an existing {@link Connection} from {@link ConnectionData}.
     * @param data the data holding the state of this connection
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public OAuth2ConnectionEx(ConnectionDataEx data, OAuth2ServiceProviderEx<A> serviceProvider, ApiAdapter<A> apiAdapter) {
        super(data, apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(data.getAccessToken(), data.getRefreshToken(), data.getExpireTime(),data.getOpenId());
        initApi();
        initApiProxy();
    }

    // implementing Connection

    public boolean hasExpired() {
        synchronized (getMonitor()) {
            return expireTime != null && System.currentTimeMillis() >= expireTime;
        }
    }

    public void refresh() {
        synchronized (getMonitor()) {
            AccessGrant accessGrant = serviceProvider.getOAuthOperations().refreshAccess(refreshToken, null);
            String openId = null;
            if(accessGrant instanceof AccessGrantEx) {
                openId = ((AccessGrantEx)accessGrant).getOpenId();
            }
            initAccessTokens(accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime(), openId);
            initApi();
        }
    }

    public A getApi() {
        if (apiProxy != null) {
            return apiProxy;
        } else {
            synchronized (getMonitor()) {
                return api;
            }
        }
    }

    public ConnectionData createData() {
        synchronized (getMonitor()) {
            return new ConnectionDataEx(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken, null, refreshToken, expireTime, openId);
        }
    }

    // internal helpers

    private void initAccessTokens(String accessToken, String refreshToken, Long expireTime, String openId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
        this.openId = openId;
    }

    private void initApi() {
        api = serviceProvider.getApi(accessToken, openId);
    }

    @SuppressWarnings("unchecked")
    private void initApiProxy() {
        Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
        if (apiType.isInterface()) {
            apiProxy = (A) Proxy.newProxyInstance(apiType.getClassLoader(), new Class<?>[] { apiType }, new ApiInvocationHandler());
        }
    }

    private class ApiInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            synchronized (getMonitor()) {
                if (hasExpired()) {
                    throw new ExpiredAuthorizationException(getKey().getProviderId());
                }
                try {
                    return method.invoke(OAuth2ConnectionEx.this.api, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
    }

    // equas() and hashCode() generated by Eclipse
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
        result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        @SuppressWarnings("rawtypes")
        OAuth2ConnectionEx other = (OAuth2ConnectionEx) obj;

        if (accessToken == null) {
            if (other.accessToken != null) return false;
        } else if (!accessToken.equals(other.accessToken)) return false;

        if (expireTime == null) {
            if (other.expireTime != null) return false;
        } else if (!expireTime.equals(other.expireTime)) return false;

        if (refreshToken == null) {
            if (other.refreshToken != null) return false;
        } else if (!refreshToken.equals(other.refreshToken)) return false;

        return true;
    }


}
