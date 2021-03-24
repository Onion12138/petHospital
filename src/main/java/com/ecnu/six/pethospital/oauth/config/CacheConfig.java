package com.ecnu.six.pethospital.oauth.config;

import cn.hutool.core.collection.ConcurrentHashSet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Component
public class CacheConfig {

    public ConcurrentHashMap<String, Timestamp> userTokenCache;

    public ConcurrentHashSet<String> adminToken;

    @PostConstruct
    public void init() {
        userTokenCache = new ConcurrentHashMap<>();
        adminToken = new ConcurrentHashSet<>();
    }
}
