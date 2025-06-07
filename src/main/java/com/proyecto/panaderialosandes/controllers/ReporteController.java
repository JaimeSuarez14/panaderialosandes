package com.proyecto.panaderialosandes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.services.ExcelExportService;
import com.proyecto.panaderialosandes.services.ProductoService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final ProductoService productoService;
    private final ExcelExportService excelExportService;

    
    public ReporteController(ProductoService productoService, ExcelExportService excelExportService) {
        this.productoService = productoService;
        this.excelExportService = excelExportService;
    }

    @GetMapping("/productos/excel")
    public void exportarProductos(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

        List<Productos> productos = productoService.obtenerTodosLosProductos();
        excelExportService.generarReporteProductos(productos);
    }
    @GetMapping("/reportes/inventario")
    public String mostrarReporteInventario() {
        return "vista/reporte_inventario";
    }

    @GetMapping("/reportes/ventas")
    public String mostrarReporteVentas() {
        return "vista/reporte_venta";
    }
}