package com.example.url_shortener_service.controllers;

import com.example.url_shortener_service.dto.ApiResponseDto;
import com.example.url_shortener_service.dto.JwtDto;
import com.example.url_shortener_service.dto.LoginUserDto;
import com.example.url_shortener_service.dto.RegisterUserDto;
import com.example.url_shortener_service.entities.UserEntity;
import com.example.url_shortener_service.services.AuthenticationService;
import com.example.url_shortener_service.services.JwtService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Data
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserEntity>> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            UserEntity registeredUser = authenticationService.signup(registerUserDto);
            ApiResponseDto<UserEntity> apiResponseDto1 = new ApiResponseDto<>(false, "User Registered successfully",
                    registeredUser);
            return new ResponseEntity<>(apiResponseDto1, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDto<UserEntity> apiResponseDto1 = new ApiResponseDto<>(true, e.getMessage(), null);
            return new ResponseEntity<>(apiResponseDto1, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<JwtDto>> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);
            System.out.println(authenticatedUser);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            ApiResponseDto<JwtDto> apiResponseDto1 = new ApiResponseDto<>(false, "logged in successfully",
                    new JwtDto(jwtToken, jwtService.getExpirationTime()));
            return new ResponseEntity<>(apiResponseDto1, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            ApiResponseDto<JwtDto> apiResponseDto1 = new ApiResponseDto<>(true, e.getMessage(), null);
            return new ResponseEntity<>(apiResponseDto1, HttpStatus.NOT_ACCEPTABLE);
        }

    }
}
