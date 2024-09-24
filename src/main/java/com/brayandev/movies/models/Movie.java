package com.brayandev.movies.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    // Esto es para una clave primaria Long siempre
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int year;
    private int votes;
    private double rating;
    // Especificar en el orm name table image_url

    @Column(name = "image_url")
    private String imageUrl;

}


