package com.serviceImpl;

import com.beans.URLMapping;
import com.exception.URLNotFoundException;
import com.helpers.Base62Encoder;
import com.repository.URLRepository;
import com.services.IURLService;

import java.util.Optional;

public class URLServiceImpl implements IURLService {
    @Override
    public String createShortURL(String longURL) {
        Optional<URLMapping> existing = URLRepository.findByLongURL(longURL);
        if(existing.isPresent()) {
            return "/" + existing.get().getShortURL()
        }
        URLMapping entity = new URLMapping();
        entity.setLongURL(longURL);
        entity = URLRepository.save(entity);

        String shortURL = Base62Encoder.encode(entity.getId());
        entity.setShortURL(shortURL);
        URLRepository.save(entity);
        return "/" + shortURL;
    }

    @Override
    public String generateLongURl(String shortURL) {
        Optional<URLMapping> existing = URLRepository.findByShortURL(shortURL);
        return existing.map(urlMapping -> "/" + urlMapping.getLongURL()).orElseThrow(() -> new URLNotFoundException("Short URL not found : " + shortURL));

    }

    @Override
    public String deactivateShortURL(String shortURL) {
        return "";
    }
}
