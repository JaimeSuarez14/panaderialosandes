package com.proyecto.panaderialosandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.panaderialosandes.models.Clientes;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/principal") //localhost:8081/principal
public class HomeController {
    

    @GetMapping //localhost:8081/principal
    public String home() {
        return "vista/principal"; 
    }

    @GetMapping("/inicio") //localhost:8081/principal/inicio
    public String cabecera() {
        return "vista/inicio"; 
    }

    @GetMapping("/productos") //localhost:8081/principal/productos
        public String vistados() {
            return "vista/lista_producto"; 
    }

    @GetMapping("/agregar-usuario") //localhost:8081/principal/agregar-usuario
    public String agregarUsuario() {
        return "vista/agregar_usuario"; 
    }

    @GetMapping("/agregarcliente") //localhost:8081/principal/agregarcliente
    public String agregarCliente(Model model) {
        model.addAttribute("cliente", new Clientes());
        return "vista/agregar_cliente"; 
    }

    @PostMapping("/guardarcliente") //localhost:8081/principal/guardarcliente
    public String guardarCliente(@ModelAttribute Clientes cliente) {
        
        return "redirect:/principal/agregarcliente";
    }

}