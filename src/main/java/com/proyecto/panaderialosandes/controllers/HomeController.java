package com.proyecto.panaderialosandes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.panaderialosandes.models.Categorias;
import com.proyecto.panaderialosandes.models.Clientes;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.repositorios.CategoriaRepository;
import com.proyecto.panaderialosandes.services.CategoriaService;
import com.proyecto.panaderialosandes.services.ExcelExportService;
import com.proyecto.panaderialosandes.services.ProductoService;
import com.proyecto.panaderialosandes.services.UsuarioService;
import com.proyecto.panaderialosandes.services.UsuarioServiceInterface;

@Controller
@RequestMapping("/principal") // localhost:8081/principal
public class HomeController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioServiceInterface usuarioServiceInterface;

    private final ExcelExportService excelExportService;

    private final CategoriaRepository categoriaRepository;

    HomeController(CategoriaRepository categoriaRepository, ExcelExportService excelExportService) {
        this.categoriaRepository = categoriaRepository;
        this.excelExportService = excelExportService;
    }

    @GetMapping // localhost:8081/principal
    public String home() {
        return "vista/inicio";
    }

    @GetMapping("/inicio") // localhost:8081/principal/inicio
    public String cabecera() {
        return "vista/principal";
    }

    @GetMapping("/productos") // localhost:8081/principal/productos
    public String mostrarProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());
        return "vista/lista_producto";
    }

    @GetMapping("/venta")
    public String mostrarVenta(Model model) {

        model.addAttribute("categoria", categoriaService.buscartodos());
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());
        model.addAttribute("cliente", new Clientes());
        return "vista/venta";
    }

    @GetMapping("/agregarcliente") // localhost:8081/principal/agregarcliente
    public String agregarCliente(Model model) {
        model.addAttribute("cliente", new Clientes());
        return "vista/agregar_cliente";
    }

    @PostMapping("/guardarcliente") // localhost:8081/principal/guardarcliente
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

    @PostMapping("/guardarproducto") // localhost:8081/principal/guardarproducto
    public String guardarProducto(@ModelAttribute Productos producto) {
        return "redirect:/principal/agregarproducto";
    }

    @GetMapping("/listar_usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServiceInterface.obtenerTodosLosUsuarios());
        return "vista/listar_usuarios";
    }

    @GetMapping("/error")
    public String error() {
        return "vista/error";
    }

    @GetMapping("/acceso_denegado")
    public String accesoDenegado(Model model) {
        return "vista/acceso_denegado";
    }

    @PostMapping("/cambiar_estado")
    public String cambiarEstado(@RequestParam int id,
            @RequestParam String nuevoEstado) {

        usuarioServiceInterface.cambiarEstadoUsuario(id, nuevoEstado);

        return "redirect:/principal/listar_usuarios"; // Redirigir a la lista de usuarios
    }

}