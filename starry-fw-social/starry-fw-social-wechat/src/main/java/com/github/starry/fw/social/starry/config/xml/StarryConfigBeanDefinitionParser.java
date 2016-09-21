package com.github.starry.fw.social.starry.config.xml;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.security.provider.SocialAuthenticationService;

import com.github.starry.fw.social.starry.config.support.StarryApiHelper;
import com.github.starry.fw.social.starry.connect.StarryConnectionFactory;
import com.github.starry.fw.social.starry.security.StarryAuthenticationService;

public class StarryConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

    public StarryConfigBeanDefinitionParser() {
        super(StarryConnectionFactory.class, StarryApiHelper.class);
    }

    @Override
    protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
        return StarryAuthenticationService.class;
    }

    @Override
    protected BeanDefinition getConnectionFactoryBeanDefinition(String appId, String appSecret, Map<String, Object> allAttributes) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(StarryConnectionFactory.class).addConstructorArgValue(appId).addConstructorArgValue(appSecret);
        if (allAttributes.containsKey("app-namespace")) {
            builder.addConstructorArgValue(allAttributes.get("app-namespace"));
        }
        return builder.getBeanDefinition();
    }

}
