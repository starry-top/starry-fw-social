package com.github.starry.fw.social.starry.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import com.github.starry.fw.social.starry.api.Starry;
import com.github.starry.fw.social.starry.api.impl.StarryTemplate;

public class StarryServiceProvider extends AbstractOAuth2ServiceProvider<Starry> {

    private String appNamespace;

    /**
     * Creates a FacebookServiceProvider for the given application ID, secret, and namespace.
     * @param appId The application's App ID as assigned by Facebook
     * @param appSecret The application's App Secret as assigned by Facebook
     * @param appNamespace The application's App Namespace as configured with Facebook. Enables use of Open Graph operations.
     */
    public StarryServiceProvider(String appId, String appSecret, String appNamespace) {
        super(getOAuth2Template(appId, appSecret));
        this.appNamespace = appNamespace;
    }

    private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
        OAuth2Template oAuth2Template = new OAuth2Template(appId, appSecret,
                "http://localhost:8081/server/oauth/authorize",
                "http://localhost:8081/server/oauth/token");
        oAuth2Template.setUseParametersForClientAuthentication(false);
        return oAuth2Template;
    }

    // http://localhost:8081/me

    public Starry getApi(String accessToken) {
        return new StarryTemplate(accessToken, appNamespace);
    }

}