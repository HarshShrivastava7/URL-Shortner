package com.services;

import java.util.Map;

public interface IFlushCounterService {
    public void flushToDB(Map<String, Long> counters);
}
