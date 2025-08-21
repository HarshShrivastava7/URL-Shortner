package com.serviceImpl;

import com.helpers.RedisConnectionUtility;
import com.services.IURLAnalyticsService;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisAnalyticsServiceImpl implements IURLAnalyticsService {

    Jedis jedis;
    @Override
    public void incrCounter(String key) {
        jedis = RedisConnectionUtility.getAnalyticsConnection();
        jedis.incr("count:" + key);
    }

    @Override
    public long getCounter(String key) {
        jedis = RedisConnectionUtility.getAnalyticsConnection();
        return Long.parseLong(jedis.get("count:" + key));
    }

    @Override
    public Map<String, Long> getCounters() {
        jedis = RedisConnectionUtility.getAnalyticsConnection();
        var keys = jedis.keys("count:*");
        return keys.stream().collect(
                Collectors.toMap(key -> key.split(":")[1], key -> Long.valueOf(jedis.get(key)))
        );
    }

    @Override
    public void resetCounters(Set<String> keys) {
        jedis = RedisConnectionUtility.getAnalyticsConnection();
        for (String key : keys) {
            jedis.set(key, "0");
        }
    }
}
