package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.UsuarioDto;
import com.example.demo.model.entidades.GeneroEntity;
import com.example.demo.model.entidades.RolEntity;
import com.example.demo.model.entidades.UsuarioEntity;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepositorio;
import com.example.demo.service.interfaces.IUsuario;

@Service
public class UsuarioServiceImp implements IUsuario{
   

    @Autowired
    UsuarioRepositorio usuarioRepository;

    @Autowired
    RolRepository rolRepository;
    

    @Override
    public void eliminar(String id) {
       if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public UsuarioEntity buscarPorId(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public UsuarioEntity buscarPorNombre(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorNombre'");
    }

    @Override
    public void actualizar(UsuarioEntity usuario, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public List<UsuarioEntity> buscarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }

    @Override
    public void guardar(UsuarioDto usuario) {
        if (usuario!=null) {
            UsuarioEntity nuevoUsuario=new UsuarioEntity();
            nuevoUsuario.setContraseña(usuario.getContraseña());
            nuevoUsuario.setUsuarioNombre(usuario.getUsuarioNombre());
            nuevoUsuario.setCorreoElectronico(usuario.getCorreoElectronico());
            nuevoUsuario.setUsuarioCedula(usuario.getUsuarioCedula());
            nuevoUsuario.setRolEntity(setRoles(usuario.getRoles()));
        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }
  

     private List<RolEntity> setRoles(String rol) {
        String[] roles= rol.split(",");
        List<RolEntity>listaRoles= new ArrayList<>();
        RolEntity role=  null;
        for(String i:roles){
            role=rolRepository.findByNombreRolIgnoreCase(i).orElse(null);
          if (role!=null) {
              listaRoles.add(role); 
          }else{
                role= new RolEntity();
                role.setNombreRol(i);
                listaRoles.add(rolRepository.save(role));
          }
        }
        return listaRoles;
    }
}
