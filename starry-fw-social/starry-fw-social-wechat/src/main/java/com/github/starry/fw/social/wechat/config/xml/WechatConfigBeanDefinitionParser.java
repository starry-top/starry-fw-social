package com.github.starry.fw.social.wechat.config.xml;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.security.provider.SocialAuthenticationService;

import com.github.starry.fw.social.wechat.config.support.WechatApiHelper;
import com.github.starry.fw.social.wechat.connect.WechatConnectionFactory;
import com.github.starry.fw.social.wechat.security.WechatAuthenticationService;

public class WechatConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

    public WechatConfigBeanDefinitionParser() {
        super(WechatConnectionFactory.class, WechatApiHelper.class);
    }

    @Override
    protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
        return WechatAuthenticationService.class;
    }

    @Override
    protected BeanDefinition getConnectionFactoryBeanDefinition(String appId, String appSecret, Map<String, Object> allAttributes) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WechatConnectionFactory.class).addConstructorArgValue(appId).addConstructorArgValue(appSecret);
        if (allAttributes.containsKey("app-namespace")) {
            builder.addConstructorArgValue(allAttributes.get("app-namespace"));
        }
        return builder.getBeanDefinition();
    }

}
