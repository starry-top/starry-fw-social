package com.github.starry.fw.social.starry.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.github.starry.fw.social.starry.api.Starry;

public class StarryConnectionFactory extends OAuth2ConnectionFactory<Starry> {

    /**
     * Creates a FacebookConnectionFactory for the given application ID and secret.
     * Using this constructor, no application namespace is set (and therefore Facebook's Open Graph operations cannot be used).
     * @param appId The application's App ID as assigned by Facebook
     * @param appSecret The application's App Secret as assigned by Facebook
     */
    public StarryConnectionFactory(String appId, String appSecret) {
        this(appId, appSecret, null);
    }

    /**
     * Creates a FacebookConnectionFactory for the given application ID, secret, and namespace.
     * @param appId The application's App ID as assigned by Facebook
     * @param appSecret The application's App Secret as assigned by Facebook
     * @param appNamespace The application's App Namespace as configured with Facebook. Enables use of Open Graph operations.
     */
    public StarryConnectionFactory(String appId, String appSecret, String appNamespace) {
        super("Starry", new StarryServiceProvider(appId, appSecret, appNamespace), new StarryAdapter());
    }

}
