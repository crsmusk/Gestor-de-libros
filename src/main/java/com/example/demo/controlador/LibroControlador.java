package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.LibroDto;
import com.example.demo.model.dto.LibroDtoResponse;
import com.example.demo.model.entidades.LibroEntity;
import com.example.demo.service.LibroServiceImpl;



@Controller
public class LibroControlador {

    @Autowired
    private LibroServiceImpl service;

    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna la plantilla login.html
    }
  
    @GetMapping("/")
    public String traerLibros(Model model) {
        
        model.addAttribute("libros",service.buscarTodos());
        return "index";
    }
    @PostMapping("/buscar-libro")
    public String buscarPorId(@RequestParam String titulo, Model model) {
        if (titulo.isEmpty()) {
            model.addAttribute("libros",service.buscarTodos());
            return "index";
        }else{
             model.addAttribute("libros",service.buscarPorTitulo(titulo));
        return "index";
        }
       
    }
    @GetMapping("/borrar-libro/{isbn}")
     public String borrarLibro(@PathVariable String isbn,Model model) {
      service.eliminar(isbn);
     return "redirect:/";
    }
    @GetMapping("/actualizar-libro/{isbn}")
    public String actualizarPorId(@PathVariable String isbn, Model model) {
         LibroDto libro = service.buscarPorId(isbn); 
        if (libro == null) {
            libro = new LibroDto(); 
            libro.setTitulo("No encontrado");
        }
       
        model.addAttribute("LibroDto", libro); // Asegúrate de usar el nombre "LibroDto"
        return "modificarLibro";
    }
    
    @PostMapping("/guardar-libro")
    public String guardar( LibroDto libro ,Model model) {
        service.guardar(libro);
        model.addAttribute("libros",service.buscarTodos());
        return "redirect:/";
    }
    @GetMapping("/modificar-agregar-libro")
    public String modificarAgregarLibro(Model model) {
        LibroDto libro =  new LibroDto(); 
       
        model.addAttribute("LibroDto", libro); 
        return "modificarLibro";
       
    }

    @GetMapping("/mostrar-libro/{isbn}")
    public String mostrarLibro(@PathVariable String isbn, Model model) {
        LibroDtoResponse libro = service.buscarPorIde(isbn); 
        if (libro == null) {
            libro = new LibroDtoResponse(); 
            libro.setTitulo("No encontrado");
        }
        model.addAttribute("libro", libro); // Asegúrate de usar el nombre "LibroDto"
        return "mostrarLibro";
    }
    
}
