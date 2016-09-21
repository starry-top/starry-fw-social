package com.github.starry.fw.social.wechat.config.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;

import com.github.starry.fw.social.wechat.api.Wechat;

public class WechatApiHelper implements ApiHelper<Wechat> {

    private final UsersConnectionRepository usersConnectionRepository;

    private final UserIdSource userIdSource;

    public WechatApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.userIdSource = userIdSource;
    }

    public Wechat getApi() {
        if (logger.isDebugEnabled()) {
            logger.debug("Getting API binding instance for Starry");
        }

        Connection<Wechat> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(Wechat.class);
        if (logger.isDebugEnabled() && connection == null) {
            logger.debug("No current connection; Returning default StarryTemplate instance.");
        }
        return connection != null ? connection.getApi() : null;
    }

    private static final Logger logger = LoggerFactory.getLogger(WechatApiHelper.class);

}
