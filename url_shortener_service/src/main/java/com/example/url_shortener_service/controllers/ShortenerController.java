package com.example.url_shortener_service.controllers;

import com.example.url_shortener_service.dto.ApiResponseDto;
import com.example.url_shortener_service.dto.ShortenerDto;
import com.example.url_shortener_service.services.ShortenerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@RestController
public class ShortenerController {
    @Autowired
    private ShortenerService shortenerService;

    @GetMapping("/{hash}")
    public ResponseEntity<String> redirectToOriginalURL(@PathVariable String hash) {
        try {
            String destination = shortenerService.getOriginalLink(hash);
//            return new RedirectView(shortenerService.getOriginalLink(hash));
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(destination))
                .build();
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/links/{id}/analytics")
    public ResponseEntity<ApiResponseDto<Integer>> getUrlsClicks(@PathVariable Long id) {
        try {
            int urlTotalClicks = shortenerService.getUrlClicks(id);
            ApiResponseDto<Integer> apiResponseDtoSuccess = new ApiResponseDto<>(false, "Total url clicks fetched successfully", urlTotalClicks);
            return new ResponseEntity<>(apiResponseDtoSuccess, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponseDto<Integer> apiResponseDtoError = new ApiResponseDto<>(true, e.getMessage(), null);
            return new ResponseEntity<>(apiResponseDtoError, HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/links/create")
    public ResponseEntity<String> createNewLink(@RequestBody ShortenerDto shortenerDTO) {
        try {
            String shortenedUrl = shortenerService.createNewLink(shortenerDTO);
            return ResponseEntity.ok(shortenedUrl);
        } catch (IllegalArgumentException ex) {
            // IllegalArgumentException indicates bad request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @GetMapping("/links")
    public ResponseEntity<List<ShortenerDto>> getAllLinks() {
        return ResponseEntity.ok(shortenerService.getAllLinks());
    }
}
