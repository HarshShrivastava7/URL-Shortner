package com.serviceImpl;

import com.services.IFlushCounterService;

import java.util.Map;

public class FlushCounterServiceImpl implements IFlushCounterService {
    PostgresDBServiceImpl urlDbService = new PostgresDBServiceImpl();
    RedisAnalyticsServiceImpl redisAnalyticsService = new RedisAnalyticsServiceImpl();

    @Override
    public void flushToDB(Map<String, Long> counters){
        if(urlDbService.addAnalytics(counters)) {
            System.out.println("Successfully added analytics to database.");
            redisAnalyticsService.resetCounters(counters.keySet());
        }
        else {
            throw new RuntimeException("Failed to add analytics to database.");
        }
    }
}
