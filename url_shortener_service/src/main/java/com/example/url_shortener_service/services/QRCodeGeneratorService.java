package com.example.url_shortener_service.services;

import com.example.url_shortener_service.dto.QRCodeDto;
import com.example.url_shortener_service.entities.QRCodeEntity;
import com.example.url_shortener_service.repositories.QRCodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeGeneratorService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    public byte[] generateQRCode(QRCodeDto qrCodeDto) throws WriterException, IOException {
        int width = 200;
        int height = 200;
        String text = qrCodeDto.getDestination();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrCodeBytes = pngOutputStream.toByteArray();
        QRCodeEntity qrCodeEntity = modelMapper.map(qrCodeDto, QRCodeEntity.class);
        String qrCodeBytesStr = new String(qrCodeBytes, StandardCharsets.UTF_8);
        qrCodeEntity.setQrCodeBytesString(qrCodeBytesStr);
        qrCodeRepository.save(qrCodeEntity);
        return qrCodeBytes;
    }
}
