package com.github.starry.fw.social.starry.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.github.starry.fw.social.starry.api.Starry;
import com.github.starry.fw.social.starry.api.User;

public class StarryAdapter implements ApiAdapter<Starry> {

    public boolean test(Starry starry) {
        try {
            //starry.userOperations().getUserProfile();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }
//
    public void setConnectionValues(Starry starry, ConnectionValues values) {
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


    public UserProfile fetchUserProfile(Starry starry) {
        // TODO 自動生成されたメソッド・スタブ
        System.out.println("fetchUserProfile");
        return null;
    }

    public void updateStatus(Starry starry, String message) {
        // TODO 自動生成されたメソッド・スタブ
        System.out.println("updateStatus");

    }

}

