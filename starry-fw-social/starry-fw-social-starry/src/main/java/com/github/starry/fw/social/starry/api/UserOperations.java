package com.github.starry.fw.social.starry.api;

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;

public interface UserOperations {

    /**
     * Retrieves the profile for the authenticated user.
     * @return the user's profile information.
     * @throws ApiException if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    User getUserProfile();

    /**
     * Retrieves the profile for the specified user.
     * @param userId the Facebook user ID to retrieve profile data for.
     * @return the user's profile information.
     * @throws ApiException if there is an error while communicating with Facebook.
     */
    User getUserProfile(String userId);

    static final String[] PROFILE_FIELDS = {
        "id", "about", "age_range", "bio", "birthday", "context", "cover", "currency", "devices", "education", "email",
        "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type",
        "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format",
        "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other",
        "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift",
        "website", "work"
    };

}
