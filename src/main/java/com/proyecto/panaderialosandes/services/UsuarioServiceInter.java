package com.proyecto.panaderialosandes.services;

import java.util.Optional;

import com.proyecto.panaderialosandes.models.Usuarios;

public interface UsuarioServiceInter {

    Optional<Usuarios> buscarPorUsername(String username);
    void guardarUsuario(Usuarios usuario);
}
    