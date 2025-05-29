package com.proyecto.panaderialosandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/principal")
public class HomeController {
    

    @GetMapping
    public String home() {
        return "vista/principal"; 
    }

    @GetMapping("/header")
    public String cabecera() {
        return "vista/header"; 
    }

    @GetMapping("/productos")
        public String vistados() {
            return "vista/lista_producto"; 
    }

    @GetMapping("/agregar-usuario")
    public String agregarUsuario() {
        return "vista/agregar_usuario"; 
    }

}