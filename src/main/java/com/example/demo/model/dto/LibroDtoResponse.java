package com.example.demo.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class LibroDtoResponse {
   private String isbn;
    private String titulo;
    private String descripcion;
    private String autores;
    private String generos;
    private String portada;
}

