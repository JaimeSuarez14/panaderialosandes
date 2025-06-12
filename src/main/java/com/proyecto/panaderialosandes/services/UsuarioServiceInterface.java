package com.proyecto.panaderialosandes.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.panaderialosandes.models.Usuarios;

public interface UsuarioServiceInterface {

    Optional<Usuarios> buscarPorUsername(String username);
    void guardarUsuario(Usuarios usuario);
    List<Usuarios> obtenerTodosLosUsuarios();
    Optional<Usuarios> buscarPorId(int id);
    void cambiarEstadoUsuario(int id, String nuevoEstado);
    void eliminarUsuario(int id);
}
    