package com.serviceImpl;

import com.helpers.RedisConnectionUtility;
import com.services.IURLCacheService;
import redis.clients.jedis.Jedis;

public class RedisServiceImpl implements IURLCacheService {
    Jedis jedis;

    @Override
    public void setCache(String key, long ttl, String value){
        jedis = RedisConnectionUtility.getConnection();
        jedis.setex(key, ttl, value);
    }

    @Override
    public String getCache(String key){
        jedis = RedisConnectionUtility.getConnection();
        return jedis.get(key);
    }
}
