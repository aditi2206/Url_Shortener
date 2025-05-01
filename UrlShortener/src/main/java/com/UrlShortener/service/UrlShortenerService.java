package com.UrlShortener.service;

import com.UrlShortener.entity.Url;
import com.UrlShortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final long OFFSET = 100000; //

    // Shorten a URL
    public String shortenUrl(String originalUrl) {
        // Step 1: Check if URL already exists
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortUrl(); // Return existing short URL
        }

        // Step 2: Create a new short URL
        Url url = new Url();
        url.setOriginalUrl(originalUrl);

        // Save URL to generate ID
        Url savedUrl = urlRepository.save(url);
        System.out.println("Saved URL ID: " + savedUrl.getId());

        // Encode ID to Base62
        String shortUrl = encodeToBase62(savedUrl.getId());

        // Update DB with the shortened URL
        savedUrl.setShortUrl(shortUrl);
        urlRepository.save(savedUrl);

        return shortUrl;
    }

    // Retrieve original URL
    public String getOriginalUrl(String shortUrl) {
        Long id = decodeFromBase62(shortUrl);
        Optional<Url> urlOptional = urlRepository.findById(id);
        return urlOptional.map(Url::getOriginalUrl).orElse(null);
    }

    // Encoding function (Base62 + Offset)
    private String encodeToBase62(Long id) {
        id += OFFSET; // Apply offset
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE62.charAt((int) (id % 62)));
            id /= 62;
        }
        return sb.reverse().toString(); // Reverse to get the correct order
    }

    // Decoding function
    private Long decodeFromBase62(String shortUrl) {
        long id = 0;
        for (char c : shortUrl.toCharArray()) {
            id = id * 62 + BASE62.indexOf(c);
        }
        return id - OFFSET; // Remove offset
    }
}

