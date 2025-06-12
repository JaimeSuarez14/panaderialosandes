package com.proyecto.panaderialosandes.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.panaderialosandes.dto.ClienteDto;
import com.proyecto.panaderialosandes.dto.VentasDto;
import com.proyecto.panaderialosandes.dto.VentasExportDto;
import com.proyecto.panaderialosandes.models.Clientes;
import com.proyecto.panaderialosandes.models.DetalleVentas;
import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.models.Usuarios;
import com.proyecto.panaderialosandes.models.Ventas;
import com.proyecto.panaderialosandes.services.ClienteService;

import com.proyecto.panaderialosandes.services.DetalleVentaService;
import com.proyecto.panaderialosandes.services.ProductoService;
import com.proyecto.panaderialosandes.services.VentasServices;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/venta")
public class VentasController {

    private final Logger logger = LoggerFactory.getLogger(VentasController.class);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentasServices ventasServices;

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping("/datosventas")
    @ResponseBody
    public ResponseEntity<VentasExportDto> enviarDatos() {
        List<Clientes> clientes = clienteService.buscarTodosLosClientes();
        List<Productos> productos = productoService.obtenerTodosLosProductos();
        VentasExportDto data = new VentasExportDto(clientes, productos);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/guardar_cliente")
    @ResponseBody
    public ResponseEntity<Clientes> buscarCliente(@RequestBody ClienteDto clienteDto) {

        Clientes cliente = new Clientes();

        cliente.setDni(clienteDto.getDni());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setCorreo(clienteDto.getCorreo());
        cliente.setDireccion(clienteDto.getDireccion());

        Clientes clienteGuardado = clienteService.guardarCliente(cliente);

        return ResponseEntity.ok(clienteGuardado);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Ventas> postMethodName(@RequestBody VentasDto ventaDto,  HttpSession session) {

        Usuarios usuario = (Usuarios) session.getAttribute("usuarioActual");
            if(usuario==null){
            // Si el usuario está autenticado, podemos continuar
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Redirigir a la página de inicio de sesión
        }

        logger.info("Datos recibidos: {}", ventaDto);

        //obtener el cliente
        Clientes cliente = clienteService.buscarPorId(ventaDto.getCliente_id().getId());
        logger.info("Cliente encontrado: {}", cliente);

        //obtener al usuario
        
        logger.info("Usuario encontrado: {}", usuario);

        //obtenemos la fecha actual
        Date fechaActual = new Date();

        //obtenemos el total de la venta
        double totalVenta = ventaDto.getTotal();

        //cramos la venta
        Ventas ventas = new Ventas();
        ventas.setCliente_id(cliente);
        ventas.setUsuario_id(usuario);
        ventas.setTotal(totalVenta);
        ventas.setFecha(fechaActual);

        //guardamos la venta
        Ventas ventaGuardada = ventasServices.guardarVenta(ventas);
        logger.info("Ventas exitosa");
        

        ventaDto.getDetalles().stream().forEach(det -> {
            
            //creamos la variable detalle ventas
            DetalleVentas detalles = new DetalleVentas();
            //obtenemos el producto
            Productos producto = productoService.obtenerProductoPorId(det.getProducto_id().getId()).orElseThrow(()-> new RuntimeException("Producto no encontrado"));
            producto.setCantidad(producto.getCantidad() - det.getCantidad());
            productoService.guardarProducto(producto);
            detalles.setCantidad(det.getCantidad());
            detalles.setSubtotal(det.getSubtotal());
            detalles.setProducto_id(producto);
            detalles.setVenta_id(ventaGuardada);

            DetalleVentas DetalleGuardado = detalleVentaService.guardar(detalles);
            logger.info("Detalle guardado: {}", DetalleGuardado);

        });

        return ResponseEntity.ok(ventas);
    }

}
