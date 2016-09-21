package com.github.starry.fw.social.wechat.api;

import org.springframework.social.ApiBinding;
import org.springframework.util.MultiValueMap;

public interface Wechat extends ApiBinding {

    /**
     * Fetches an object, extracting it into the given Java type
     * Requires appropriate permission to fetch the object.
     * @param objectId the Facebook object's ID
     * @param type the Java type to fetch
     * @param <T> The Java type to bind the Facebook object to
     * @return an Java object representing the requested Facebook object.
     */
    <T> T fetchObject(String objectId, Class<T> type);

    /**
     * Fetches an object, extracting it into the given Java type
     * Requires appropriate permission to fetch the object.
     * @param objectId the Facebook object's ID
     * @param type the Java type to fetch
     * @param fields the fields to include in the response.
     * @param <T> The Java type to bind the Facebook object to
     * @return an Java object representing the requested Facebook object.
     */
    <T> T fetchObject(String objectId, Class<T> type, String... fields);

    /**
     * Fetches an object, extracting it into the given Java type
     * Requires appropriate permission to fetch the object.
     * @param objectId the Facebook object's ID
     * @param type the Java type to fetch
     * @param queryParameters query parameters to include in the request
     * @param <T> The Java type to bind the Facebook object to
     * @return an Java object representing the requested Facebook object.
     */
    <T> T fetchObject(String objectId, Class<T> type, MultiValueMap<String, String> queryParameters);

}
