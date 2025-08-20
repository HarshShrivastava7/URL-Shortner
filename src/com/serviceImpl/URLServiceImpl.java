package com.serviceImpl;

import com.exception.URLException;
import com.helpers.Base62Encoder;
import com.services.IURLCacheService;
import com.services.IURLDBService;
import com.services.IURLService;

public class URLServiceImpl implements IURLService {

    IURLDBService urlDBService = new PostgresDBServiceImpl();
    IURLCacheService urlCacheService = new RedisServiceImpl();

    @Override
    public String createShortURL(String longURL) {
        String shortURL = "/harsh/";
        shortURL += Base62Encoder.encode(longURL);
        if (urlDBService.selectLongUrl(shortURL) != null) {
            throw new URLException("URL is already exist. Try again");
        }
        if(urlDBService.insertIntoUrlMapping(shortURL, longURL)) {
            return shortURL;
        }
        else {
            throw new URLException("Error in inserting shortURL");
        }
    }


    @Override
    public String findLongURl(String shortURL) {
        String longURL = urlDBService.selectLongUrl(shortURL);
        if (longURL != null) {
            urlCacheService.setCache(shortURL, 86400, longURL);
            return longURL;
        }
        else {
            throw new URLException("Invalid shortURL");
        }
    }

    @Override
    public void deactivateShortURL(String shortURL) {
        if (urlDBService.updateActiveStatus(false, shortURL)) {
            System.out.println("URLMapping has been deactivated");
        }
        else {
            System.out.println("ShortURL is already deactivated or doesn't exist");
        }
    }
}
