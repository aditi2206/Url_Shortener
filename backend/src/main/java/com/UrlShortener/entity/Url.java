package com.UrlShortener.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Url {
    @Id
    private String shortUrl;  // Use this as the primary key

    @Column(nullable = false, length = 500)
    private String originalUrl;


}
