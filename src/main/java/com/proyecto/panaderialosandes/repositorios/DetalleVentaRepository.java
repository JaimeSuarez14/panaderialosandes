package com.proyecto.panaderialosandes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panaderialosandes.models.DetalleVentas;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentas, Integer>{
    
}
