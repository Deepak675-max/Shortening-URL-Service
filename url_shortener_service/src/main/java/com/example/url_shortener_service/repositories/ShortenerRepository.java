package com.example.url_shortener_service.repositories;

import com.example.url_shortener_service.entities.ShortenerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShortenerRepository extends JpaRepository<ShortenerEntity, Long> {
    List<ShortenerEntity> findAllByDestinationAndUserId(String destination, Long userId);
    ShortenerEntity findByHash(String hash);

}
