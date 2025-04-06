package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entidades.UsuarioEntity;
@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntity,String>{
    Optional<UsuarioEntity> findByUsuarioNombre(String usuarioNombre);
}
