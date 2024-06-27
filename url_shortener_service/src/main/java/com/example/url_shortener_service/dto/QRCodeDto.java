package com.example.url_shortener_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeDto {
    private Long id;
    private String title;
    private String destination;
    private Integer userId;
    private String shortURL;
}
