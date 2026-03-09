package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortenRequest;
import com.example.urlshortener.dto.ShortenResponse;
import com.example.urlshortener.dto.UrlInfoResponse;
import com.example.urlshortener.service.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Tag(name = "URL Shortener", description = "API for shortening and managing URLs")
public class ShortUrlController {
    private final ShortUrlService service;

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @Operation(summary = "Shorten a URL")
    @ApiResponse(responseCode = "201", description = "Short URL created successfully")
    @PostMapping("/api/shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortenResponse shorten(@RequestBody ShortenRequest request) {
        return service.shorten(request);
    }

    @Operation(summary = "Redirect to original URL")
    @ApiResponse(responseCode = "302", description = "Redirect to original URL")
    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
    }

    @Operation(summary = "Get URL info")
    @ApiResponse(responseCode = "200", description = "URL info retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Short URL not found")
    @GetMapping("/api/{shortCode}")
    public UrlInfoResponse getUrlInfo(@PathVariable String shortCode) {
        return this.service.getUrlInfo(shortCode);
    }

    @Operation(summary = "Delete a short URL")
    @ApiResponse(responseCode = "204", description = "Short URL deleted successfully")
    @ApiResponse(responseCode = "404", description = "Short URL not found")
    @DeleteMapping("/api/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable String shortCode) {
        this.service.delete(shortCode);
    }
}
