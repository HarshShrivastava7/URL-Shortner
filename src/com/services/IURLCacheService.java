package com.services;

public interface IURLCacheService {
    public boolean setCache(String key, long ttl, String value);
    public String getCache(String key);
}
