package com.proyecto.panaderialosandes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String username;
    private String password;
    private String rol;
    private String estado;

    public Usuarios() {
    }
    
    public Usuarios(Integer id, String nombre, String username, String password, String rol, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuarios [id=" + id + ", nombre=" + nombre + ", username=" + username + ", password=" + password + ", rol="
                + rol + ", estado=" + estado + "]";
    }

    
}
