package com.github.starry.fw.social.starry.config.xml;

import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.config.xml.AbstractProviderConfigNamespaceHandler;

public class StarryNamespaceHandler extends AbstractProviderConfigNamespaceHandler {

    @Override
    protected AbstractProviderConfigBeanDefinitionParser getProviderConfigBeanDefinitionParser() {
        return new StarryConfigBeanDefinitionParser();
    }

}

