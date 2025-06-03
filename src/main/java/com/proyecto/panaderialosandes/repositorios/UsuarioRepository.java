package com.proyecto.panaderialosandes.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.panaderialosandes.models.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer>{
    Optional<Usuarios> findByUsername(String username);

}
