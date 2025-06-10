package com.proyecto.panaderialosandes.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.panaderialosandes.models.Categorias;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.repositorios.CategoriaRepository;
import com.proyecto.panaderialosandes.repositorios.ProductoRepository;
import com.proyecto.panaderialosandes.services.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    

    @GetMapping("/nuevo") //localhost:8081/productos/nuevo
    public String agregarProducto(Model model) {
        model.addAttribute("producto", new Productos());;
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

//metodo para buscar un producto en el menu principal
@PostMapping("/buscar")
public String buscarProducto(@RequestParam("busqueda") String busqueda, Model model) {

    logger.info("Busqueda: {}", busqueda);

    List<Productos> productos = productoService.buscarPorNombre(busqueda);
    model.addAttribute("productos", productos);

    //obtener el usuario
    return "vista/lista_producto";
    
}

}
