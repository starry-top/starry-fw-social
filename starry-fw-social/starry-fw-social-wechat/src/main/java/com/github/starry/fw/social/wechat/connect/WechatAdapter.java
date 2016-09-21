package com.github.starry.fw.social.wechat.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.github.starry.fw.social.wechat.api.User;
import com.github.starry.fw.social.wechat.api.Wechat;

public class WechatAdapter implements ApiAdapter<Wechat> {

    public boolean test(Wechat starry) {
        try {
            //starry.userOperations().getUserProfile();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }
//
    public void setConnectionValues(Wechat starry, ConnectionValues values) {
        User profile = starry.fetchObject("me", User.class);
        values.setDisplayName(profile.getUserInfo().getName());
    }
//
//	public UserProfile fetchUserProfile(Facebook facebook) {
//		User profile = facebook.userOperations().getUserProfile();
//		return new UserProfileBuilder().setId(profile.getId()).setName(profile.getName()).setFirstName(profile.getFirstName()).setLastName(profile.getLastName()).
//			setEmail(profile.getEmail()).build();
//	}
//
//	public void updateStatus(Starry facebook, String message) {
//		facebook.feedOperations().updateStatus(message);
//	}


    public UserProfile fetchUserProfile(Wechat starry) {
        // TODO 自動生成されたメソッド・スタブ
        System.out.println("fetchUserProfile");
        return null;
    }

    public void updateStatus(Wechat starry, String message) {
        // TODO 自動生成されたメソッド・スタブ
        System.out.println("updateStatus");

    }

}

