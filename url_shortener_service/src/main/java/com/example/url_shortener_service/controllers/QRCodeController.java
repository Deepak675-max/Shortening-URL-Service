package com.example.url_shortener_service.controllers;

import com.example.url_shortener_service.dto.QRCodeDto;
import com.example.url_shortener_service.entities.UserEntity;
import com.example.url_shortener_service.services.QRCodeGeneratorService;
import com.example.url_shortener_service.services.UserService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QRCodeController {

    @Autowired
    private QRCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    private UserService userService;

    @GetMapping("/QRCode/generateQRCode")
    public ResponseEntity<byte[]> generateQRCode(@RequestBody QRCodeDto qrCodeDto,
            @RequestAttribute("payload") String userEmail) {

        try {
            System.out.println("payload = " + userEmail);
            UserEntity userEntity = userService.loadUserByUsername(userEmail);
            qrCodeDto.setUserId(userEntity.getId());
            byte[] qrCodeImage = qrCodeGeneratorService.generateQRCode(qrCodeDto);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "image/png");
            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
