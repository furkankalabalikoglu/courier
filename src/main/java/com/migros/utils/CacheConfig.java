package com.migros.utils;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @PostConstruct
    public void postConstruct() {

    }

    /**
     * Guava Configuration
     */
    @Component
    public static class GuavaConfiguration {

        /**
         * GUAVA_24HOUR_CACHE
         *
         * @return Cache Manager
         */
        @Bean("Guava24HourCacheManager")
        public CacheManager guava24HourCacheManager() {
            return getCacheWithTtl(60 * 24);
        }


        /**
         * Cache With Ttl
         *
         * @param ttl time
         * @return Cache Manager
         */
        private CacheManager getCacheWithTtl(int ttl) {
            GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
            guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder().initialCapacity(128).expireAfterWrite(ttl, TimeUnit.MINUTES));
            return guavaCacheManager;
        }
    }
}
