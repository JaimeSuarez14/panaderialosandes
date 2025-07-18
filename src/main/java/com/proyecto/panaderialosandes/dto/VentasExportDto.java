package com.proyecto.panaderialosandes.dto;

import java.util.List;

import com.proyecto.panaderialosandes.models.Clientes;
import com.proyecto.panaderialosandes.models.Productos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VentasExportDto {
    private List<Clientes> clientes;
    private List<Productos> productos;
}
