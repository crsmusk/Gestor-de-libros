package com.example.demo.model.entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
  @Id
 private String usuarioCedula;
 @Column(name="nombre_usuario",unique = true)
 private String usuarioNombre;
 @Column(name="correo_electronico",unique = true)
 private String correoElectronico;

 private String contraseña;
 @Column(name="is_enable")
 private boolean isEnable;
 @Column(name="is_account_no_expired")
 private boolean isAccountNoExpired;
 @Column(name="is_credentials_no_expired")
 private boolean isCredentialsNoExpired;
 @Column(name="is_account_no_locked")
 private boolean isAccountNoLocked;

 @ManyToMany(fetch = FetchType.EAGER)
 @JoinTable(name = "usuario_rol",
    joinColumns = @jakarta.persistence.JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "rol_id"))
 private List<RolEntity> rolEntity;
    

}