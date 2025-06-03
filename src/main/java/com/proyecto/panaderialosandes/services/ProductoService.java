package com.proyecto.panaderialosandes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.repositorios.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Productos> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
}
