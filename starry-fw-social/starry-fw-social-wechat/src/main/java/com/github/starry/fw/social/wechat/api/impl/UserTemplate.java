package com.github.starry.fw.social.wechat.api.impl;

import org.springframework.web.client.RestTemplate;

import com.github.starry.fw.social.wechat.api.User;
import com.github.starry.fw.social.wechat.api.UserOperations;

public class UserTemplate implements UserOperations {



    private final RestTemplate restTemplate;

    public UserTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserProfile() {
        return getUserProfile("me");
    }

    public User getUserProfile(String facebookId) {

        return new User();
    }
}
