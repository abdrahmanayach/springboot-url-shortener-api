package com.example.urlshortener;

import com.example.urlshortener.controller.ShortUrlController;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.service.ShortUrlService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApiApplication.class, args);
	}

}
