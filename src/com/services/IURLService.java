package com.services;

public interface IURLService {
    public String createShortURL(String longURL);
    public String generateLongURl(String shortURL);
    public String deactivateShortURL(String shortURL);
}
