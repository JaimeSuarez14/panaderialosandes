package com.proyecto.panaderialosandes.dto;

import com.proyecto.panaderialosandes.models.Productos;
import com.proyecto.panaderialosandes.models.Ventas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDto {

    private Ventas venta_id;
    private double subtotal;
    private int cantidad;
    private Productos producto_id;
}

