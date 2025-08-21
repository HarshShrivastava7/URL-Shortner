package com.helpers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionUtility {
    private static JedisPool pool;
    private static JedisPool analyticsPool;

    //initialize pool

    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(20); // max connections
            config.setMaxIdle(10); // max idle connections
            config.setMinIdle(2); // min idle connections

            // connect to redis (default - localhost:6379
            pool = new JedisPool(config,"localhost", 6379);
            System.out.println("Redis Connection Successful");

            //connect to redis (analytics - localhost:6380)
            analyticsPool = new JedisPool(config,"localhost", 6380);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // get jedis resource
    public  static Jedis getConnection()
    {
        return pool.getResource();
    }

    // close pool or shutdown
    public static void closePool() {
        if (pool != null) {
            pool.close();
        }
    }

    //get analytics jedis resource
    public static Jedis getAnalyticsConnection() {
        return analyticsPool.getResource();
    }

    // close analytics pool or shutdown
    public static void closeAnalyticsPool() {
        if (analyticsPool != null) {
            analyticsPool.close();
        }
    }
}
