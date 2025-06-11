package com.proyecto.panaderialosandes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panaderialosandes.models.Clientes;

@Repository
public interface ClienteRepository extends JpaRepository<Clientes, Integer>{
    Clientes findByDni(String dni);
}
