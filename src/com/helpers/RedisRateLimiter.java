package com.helpers;

import redis.clients.jedis.Jedis;

public class RedisRateLimiter {
    public static boolean isAllowed(String key, int limit, int windowSec) {

        try(Jedis jedis = RedisConnectionUtility.getConnection()) {
            long count = jedis.incr(key);
            if (count == 1) jedis.expire(key, windowSec);
            return count <= limit;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
