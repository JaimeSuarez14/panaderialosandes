package com.proyecto.panaderialosandes.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements UsuarioServiceInterface {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuarios> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void guardarUsuario(Usuarios usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuarios> buscarPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public void cambiarEstadoUsuario(int id, String nuevoEstado) {
    Optional<Usuarios> usuario = buscarPorId(id);
    if (usuario.isPresent()) {
        Usuarios user = usuario.get();
        user.setEstado(nuevoEstado.equals("activo") ? "activo" : "inactivo");
        usuarioRepository.save(user);
    } else {
        throw new IllegalArgumentException("Usuario no encontrado");
    }
}

    @Override
    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }
}