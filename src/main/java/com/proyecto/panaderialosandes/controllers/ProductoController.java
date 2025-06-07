package com.proyecto.panaderialosandes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("/nuevo") //localhost:8081/productos/nuevo
    public String mostrarFormularioNuevoProducto() {

        logger.info("Accediendo al formulario de nuevo producto");
        
        return "vista/agregar_producto";
        
    }

    @PostMapping("/guardar")    //localhost:8081/productos/guardar
    public String guardarProducto(@ModelAttribute Productos producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos/nuevo";
    }
}
