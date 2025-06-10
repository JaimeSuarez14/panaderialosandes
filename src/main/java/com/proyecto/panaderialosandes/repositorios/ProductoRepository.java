package com.proyecto.panaderialosandes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.panaderialosandes.models.Productos;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, Integer>{
    
}
