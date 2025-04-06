package com.example.demo.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class LibroDto {
    private String isbn;
    private String titulo;
    private String descripcion;
    private String autores;
    private String generos;
    private MultipartFile portada;
}
