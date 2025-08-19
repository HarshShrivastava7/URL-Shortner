package com.services;

import com.beans.URLMapping;

public interface IURLService {
    public String createShortURL(String longURL);
    public String findLongURl(String shortURL);
    public void deactivateShortURL(String shortURL);
}
