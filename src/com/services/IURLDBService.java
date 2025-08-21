package com.services;

import java.util.Map;

public interface IURLDBService {
    public boolean insertIntoUrlMapping(String shortURL, String longURL);
    public String selectLongUrl(String shortURL);
    public boolean updateActiveStatus(boolean status, String shortURL);
    public boolean addAnalytics(Map<String, Long> counters);
}
