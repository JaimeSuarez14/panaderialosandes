package com.proyecto.panaderialosandes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.services.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto() {
        return "vista/agregar_producto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Productos producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos/nuevo";
    }
}
