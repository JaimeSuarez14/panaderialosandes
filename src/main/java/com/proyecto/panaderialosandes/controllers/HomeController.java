package com.proyecto.panaderialosandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.panaderialosandes.models.Clientes;

import org.springframework.ui.Model;

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

    @GetMapping("/agregar-usuario")
    public String agregarUsuario() {
        return "vista/agregar_usuario"; 
    }

    @GetMapping("/agregarcliente")
    public String agregarCliente(Model model) {
        model.addAttribute("cliente", new Clientes());
        return "vista/agregar_cliente"; 
    }

    @PostMapping("/guardarcliente")
    public String guardarCliente(@ModelAttribute Clientes cliente) {
        
        return "redirect:/principal/agregarcliente";
    }

}