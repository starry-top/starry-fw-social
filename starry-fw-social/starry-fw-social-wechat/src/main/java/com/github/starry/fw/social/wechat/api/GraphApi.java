package com.github.starry.fw.social.wechat.api;

import org.springframework.util.MultiValueMap;

public interface GraphApi {

    <T> T fetchObject(String objectId, Class<T> type);

    <T> T fetchObject(String objectId, Class<T> type, String... fields);

    <T> T fetchObject(String objectId, Class<T> type, MultiValueMap<String, String> queryParameters);

    String getOpenId();


}
