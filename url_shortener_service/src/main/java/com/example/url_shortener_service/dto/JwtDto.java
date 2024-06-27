package com.example.url_shortener_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDto {
    private String jwtToken;
    private Long expirationTime;
}
