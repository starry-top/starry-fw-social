package com.github.starry.fw.social.autoconfigure.starry;

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
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;

import com.github.starry.fw.social.starry.api.Starry;
import com.github.starry.fw.social.starry.connect.StarryConnectionFactory;

@Configuration
@ConditionalOnClass({ SocialConfigurerAdapter.class, StarryConnectionFactory.class })
@ConditionalOnProperty(prefix = "starry.social.starry", name = "app-id")
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class StarryAutoConfiguration {

    @Configuration
    @EnableSocial
    @EnableConfigurationProperties(StarryProperties.class)
    @ConditionalOnWebApplication
    protected static class StarryConfigurerAdapter extends SocialConfigurerAdapter {

        @Autowired
        private StarryProperties properties;

        @Bean
        @ConditionalOnMissingBean(Starry.class)
        @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
        public Starry starry(ConnectionRepository repository) {
            Connection<Starry> connection = repository.findPrimaryConnection(Starry.class);
            return connection != null ? connection.getApi() : null;
        }

        @Bean(name = { "connect/starryConnect", "connect/starryConnected" })
        @ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
        public GenericConnectionStatusView starryConnectView() {
            return new GenericConnectionStatusView("starry", "Starry");
        }

        protected ConnectionFactory<?> createConnectionFactory() {
            return new StarryConnectionFactory(this.properties.getAppId(), this.properties.getAppSecret());
        }

        @Override
        public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
            configurer.addConnectionFactory(createConnectionFactory());
        }

    }

}
