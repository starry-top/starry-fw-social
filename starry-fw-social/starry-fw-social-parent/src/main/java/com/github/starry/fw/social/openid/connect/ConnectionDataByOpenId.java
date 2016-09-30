package com.github.starry.fw.social.openid.connect;

import org.springframework.social.connect.ConnectionData;

public class ConnectionDataByOpenId extends ConnectionData {

    private String openId;

    public ConnectionDataByOpenId(String providerId, String providerUserId,
            String displayName, String profileUrl, String imageUrl,
            String accessToken, String secret, String refreshToken,
            Long expireTime, String openId) {
        super(providerId, providerUserId, displayName, profileUrl, imageUrl,
                accessToken, secret, refreshToken, expireTime);
        this.openId  = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
