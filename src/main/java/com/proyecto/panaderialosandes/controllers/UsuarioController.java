package com.proyecto.panaderialosandes.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.panaderialosandes.entidades.Usuarios;
import com.proyecto.panaderialosandes.services.UsuarioService;

@Controller
@RequestMapping("/login")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarLogin(){
        return "vista/login";
    }

    @PostMapping
    public String procesarLogin(@RequestParam String username, @RequestParam String password, Model model){
        Optional<Usuarios> usuario = usuarioService.buscarPorUsername(username);
        if(usuario.isPresent() && usuario.get().getPassword().equals(password)){
            return "vista/principal";
        }else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "vista/login";
        }
    }
}
