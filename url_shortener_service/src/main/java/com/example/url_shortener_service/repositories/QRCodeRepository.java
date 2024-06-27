package com.example.url_shortener_service.repositories;

import com.example.url_shortener_service.entities.QRCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCodeEntity, Integer> {

}
