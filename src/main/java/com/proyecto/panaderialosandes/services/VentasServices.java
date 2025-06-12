package com.proyecto.panaderialosandes.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panaderialosandes.models.Ventas;
import com.proyecto.panaderialosandes.repositorios.VentaRepository;

@Service
public class VentasServices {
    @Autowired
    private VentaRepository ventaRepository;;

    public Ventas guardarVenta(Ventas venta) {
        return ventaRepository.save(venta);
    }

}
