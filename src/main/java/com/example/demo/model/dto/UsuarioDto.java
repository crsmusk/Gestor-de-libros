package com.example.demo.model.dto;


import lombok.Data;
@Data
public class UsuarioDto {
  private String usuarioCedula; 
  private String usuarioNombre;
  private String correoElectronico;
  private String contraseña;
  private String roles;
}
