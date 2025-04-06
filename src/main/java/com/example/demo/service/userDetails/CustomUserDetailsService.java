package com.example.demo.service.userDetails;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.example.demo.model.entidades.RolEntity;
import com.example.demo.model.entidades.UsuarioEntity;
import com.example.demo.repository.UsuarioRepositorio;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepositorio usuarioRepo;
   @Autowired
   public void setUsuarioRepo(UsuarioRepositorio usuarioRepo){
       this.usuarioRepo=usuarioRepo;
   }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioRepo.findByUsuarioNombre(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List <GrantedAuthority> authorities = new ArrayList<>();
        for (RolEntity rol : usuario.getRolEntity()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getNombreRol())));
        }
        
        return new User(usuario.getUsuarioNombre(), usuario.getContraseña(), authorities);
    }
   

}
