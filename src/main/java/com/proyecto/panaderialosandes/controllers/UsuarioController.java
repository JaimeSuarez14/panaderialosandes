package com.proyecto.panaderialosandes.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.StringUtils;

import com.proyecto.panaderialosandes.models.Usuarios;
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

        //vamos a validar con username y password que no halla espacion vacios
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            model.addAttribute("error", "El nombre de usuario y la contraseña no pueden estar vacios");
            return "vista/login";
        }

        Optional<Usuarios> usuario = usuarioService.buscarPorUsername(username);
        if(usuario.isPresent() && usuario.get().getPassword().equals(password)){
            return "redirect:/principal";
        }else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "vista/login";
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "vista/agregar_usuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuarios usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/principal";
    }
}


