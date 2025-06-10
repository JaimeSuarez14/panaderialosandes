package com.proyecto.panaderialosandes.controllers;


import com.proyecto.panaderialosandes.services.ExcelExportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.repositorios.CategoriaRepository;
import com.proyecto.panaderialosandes.models.Categorias;
import com.proyecto.panaderialosandes.models.Clientes;


@Controller
@RequestMapping("/principal") //localhost:8081/principal
public class HomeController {

    private final ExcelExportService excelExportService;

    private final CategoriaRepository categoriaRepository;


    HomeController(CategoriaRepository categoriaRepository, ExcelExportService excelExportService) {
        this.categoriaRepository = categoriaRepository;
        this.excelExportService = excelExportService;
    }
    

    @GetMapping //localhost:8081/principal
    public String home() {
        return "vista/inicio"; 
    }

    @GetMapping("/inicio") //localhost:8081/principal/inicio
    public String cabecera() {
        return "vista/principal"; 
    }

    @GetMapping("/productos") //localhost:8081/principal/productos
        public String vistados() {
            return "vista/lista_producto"; 
    }
    @GetMapping("/venta")
    public String mostrarVenta() {
        return "vista/venta";
    }

    @GetMapping("/agregar-usuario") //localhost:8081/principal/agregar-usuario
    public String agregarUsuario() {
        return "vista/agregar_usuario"; 
    }
    @GetMapping("/agregar-producto")
    public String agregarProducto(Model model) {
        model.addAttribute("producto", new Productos());;
        return "vista/agregar_producto";
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
    @GetMapping("/reporte_venta")
    public String reporteVenta(Model model) {
        model.addAttribute("categorias", new Categorias());
        model.addAttribute("usuarios", new Usuarios());
        return "vista/reporte_venta";
    }
    @GetMapping("/reporte_inventario")
    public String reporteInventario(Model model) {
        model.addAttribute("categorias", new Categorias());
        model.addAttribute("usuarios", new Usuarios());
        return "vista/reporte_inventario";
    }

    @PostMapping("/guardarproducto") //localhost:8081/principal/guardarproducto
    public String guardarProducto(@ModelAttribute Productos producto) {
        return "redirect:/principal/agregarproducto";   
    }
    @GetMapping("/listar_usuarios")
    public String listarUsuarios(Model model) { 
        model.addAttribute("usuarios", new Usuarios());
        return "vista/listar_usuarios";
    }

        

}