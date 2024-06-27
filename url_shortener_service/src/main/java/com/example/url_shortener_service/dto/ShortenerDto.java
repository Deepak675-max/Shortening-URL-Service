package com.example.url_shortener_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenerDto {
    private Long id;
    private String title;
    private String hash;
    private String destination;
    private Long userId;
    private String shortURL;

    public ShortenerDto(Long id, String destination, String title, String shortURL) {
        this.id = id;
        this.destination = destination;
        this.title = title;
        this.shortURL = shortURL;
    }
}
