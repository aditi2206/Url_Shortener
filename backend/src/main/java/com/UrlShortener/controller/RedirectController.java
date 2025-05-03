package com.UrlShortener.controller;

import com.UrlShortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
public class RedirectController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    // Redirect to original URL based on short code
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getLongUrl(shortCode);

        if (originalUrl.isPresent()) {
            // Redirect to the original URL
            URI targetUrl = URI.create(originalUrl.get());
            return ResponseEntity.status(302).location(targetUrl).build();
        } else {
            // If not found, return 404
            return ResponseEntity.notFound().build();
        }
    }

}
