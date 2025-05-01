package com.UrlShortener.controller;

import com.UrlShortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlShortenerService urlShortenerService;


    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        String shortUrl = urlShortenerService.shortenUrl(originalUrl);
        return ResponseEntity.ok(shortUrl);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);
        return originalUrl != null ? ResponseEntity.ok(originalUrl) : ResponseEntity.notFound().build();
    }
}

