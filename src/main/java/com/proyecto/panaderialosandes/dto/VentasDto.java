package com.proyecto.panaderialosandes.dto;

import java.util.List;

import com.proyecto.panaderialosandes.models.Clientes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentasDto {

    private Clientes cliente_id;
    private List<DetalleVentaDto> detalles;
    private double total;
}