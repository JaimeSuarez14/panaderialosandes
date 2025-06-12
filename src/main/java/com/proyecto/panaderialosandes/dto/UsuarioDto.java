package com.proyecto.panaderialosandes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDto {
    private Integer id;
    private String nombre;
    private String username;
    private String rol;
    private String estado;
    
}
