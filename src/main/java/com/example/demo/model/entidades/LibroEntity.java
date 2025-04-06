package com.example.demo.model.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "libros")
public class LibroEntity {
    @Id
    private String isbn;
    private String titulo;
    private String descripcion;
    private String portadaDireccion;
    @ManyToMany
    @JoinTable(name = "libro_autor",
    joinColumns = @JoinColumn(name = "libro_id"),
    inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<AutorEntity> autores;
    @ManyToMany
    @JoinTable(name = "libro_genero",
    joinColumns = @JoinColumn(name = "libro_id"),
    inverseJoinColumns = @JoinColumn(name = "genero_id"))
    private List<GeneroEntity> generos;
}
