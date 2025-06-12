package com.proyecto.panaderialosandes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteDto {

    private String nombre;
    private String dni;
    private String correo;
    private String telefono;
    private String direccion;
}