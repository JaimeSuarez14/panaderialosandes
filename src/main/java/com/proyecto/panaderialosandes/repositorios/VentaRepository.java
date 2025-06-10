package com.proyecto.panaderialosandes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panaderialosandes.models.Ventas;

@Repository
public interface VentaRepository extends JpaRepository<Ventas, Integer>{
    
}
