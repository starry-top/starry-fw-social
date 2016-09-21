package com.github.starry.fw.social.wechat.api.impl;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.github.starry.fw.social.wechat.api.GraphApi;
import com.github.starry.fw.social.wechat.api.SnsOperations;
import com.github.starry.fw.social.wechat.api.UserInfo;

public class SnsTemplate implements SnsOperations {

    private final GraphApi graphApi;

    private final RestTemplate restTemplate;

    public SnsTemplate(GraphApi graphApi, RestTemplate restTemplate) {
        this.graphApi = graphApi;
        this.restTemplate = restTemplate;
    }

    public UserInfo getUserInfo() {
        return getUserInfo("zh_CN");
    }

    public UserInfo getUserInfo(String lang) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("openid",graphApi.getOpenId());
        params.set("lang","zh_CN");
        return graphApi.fetchObject("sns/userinfo", UserInfo.class, params);
    }

}
