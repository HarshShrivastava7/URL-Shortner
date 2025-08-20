package com.controllers;

import com.helpers.RedisRateLimiter;
import com.serviceImpl.URLServiceImpl;
import com.services.IURLService;

import java.util.Scanner;

public class URLController {
    IURLService urlService = new URLServiceImpl();

    public void createShortURL(Scanner sc) {
        System.out.println("Please enter the URL");
        String longURL = sc.next();

        try {
            String shortURL = urlService.createShortURL(longURL);
            System.out.println("Your short url is : " + shortURL);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void findLongURl(Scanner sc) {
        System.out.println("Please enter the Short URL");
        String shortURL = sc.next();

        try {
            System.out.println(urlService.findLongURl(shortURL));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deactivateShortURL(Scanner sc) {
        System.out.println("Please enter the Short URL");
        String shortURL = sc.next();
        try {
            urlService.deactivateShortURL(shortURL);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
