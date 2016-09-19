package com.github.starry.fw.social.starry.api.impl;

import java.net.URI;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.github.starry.fw.social.starry.api.Starry;
import com.github.starry.fw.social.starry.api.UserOperations;

public class StarryTemplate extends AbstractOAuth2ApiBinding implements Starry {

    private String appId;
    private String applicationNamespace;

    private UserOperations userOperations;

    /**
     * Create a new instance of FacebookTemplate.
     * This constructor creates the FacebookTemplate using a given access token.
     * @param accessToken An access token given by Facebook after a successful OAuth 2 authentication (or through Facebook's JS library).
     */
    public StarryTemplate(String accessToken) {
        this(accessToken, null);
    }

    public StarryTemplate(String accessToken, String applicationNamespace) {
        this(accessToken, applicationNamespace, null);
    }

    public StarryTemplate(String accessToken, String applicationNamespace, String appId) {
        super(accessToken);
        this.applicationNamespace = applicationNamespace;
        this.appId = appId;
        //initialize();
    }

    @Override
    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        // Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
    }

     // private helpers
     private void initialize() {
         // Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
         super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
         initSubApis();
     }

     private void initSubApis() {
         userOperations = new UserTemplate(getRestTemplate());
     }

     public UserOperations userOperations() {
         return userOperations;
     }

     // low-level Graph API operations
     public <T> T fetchObject(String objectId, Class<T> type) {
         URI uri = URIBuilder.fromUri(getBaseGraphApiUrl() + objectId).build();
         return getRestTemplate().getForObject(uri, type);
     }

     public <T> T fetchObject(String objectId, Class<T> type, String... fields) {
         MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
         if(fields.length > 0) {
             String joinedFields = join(fields);
             queryParameters.set("fields", joinedFields);
         }
         return fetchObject(objectId, type, queryParameters);
     }

     public <T> T fetchObject(String objectId, Class<T> type, MultiValueMap<String, String> queryParameters) {
         URI uri = URIBuilder.fromUri(getBaseGraphApiUrl() + objectId).queryParams(queryParameters).build();
         return getRestTemplate().getForObject(uri, type);
     }

     private String join(String[] strings) {
         StringBuilder builder = new StringBuilder();
         if(strings.length > 0) {
             builder.append(strings[0]);
             for (int i = 1; i < strings.length; i++) {
                 builder.append("," + strings[i]);
             }
         }
         return builder.toString();
     }

     public String getBaseGraphApiUrl() {
         return "http://localhost:8081/server/";
     }

}
