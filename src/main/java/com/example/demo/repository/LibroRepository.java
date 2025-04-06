package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entidades.LibroEntity;
@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, String> {
  
   List<LibroEntity> findByTituloContainingIgnoreCase(@Param("titulo") String titulo);
} 
