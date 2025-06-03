package com.proyecto.panaderialosandes.services;

import java.util.List;

import com.proyecto.panaderialosandes.models.Productos;

public interface ProductoServiceInterface {
    List<Productos> obtenerTodosLosProductos();
    void guardarProducto(Productos producto);
    void eliminarProducto(Long id);
    Productos obtenerProductoPorId(Long id);
    void actualizarProducto(Productos producto);
}
