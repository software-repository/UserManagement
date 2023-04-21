package com.jas.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserCacheScheduler {


    @Autowired
    CacheManager cacheManager;
    public void evictAllCaches() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
    @Scheduled(cron = "0 0 2 * * *")
    public void evictAllcachesAtIntervals() {
        evictAllCaches();
    }

}
