package com.proyecto.panaderialosandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError() {
        return "error";
    }

    @GetMapping("/acceso-denegado")
    public String accessDenied() {
        return "acceso-denegado";
    }
}