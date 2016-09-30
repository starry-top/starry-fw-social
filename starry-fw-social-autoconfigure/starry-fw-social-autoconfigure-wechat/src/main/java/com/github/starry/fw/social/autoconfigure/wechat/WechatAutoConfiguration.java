package com.github.starry.fw.social.autoconfigure.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import com.github.starry.fw.social.wechat.api.Wechat;
import com.github.starry.fw.social.wechat.connect.WechatConnectionFactory;

@Configuration
@ConditionalOnClass({ SocialConfigurerAdapter.class, WechatConnectionFactory.class })
@ConditionalOnProperty(prefix = "starry.social.wechat", name = "app-id")
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WechatAutoConfiguration {

    @Configuration
    @EnableSocial
    @EnableConfigurationProperties(WechatProperties.class)
    @ConditionalOnWebApplication
    protected static class WechatConfigurerAdapter extends SocialConfigurerAdapter {

        @Autowired
        private WechatProperties properties;

        @Bean
        @ConditionalOnMissingBean(Wechat.class)
        @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
        public Wechat starry(ConnectionRepository repository) {
            Connection<Wechat> connection = repository.findPrimaryConnection(Wechat.class);
            return connection != null ? connection.getApi() : null;
        }

        @Bean(name = { "connect/wechatConnect", "connect/wechatConnected" })
        @ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
        public GenericConnectionStatusView wechatConnectView() {
            return new GenericConnectionStatusView("wechat", "Wechat");
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "starry.social.wechat", name = "token")
        public BeanNameViewResolver beanNameViewResolver() {
            BeanNameViewResolver viewResolver = new BeanNameViewResolver();
            viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return viewResolver;
        }

        @Bean(name = { "verify/wechat"})
        @ConditionalOnProperty(prefix = "starry.social.wechat", name = "token")
        public WechatVerifyView verifyConnectView() {
            return new WechatVerifyView(properties.getToken());
        }

        @Bean
        @ConditionalOnMissingBean(VerifyController.class)
        public VerifyController verifyController() {
            VerifyController controller = new VerifyController();
            return controller;
        }

        protected ConnectionFactory<?> createConnectionFactory() {
            return new WechatConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret());
        }

        @Override
        public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
            configurer.addConnectionFactory(createConnectionFactory());
        }

    }

}
