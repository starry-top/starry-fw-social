package com.github.starry.fw.social.starry.security;

import org.springframework.social.security.provider.OAuth2AuthenticationService;

import com.github.starry.fw.social.starry.api.Starry;
import com.github.starry.fw.social.starry.connect.StarryConnectionFactory;

public class StarryAuthenticationService extends OAuth2AuthenticationService<Starry> {

    public StarryAuthenticationService(String apiKey, String appSecret) {
        super(new StarryConnectionFactory(apiKey, appSecret));
    }

    public StarryAuthenticationService(String apiKey, String appSecret, String appNamespace) {
        super(new StarryConnectionFactory(apiKey, appSecret, appNamespace));
    }

}
