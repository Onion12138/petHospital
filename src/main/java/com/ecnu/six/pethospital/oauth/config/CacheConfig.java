package com.ecnu.six.pethospital.oauth.config;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc 不使用本地缓存包，从简
 * @since
 */
@Component
@Slf4j
public class CacheConfig {

    public ConcurrentHashMap<String, Timestamp> userTokenCache;

    public ConcurrentHashSet<String> adminToken;

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);

    private volatile ShutDownHook _hook;

    @PostConstruct
    public void init() {
        userTokenCache = new ConcurrentHashMap<>();
        adminToken = new ConcurrentHashSet<>();
        EXECUTOR_SERVICE.submit(() -> {
            for (;;) {
                try {
                    Thread.sleep( 3600 * 1000);
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                        // ignore
                        log.info("CacheConfig -> EXECUTOR_SERVICE interrupted");
                    }else {
                        log.error("CacheConfig -> EXECUTOR_SERVICE exception", e);
                    }
                    break;
                }
                log.info("当前清理数量为 {}", adminToken.size());
                if (adminToken == null) break;
                adminToken.clear();
            }
        });
        Runtime.getRuntime().addShutdownHook((_hook = new ShutDownHook(this)));
    }

    private void ShotDown() {
        userTokenCache.clear();
        adminToken.clear();
        userTokenCache = null;
        adminToken = null;
        EXECUTOR_SERVICE.shutdownNow();
    }

    class ShutDownHook extends Thread {

        private CacheConfig _cache;

        /**
         * Allocates a new {@code Thread} object. This constructor has the same
         * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
         * {@code (null, null, gname)}, where {@code gname} is a newly generated
         * name. Automatically generated names are of the form
         * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
         */
        public ShutDownHook(CacheConfig _cache) {
            this._cache = _cache;
        }

        /**
         * If this thread was constructed using a separate
         * {@code Runnable} run object, then that
         * {@code Runnable} object's {@code run} method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of {@code Thread} should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
            log.info("shutdown cache ====>");
            _hook = null;
            _cache.ShotDown();
            log.info("<==== cache shutdown completely");
        }
    }
}
