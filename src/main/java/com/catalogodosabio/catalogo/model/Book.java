package com.catalogodosabio.catalogo.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;
}

