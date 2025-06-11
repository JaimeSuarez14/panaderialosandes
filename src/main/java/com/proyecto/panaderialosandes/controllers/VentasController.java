package com.proyecto.panaderialosandes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.panaderialosandes.models.Clientes;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.services.CategoriaService;
import com.proyecto.panaderialosandes.services.ClienteService;
import com.proyecto.panaderialosandes.services.ProductoService;
import com.proyecto.panaderialosandes.services.VentasDto;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/venta")
public class VentasController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;
        
        @PostMapping("/buscarcliente")
        public String buscarCliente(@RequestParam("dni") String dni, Model model) {
           
            Clientes cliente = clienteService.buscarClientePorDni(dni);
    
            if (cliente != null) {
            model.addAttribute("categoria", categoriaService.buscartodos());
            model.addAttribute("productos", productoService.obtenerTodosLosProductos());
            model.addAttribute("cliente", cliente);
            return "vista/venta";
        
        }
        return "redirect:/principal/venta";
    }

    @GetMapping("/datosventas")
    @ResponseBody
    public ResponseEntity<VentasDto> enviarDatos() {
        List<Clientes> clientes = clienteService.buscarTodosLosClientes();
        List<Productos> productos = productoService.obtenerTodosLosProductos();
        VentasDto data = new VentasDto(clientes, productos);

        return ResponseEntity.ok(data);
    }
    
}
