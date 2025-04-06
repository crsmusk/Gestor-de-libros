package com.example.demo.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.dto.LibroDto;
import com.example.demo.model.dto.LibroDtoResponse;
import com.example.demo.model.entidades.AutorEntity;
import com.example.demo.model.entidades.GeneroEntity;
import com.example.demo.model.entidades.LibroEntity;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.GeneroRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.service.interfaces.ILibro;

@Service
public class LibroServiceImpl implements ILibro {
    @Value("${file.upload-dir}")
    String direccion;
    @Autowired
    LibroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Override
    public LibroDto buscarPorId(String id) {
        String generos = "";
        String autores = "";
        LibroEntity libro = repository.findById(id).orElse(null);

        if (libro != null) {
            LibroDto libroDto = new LibroDto();
            libroDto.setIsbn(libro.getIsbn());
            libroDto.setTitulo(libro.getTitulo());
            libroDto.setDescripcion(libro.getDescripcion());
            generos = libro.getGeneros().stream()
                    .map(GeneroEntity::getNombre)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            autores = libro.getAutores().stream()
                    .map(AutorEntity::getNombre)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
            libroDto.setAutores(autores);
            libroDto.setGeneros(generos);
            libroDto.setPortada(null);
            return libroDto;
        }

        return new LibroDto();
    }

    @Override
    @Transactional
    public void guardar(LibroDto libro) {
        if (repository.existsById(libro.getIsbn())) {
            String rutaPortada = repository.findById(libro.getIsbn()).get().getPortadaDireccion();
            if (rutaPortada != null) {
                String nombreArchivo = rutaPortada.substring(rutaPortada.lastIndexOf("/") + 1);
                File archivo = new File(direccion + File.separator + nombreArchivo);
                if (archivo.exists()) {
                    archivo.delete();
                }
            }
        }
        if (libro != null) {
            LibroEntity nuevoLibro = new LibroEntity();
            nuevoLibro.setIsbn(libro.getIsbn());
            nuevoLibro.setTitulo(libro.getTitulo());
            nuevoLibro.setDescripcion(libro.getDescripcion());
            nuevoLibro.setAutores(setAutor(libro.getAutores()));
            nuevoLibro.setGeneros(setGenero(libro.getGeneros()));
            nuevoLibro.setPortadaDireccion(guardarPortada(libro.getPortada()));
            repository.save(nuevoLibro);
        }
    }

    @Override
    public void eliminar(String id) {
        if (repository.existsById(id)) {
            String rutaPortada = repository.findById(id).get().getPortadaDireccion();
            if (rutaPortada != null) {
                String nombreArchivo = rutaPortada.substring(rutaPortada.lastIndexOf("/") + 1);
                File archivo = new File(direccion + File.separator + nombreArchivo);
                if (archivo.exists()) {
                    archivo.delete();
                }
            }
            repository.deleteById(id);
        }
    }

    @Override
    public List<LibroDtoResponse> buscarTodos() {
        List<LibroDtoResponse> librosDto = new ArrayList<>();
        List<LibroEntity> libros = repository.findAll();
        for (LibroEntity libro : libros) {
            LibroDtoResponse libroDto = new LibroDtoResponse();
            libroDto.setIsbn(libro.getIsbn());
            libroDto.setTitulo(libro.getTitulo());
            libroDto.setDescripcion(libro.getDescripcion());
            String generos = libro.getGeneros().stream()
                    .map(GeneroEntity::getNombre)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
            String autores = libro.getAutores().stream()
                    .map(AutorEntity::getNombre)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
            libroDto.setAutores(autores);
            libroDto.setGeneros(generos);
            libroDto.setPortada(libro.getPortadaDireccion());
            librosDto.add(libroDto);
        }
        return librosDto;
    }

    @Override
    public List<LibroDtoResponse> buscarPorTitulo(String titulo) {
        String generos = "";
        String autores = "";
        List<LibroEntity> libro = repository.findByTituloContainingIgnoreCase(titulo);
        List<LibroDtoResponse> librosDto = new ArrayList<>();
        if (!libro.isEmpty()) {
            for (LibroEntity libro1 : libro) {
                LibroDtoResponse libroDto = new LibroDtoResponse();
                libroDto.setIsbn(libro1.getIsbn());
                libroDto.setTitulo(libro1.getTitulo());
                libroDto.setDescripcion(libro1.getDescripcion());
                generos = libro1.getGeneros().stream()
                        .map(GeneroEntity::getNombre)
                        .reduce((a, b) -> a + "," + b)
                        .orElse("");
                autores = libro1.getAutores().stream()
                        .map(AutorEntity::getNombre)
                        .reduce((a, b) -> a + "," + b)
                        .orElse("");
                libroDto.setAutores(autores);
                libroDto.setGeneros(generos);
                libroDto.setPortada(libro1.getPortadaDireccion());
                librosDto.add(libroDto);
            }
            return librosDto;
        }

        return librosDto;
    }

    private List<AutorEntity> setAutor(String libro) {
        String[] autores = libro.split(",");
        List<AutorEntity> listaAutores = new ArrayList<>();
        AutorEntity autor = null;
        for (String i : autores) {
            autor = autorRepository.findByNombreIgnoreCase(i).orElse(null);
            if (autor != null) {
                listaAutores.add(autor);
            } else {
                autor = new AutorEntity();
                autor.setNombre(i);
                listaAutores.add(autorRepository.save(autor));
            }
        }
        return listaAutores;
    }

    private List<GeneroEntity> setGenero(String libro) {
        String[] generos = libro.split(",");
        List<GeneroEntity> listaGeneros = new ArrayList<>();
        GeneroEntity genero = null;
        for (String i : generos) {
            genero = generoRepository.findByNombreIgnoreCase(i).orElse(null);
            if (genero != null) {
                listaGeneros.add(genero);
            } else {
                genero = new GeneroEntity();
                genero.setNombre(i);
                listaGeneros.add(generoRepository.save(genero));
            }
        }
        return listaGeneros;
    }

    public String guardarPortada(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }
        try {
            File directorio = new File(direccion);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            String nombreArchivo = System.currentTimeMillis() + extension;

            Path path = Paths.get(direccion, nombreArchivo);
            Files.write(path, archivo.getBytes());

            return "/templates.portadas/" + nombreArchivo;
        } catch (Exception e) {
            System.out.println("Error al guardar la imagen: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LibroDtoResponse buscarPorIde(String id) {
        String generos = "";
        String autores = "";
        LibroEntity libro = repository.findById(id).orElse(null);

        if (libro != null) {
            LibroDtoResponse libroDto = new LibroDtoResponse();
            libroDto.setIsbn(libro.getIsbn());
            libroDto.setTitulo(libro.getTitulo());
            libroDto.setDescripcion(libro.getDescripcion());
            generos = libro.getGeneros().stream()
                    .map(GeneroEntity::getNombre)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            autores = libro.getAutores().stream()
                    .map(AutorEntity::getNombre)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
            libroDto.setAutores(autores);
            libroDto.setGeneros(generos);
            libroDto.setPortada(libro.getPortadaDireccion());
            return libroDto;
        }

        return new LibroDtoResponse();
    }

}
