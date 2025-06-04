package com.proyecto.panaderialosandes.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.repositorios.ProductoRepository;

@Service
public class ProductoService implements ProductoServiceInterface {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Productos> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    public void guardarProducto(Productos producto) {
        productoRepository.save(producto);
    }
    public void eliminarProducto(int id) {
        productoRepository.deleteById(id);
    }
    public Optional<Productos> obtenerProductoPorId(int id) {
        return productoRepository.findById(id);
    }
    public void actualizarProducto(Productos producto) {
        productoRepository.save(producto);
    }   
}
