package com.services;

import java.util.Map;
import java.util.Set;

public interface IURLAnalyticsService {
    public void incrCounter(String key);
    public long getCounter(String key);
    public Map<String, Long> getCounters();
    public void resetCounters(Set<String> keys);
}
