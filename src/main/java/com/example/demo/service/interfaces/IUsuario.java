package com.example.demo.service.interfaces;

import java.util.List;

import com.example.demo.model.dto.UsuarioDto;
import com.example.demo.model.entidades.UsuarioEntity;

public interface IUsuario {
  
    public void guardar(UsuarioDto usuario);
    public void eliminar(String id);
    public UsuarioEntity buscarPorId(String id);
    public UsuarioEntity buscarPorNombre(String nombre);
    public void actualizar(UsuarioEntity usuario, String id);
    public List<UsuarioEntity> buscarTodos();
}
