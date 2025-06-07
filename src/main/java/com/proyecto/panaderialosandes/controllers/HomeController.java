package com.proyecto.panaderialosandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.models.Categorias;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/principal")
public class HomeController {
    

    @GetMapping
    public String home() {
        return "vista/principal"; 
    }

    @GetMapping("/inicio")
    public String cabecera() {
        return "vista/inicio"; 
    }

    @GetMapping("/productos")
        public String vistados() {
            return "vista/lista_producto"; 
    }
    @GetMapping("/venta")
    public String mostrarVenta() {
        return "vista/venta";
    }

    @GetMapping("/agregar-usuario")
    public String agregarUsuario() {
        return "vista/agregar_usuario"; 
    }
    @GetMapping("/agregar-producto")
    public String agregarProducto(Model model) {
        model.addAttribute("producto", new Productos());;
        return "vista/agregar_producto";
    }

}