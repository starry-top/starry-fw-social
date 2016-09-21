package com.github.starry.fw.social.wechat.api;

import org.springframework.social.ApiBinding;

public interface Wechat extends ApiBinding , GraphApi {

    public SnsOperations snsOperations();

}
