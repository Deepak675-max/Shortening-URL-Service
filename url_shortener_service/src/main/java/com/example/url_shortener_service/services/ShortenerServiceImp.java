package com.example.url_shortener_service.services;

import com.example.url_shortener_service.configuration.AppConfig;
import com.example.url_shortener_service.dto.ShortenerDto;
import com.example.url_shortener_service.entities.ShortenerEntity;
import com.example.url_shortener_service.helper.SHA256;
import com.example.url_shortener_service.repositories.ShortenerRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ShortenerServiceImp implements ShortenerService {
    @Autowired
    private ShortenerRepository shortenerRepository;
    @Autowired
    private SHA256 sha256;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AppConfig appConfig;

    @Override
    public String createNewLink(ShortenerDto shortenerDTO) {
        byte[] hash;
        try {
            hash = sha256.getSHA(shortenerDTO.getDestination());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        List<ShortenerEntity> shortenerData = shortenerRepository.findAllByDestinationAndUserId(shortenerDTO.getDestination(), shortenerDTO.getUserId());
        if(!shortenerData.isEmpty()) {
            throw new RuntimeException(shortenerDTO.getDestination() + " already used for another short link");
        }
        String hexString = sha256.toHexString(hash);
        String hashCode = hexString.substring(0,6);
        shortenerDTO.setHash(hashCode);
        ShortenerEntity shortenerEntity = modelMapper.map(shortenerDTO, ShortenerEntity.class);
        shortenerEntity.setUrlClicks(0);
        shortenerRepository.save(shortenerEntity);
        return appConfig.getBaseURL()+shortenerEntity.getHash();
    }

    @Override
    public List<ShortenerDto> getAllLinks() {
       List<ShortenerEntity> shortenerEntities = shortenerRepository.findAll();
       String baseURL = appConfig.getBaseURL();
       return shortenerEntities.stream().map(entity -> new ShortenerDto(entity.getId(), entity.getDestination(), entity.getTitle(), baseURL.concat(entity.getHash()))).toList();
    }

    @Override
    public String getOriginalLink(String hash) {
        ShortenerEntity shortenerEntity = shortenerRepository.findByHash(hash);
        if(shortenerEntity == null) {
            throw  new RuntimeException("Not found any destination with this url");
        }
        shortenerEntity.setUrlClicks(shortenerEntity.getUrlClicks()+1);
        shortenerRepository.save(shortenerEntity);
        return shortenerEntity.getDestination();
    }

    @Override
    public int getUrlClicks(Long id) {
        Optional<ShortenerEntity> shortenerEntity = shortenerRepository.findById(id);
        if(shortenerEntity.isEmpty()) {
            throw new RuntimeException("Invalid Id");
        }
        return shortenerEntity.get().getUrlClicks();
    }
}
