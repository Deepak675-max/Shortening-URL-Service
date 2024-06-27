package com.example.url_shortener_service.services;

import com.example.url_shortener_service.dto.ShortenerDto;

import java.util.List;

public interface ShortenerService {
    public String createNewLink(ShortenerDto shortenerDTO);
    public List<ShortenerDto> getAllLinks();
    public String getOriginalLink(String hash);
    public int getUrlClicks(Long id);
}
