package com.proyecto.panaderialosandes.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.panaderialosandes.models.Productos;

public interface ProductoServiceInterface {

    List<Productos> obtenerTodosLosProductos();

    void guardarProducto(Productos producto);

    void eliminarProducto(int id);

    Optional<Productos> obtenerProductoPorId(int id);
    void actualizarProducto(Productos producto);
    
}
