package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entidades.RolEntity;
@Repository
public interface RolRepository extends JpaRepository<RolEntity,Long> {
    Optional<RolEntity> findByNombreRolIgnoreCase(String nombre);

}
