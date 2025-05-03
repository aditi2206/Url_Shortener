package com.UrlShortener.controller;

import com.UrlShortener.service.UrlShortenerService;
import dto.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")  // Base URL for URL shortener API
public class ShortenController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    // Shorten a long URL

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRequest request) {
        try {
            // Generate short code
            String shortCode = urlShortenerService.shortenUrl(request.getOriginalUrl());
            return ResponseEntity.ok("http://localhost:8080/" + shortCode);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}


