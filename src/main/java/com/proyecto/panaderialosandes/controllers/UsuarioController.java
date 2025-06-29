package com.proyecto.panaderialosandes.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;

import com.proyecto.panaderialosandes.dto.UsuarioDto;

import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.services.UsuarioService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @GetMapping
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
        if (usuario.isPresent()) {
            // Verificar estado del usuario
            if (!"activo".equalsIgnoreCase(usuario.get().getEstado())) {
                model.addAttribute("error", "Usuario inactivo. Contacte al administrador.");
                return "vista/login";
            }
            
            // Verificar contraseña
            if (usuario.get().getPassword().equals(password)) {
                session.setAttribute("usuarioActual", usuario.get());
                logger.info("Usuario autenticado: {} - Rol: {}", usuario.get().getNombre(), usuario.get().getRol());
                return "redirect:/principal/inicio";
            } else {
                model.addAttribute("error", "Credenciales incorrectas");
                return "vista/login";
            }
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "vista/login";
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "vista/agregar_usuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuarios usuario, Model model) {
        if (StringUtils.isBlank(usuario.getNombre()) || StringUtils.isBlank(usuario.getUsername()) || StringUtils.isBlank(usuario.getPassword())) {
            model.addAttribute("error", "El nombre de usuario, el nombre y la contraseña no pueden estar vacíos");
            model.addAttribute("usuario", new Usuarios());
            return "vista/agregar_usuario";
        }

        usuario.setEstado("activo");
        usuarioService.guardarUsuario(usuario);
        model.addAttribute("usuario", new Usuarios());
        model.addAttribute("success", "Usuario creado exitosamente");
        return "vista/agregar_usuario";
    }

    @GetMapping("/principal/inicio")
    public String mostrarInicio(Model model, HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioActual");

        if (usuario != null) {
            model.addAttribute("nombre", usuario.getNombre());
            model.addAttribute("rol", usuario.getRol());
            return "vista/principal";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/dataUsuarios")
    @ResponseBody
    public ResponseEntity<List<UsuarioDto>> enviarData() {
        List<Usuarios> usuario = usuarioService.obtenerTodosLosUsuarios();
        List<UsuarioDto> data = usuario.stream()
                .map(u -> new UsuarioDto(u.getId(), u.getNombre(), u.getUsername(), u.getRol(), u.getEstado()))
                .toList();
        return ResponseEntity.ok(data); 
    }

    @PostMapping("/actualizar/{id}")
    @ResponseBody
    public String actualizarUsuario(@PathVariable int id, @RequestBody UsuarioDto usuarioDto) {
        logger.info("Actualizando usuario: {}", usuarioDto);
        Optional<Usuarios> usuarioOptional = usuarioService.buscarPorId(id);
        
        if(usuarioOptional.isPresent()){
            Usuarios usuario = usuarioOptional.get();
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setUsername(usuarioDto.getUsername());
            usuario.setRol(usuarioDto.getRol());
            usuarioService.guardarUsuario(usuario);
            return "Usuario actualizado correctamente";
        } else {
            return "Error: Usuario no encontrado";
        }
    }

    @PostMapping("/eliminar-usuario/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        Optional<Usuarios> usuarioOptional = usuarioService.buscarPorId(id);
        if(usuarioOptional.isPresent()){
            usuarioService.eliminarUsuario(id);
        }
        return "redirect:/principal/listar_usuarios";
    }
}