package com.github.starry.fw.social.wechat.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.github.starry.fw.social.wechat.api.UserInfo;
import com.github.starry.fw.social.wechat.api.Wechat;

public class WechatAdapter implements ApiAdapter<Wechat> {

    public boolean test(Wechat wechat) {
        try {
            wechat.snsOperations().getUserInfo();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }
//
    public void setConnectionValues(Wechat wechat, ConnectionValues values) {
        UserInfo userInfo = wechat.snsOperations().getUserInfo();
        values.setProviderUserId(userInfo.getOpenid());
        values.setDisplayName(userInfo.getNickname());
        values.setProfileUrl(null);
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    public UserProfile fetchUserProfile(Wechat wechat) {
        UserInfo userInfo = wechat.snsOperations().getUserInfo();
        return new UserProfileBuilder().setId(userInfo.getOpenid()).setName(userInfo.getNickname()).build();
    }

    public void updateStatus(Wechat wechat, String message) {
    }

}

