package com.github.starry.fw.social.wechat.api.impl;

import java.net.URI;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.github.starry.fw.social.wechat.api.SnsOperations;
import com.github.starry.fw.social.wechat.api.Wechat;

public class WechatTemplate extends AbstractOAuth2ApiBinding implements Wechat {

    private String appId;
    private String applicationNamespace;
    private String openId;

    private SnsOperations snsOperations;

    /**
     * Create a new instance of FacebookTemplate.
     * This constructor creates the FacebookTemplate using a given access token.
     * @param accessToken An access token given by Facebook after a successful OAuth 2 authentication (or through Facebook's JS library).
     */
    public WechatTemplate(String accessToken, String openId) {
        this(accessToken, openId, null);
    }

    public WechatTemplate(String accessToken, String openId,String applicationNamespace) {
        this(accessToken, openId, applicationNamespace, null);
    }

    public WechatTemplate(String accessToken, String openId, String applicationNamespace, String appId) {
        super(accessToken);
        this.applicationNamespace = applicationNamespace;
        this.appId = appId;
        this.openId = openId;
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
         snsOperations = new SnsTemplate(this,getRestTemplate());
     }

     public SnsOperations snsOperations() {
         return snsOperations;
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
         return "https://api.weixin.qq.com/";
     }

    public String getOpenId() {
        return openId;
    }

}
