package com.proyecto.panaderialosandes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.repositorios.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuarios> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public void guardarUsuario(Usuarios usuario) {
        usuarioRepository.save(usuario);
    }

}
