package com.UrlShortener.service;

import com.UrlShortener.entity.Url;
import com.UrlShortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    // Create short code from URL
    public String shortenUrl(String longUrl) throws Exception {
        String shortCode = generateMD5ShortCode(longUrl, 10);

        // Check if it already exists
        if (urlRepository.existsById(shortCode)) {
            return shortCode;
        }

        Url url = new Url(shortCode, longUrl);
        urlRepository.save(url);

        return shortCode;   
    }

    // Expand short code to full URL
    public Optional<String> getLongUrl(String shortCode) {
        return urlRepository.findById(shortCode).map(Url::getOriginalUrl);
    }

    private String generateMD5ShortCode(String input, int length) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }

        return sb.substring(0, Math.min(length, sb.length()));
    }
}

