package com.proyecto.panaderialosandes.services;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.proyecto.panaderialosandes.models.Categorias;
import com.proyecto.panaderialosandes.models.Productos;    

public class ProductoValidadorService {

    public void validarProducto(String nombre, double precio, int cantidad, String categoria, int unidad) {
        Preconditions.checkNotNull(nombre, "El nombre del producto no puede ser nulo.");
        Preconditions.checkArgument(precio > 0, "El precio debe ser mayor a 0.");
        Preconditions.checkArgument(cantidad >= 0, "La cantidad no puede ser negativa.");
        Preconditions.checkNotNull(categoria, "La categoria no puede ser nula.");
        Preconditions.checkArgument(unidad > 0, "La unidad no puede ser negativa.");
    }

    public void productosPorCategoria(Productos producto) {   

        Multimap<Categorias, String> productosPorCategoria = ArrayListMultimap.create();
        
        productosPorCategoria.put(producto.getCategoria_id(), producto.getNombre());
       

        System.out.println(productosPorCategoria);
    }
}

    

