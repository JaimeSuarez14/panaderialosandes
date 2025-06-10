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
import jakarta.servlet.http.HttpSession;
import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.services.UsuarioService;

@Controller
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping // localhost:8081/login
    public String mostrarLogin() {
        return "vista/login";
    }

    @PostMapping
public String procesarLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
        model.addAttribute("error", "El nombre de usuario y la contraseña no pueden estar vacíos");
        return "vista/login";
    }
    
    Optional<Usuarios> usuario = usuarioService.buscarPorUsername(username);
    if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
        session.setAttribute("usuarioActual", usuario.get()); // Guarda usuario en sesión
        
        // Depuración: Imprimir en la consola
        System.out.println("Usuario autenticado: " + usuario.get().getNombre() + " - Rol: " + usuario.get().getRol());
        
        return "redirect:/principal/inicio";
    } else {
        model.addAttribute("error", "Credenciales incorrectas");
        return "vista/login";
    }
}

    @GetMapping("/nuevo") // localhost:8081/login/nuevo
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "vista/agregar_usuario";
    }

    @PostMapping("/guardar") // localhost:8081/login/guardar
    public String guardarUsuario(@ModelAttribute Usuarios usuario) {
        usuario.setEstado("Activo"); // Estado por defecto al crear el usuario
        usuarioService.guardarUsuario(usuario);
        return "redirect:/principal/inicio";
    }

    @GetMapping("/principal/inicio")
public String mostrarInicio(Model model, HttpSession session) {
    Usuarios usuario = (Usuarios) session.getAttribute("usuarioActual");

    if (usuario != null) {
        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("rol", usuario.getRol());

        // Depuración: Imprimir en consola
        System.out.println("Usuario en sesión: " + usuario.getNombre() + " - Rol: " + usuario.getRol());
        
        return "vista/principal";
    } else {
        System.out.println("No hay usuario en sesión.");
        return "redirect:/login"; // Redirige al login si no hay usuario en sesión
    }
}
}