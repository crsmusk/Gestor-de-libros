package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.dto.UsuarioDto;
import com.example.demo.model.entidades.LibroEntity;
import com.example.demo.model.entidades.UsuarioEntity;
import com.example.demo.service.UsuarioServiceImp;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UsuarioControlador {
  
    @Autowired
    private UsuarioServiceImp service;
    

     @GetMapping("/actualizar-usuario/{cedula}")
    public String actualizarPorId( @PathVariable String cedula, Model model) {
        UsuarioDto usuario = null;
        if (usuario==null) {
            usuario = new UsuarioDto();
            usuario.setUsuarioNombre("No encontrado");
        }
        model.addAttribute("UsuarioDto",usuario);
        return "modificarUsuario";
    }
    
    @PostMapping("/guardar-usuario")
    public String guardar( UsuarioDto usuario, Model model) {
        service.guardar(usuario);
        return "redirect:/";
    }
    
    @GetMapping("/borrar-usuario/{id}")
     public String borrarLibro(@PathVariable String id,Model model) {
      service.eliminar(id);
     return "redirect:/";
    }
    
    @GetMapping("/modificar-agregar-usuario")
    public String modificarAgregarUsuario(Model model) {
        UsuarioDto usuario = new UsuarioDto();
        model.addAttribute("UsuarioDto",usuario);
        return "modificarUsuario";
    }
    
}
