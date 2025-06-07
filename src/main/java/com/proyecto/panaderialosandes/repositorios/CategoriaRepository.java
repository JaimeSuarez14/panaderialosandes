package com.proyecto.panaderialosandes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.panaderialosandes.models.Categorias;

public interface CategoriaRepository extends JpaRepository<Categorias,Integer> {
}