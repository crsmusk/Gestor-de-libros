package com.example.demo.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.dto.LibroDto;
import com.example.demo.model.dto.LibroDtoResponse;
import com.example.demo.model.entidades.LibroEntity;

public interface ILibro {
  
    public LibroDto buscarPorId(String id);
    public void guardar(LibroDto libro);
    public void eliminar(String id);
    public List<LibroDtoResponse> buscarTodos();
    public List<LibroDtoResponse> buscarPorTitulo(String titulo);
    public LibroDtoResponse buscarPorIde(String id);
}
