package com.proyecto.panaderialosandes.repositorios;

import com.proyecto.panaderialosandes.models.DetalleVentas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<DetalleVentas, Integer> {
}