package com.github.starry.fw.social.wechat.connect.extend;

import org.springframework.social.oauth2.AccessGrant;

public class AccessGrantEx extends AccessGrant {

    private final String openId;

    public AccessGrantEx(String accessToken, String scope,
            String refreshToken, Long expiresIn, String openId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }


}
