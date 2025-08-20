package com.serviceImpl;

import com.helpers.Base62Encoder;
import com.helpers.ConnectionUtility;
import com.helpers.RedisConnectionUtility;
import com.services.IURLDBService;
import com.services.IURLService;
import redis.clients.jedis.Jedis;

import java.sql.*;

public class URLServiceImpl implements IURLService {

    URLDBServiceImpl urlDBService = new URLDBServiceImpl();
    @Override
    public String createShortURL(String longURL) {
        String shortURL = "/harsh/";
        shortURL += Base62Encoder.encode(longURL);
        if(urlDBService.insertIntoUrlMapping(shortURL, longURL)) {
            return shortURL;
        }
        else {
            throw new RuntimeException("Error in inserting shortURL");
        }
    }


    @Override
    public String findLongURl(String shortURL) {
        String longURL = urlDBService.selectLongUrl(shortURL);
        if (longURL != null) {
            return longURL;
        }
        else {
            throw new RuntimeException("Invalid shortURL");
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
