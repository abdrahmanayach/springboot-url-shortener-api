package com.example.urlshortener.service;

import com.example.urlshortener.dto.ShortenRequest;
import com.example.urlshortener.dto.ShortenResponse;
import com.example.urlshortener.dto.UrlInfoResponse;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.exception.ShortUrlNotFoundException;
import com.example.urlshortener.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class ShortUrlService {
    private final ShortUrlRepository repository;

    @Value("${app.base-url}")
    private String baseUrl;

    public ShortUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortenResponse shorten(ShortenRequest request) {
        String shortCode = generateUniqueShortCode();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(request.originalUrl());
        shortUrl.setShortCode(shortCode);
        shortUrl.setClickCount(0L);
        shortUrl.setCreatedAt(LocalDateTime.now());
        repository.save(shortUrl);

        return new ShortenResponse(shortCode, baseUrl + "/" + shortCode);
    }

    public String getOriginalUrl(String shortCode) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);
        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        this.repository.save(shortUrl);
        return shortUrl.getOriginalUrl();
    }

    public UrlInfoResponse getUrlInfo(String shortCode) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);
        return new UrlInfoResponse(shortUrl.getOriginalUrl(), shortUrl.getClickCount());
    }

    public void delete(String shortCode) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);
        repository.deleteById(shortUrl.getId());
    }

    private ShortUrl findByShortCodeOrThrow(String shortCode) {
        return this.repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));
    }

    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while(this.repository.existsByShortCode(shortCode));
        return shortCode;
    }

    private String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            int x = random.nextInt(chars.length());
            char c = chars.charAt(x);
            sb.append(c);
        }
        return sb.toString();
    }
}
