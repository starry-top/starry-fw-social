package com.github.starry.fw.social.wechat.connect.extend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class WechatOAuth2Template extends OAuth2Template {

    private final String authorizeUrl;

    public WechatOAuth2Template(String clientId, String clientSecret,String authorizeUrl, String accessTokenUrl) {
        this(clientId, clientSecret, authorizeUrl, null, accessTokenUrl);
    }

    public WechatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
        String clientInfo = "?appid=" + formEncode(clientId);
        this.authorizeUrl = authorizeUrl + clientInfo;
    }

    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthUrl(authorizeUrl, GrantType.AUTHORIZATION_CODE, parameters);
    }

    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        return buildAuthUrl(authorizeUrl, grantType, parameters);
    }

    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        return new AccessGrantEx(accessToken, scope, refreshToken, expiresIn, (String) response.get("openid"));
    }

    @SuppressWarnings("unchecked")
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        if(parameters.containsKey("client_id")) {
            params.set("appid", parameters.getFirst("client_id"));
            parameters.remove("client_id");
        }

        if(parameters.containsKey("clientSecret")) {
            params.set("secret", parameters.getFirst("clientSecret"));
            parameters.remove("clientSecret");
        }
        params.putAll(parameters);

        return super.postForAccessGrant(accessTokenUrl, params);
    }

    private String buildAuthUrl(String baseAuthUrl, GrantType grantType, OAuth2Parameters parameters) {
        StringBuilder authUrl = new StringBuilder(baseAuthUrl);

        String[] authUrlParamKeys = new String[]{"redirect_uri", "response_type" ,"scope", "state"};

        if (grantType == GrantType.AUTHORIZATION_CODE) {
            parameters.put("response_type", Arrays.asList("code"));
        } else if (grantType == GrantType.IMPLICIT_GRANT) {
            parameters.put("response_type", Arrays.asList("token"));
        }

        for(String paramKey : authUrlParamKeys) {
            List<String> values = parameters.get(paramKey);
            for(String value : values) {
                authUrl.append('&').append(formEncode(paramKey));
                authUrl.append('=').append(formEncode(value));
            }
        }

        authUrl.append("#wechat_redirect");

        return authUrl.toString();
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

}
