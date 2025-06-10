package com.proyecto.panaderialosandes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.panaderialosandes.models.Categorias;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.repositorios.CategoriaRepository;
import com.proyecto.panaderialosandes.repositorios.ProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping("/nuevo") //localhost:8081/productos/nuevo
    public String mostrarFormularioNuevoProducto() {

        logger.info("Accediendo al formulario de nuevo producto");
        
        return "vista/agregar_producto";
        
    }

    @PostMapping("/guardar")
public String guardarProducto(@RequestParam("categoria_id") int categoriaId,
                              @ModelAttribute Productos producto) {
    Categorias categoria = categoriaRepository.findById(categoriaId).orElse(null);
    producto.setCategoria_id(categoria);
    productoRepository.save(producto);
    return "vista/agregar_producto";
}
}
