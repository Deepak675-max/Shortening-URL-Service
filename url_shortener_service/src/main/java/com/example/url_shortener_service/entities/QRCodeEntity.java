package com.example.url_shortener_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "QRCodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QRCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = true)
    private String title;
    @Column(nullable = false)
    private String destination;
    @Column(name = "qrCodeBytesString", length = 1000)
    private String qrCodeBytesString;
    private Integer userId;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
