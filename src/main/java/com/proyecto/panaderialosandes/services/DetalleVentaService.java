package com.proyecto.panaderialosandes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panaderialosandes.models.DetalleVentas;
import com.proyecto.panaderialosandes.repositorios.DetalleVentaRepository;

@Service
public class DetalleVentaService {
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVentas> getAllDetalleVentas() {
        return detalleVentaRepository.findAll();
    }

    public DetalleVentas guardar(DetalleVentas detalle) {
        return detalleVentaRepository.save(detalle);
    } 
}
