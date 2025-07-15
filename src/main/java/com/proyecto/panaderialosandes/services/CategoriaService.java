package com.proyecto.panaderialosandes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.panaderialosandes.models.Categorias;
import com.proyecto.panaderialosandes.repositorios.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categorias> buscartodos(){
        return categoriaRepository.findAll();
    }

    public Object listarTodas() {
        return categoriaRepository.findAll();
    }
}
