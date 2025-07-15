package com.proyecto.panaderialosandes.services;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Service
public class ReporteVentaService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> obtenerDatosVentas(String fechaDesde, String fechaHasta, 
                                           Long categoriaId, Long usuarioId, Long clienteId) {
        
        String sql = "SELECT " +
                     "v.fecha as fecha, " +
                     "u.nombre as usuario, " +
                     "p.nombre as producto, " +
                     "c.nombre as categoria, " +
                     "dv.cantidad as cantidad, " +
                     "p.precio as precio, " +
                     "dv.subtotal as subtotal " +
                     "FROM detalleventas dv " +
                     "JOIN ventas v ON dv.venta_id = v.id " +
                     "JOIN usuarios u ON v.usuario_id = u.id " +
                     "JOIN productos p ON dv.producto_id = p.id " +
                     "JOIN categorias c ON p.categoria_id = c.id " +
                     "WHERE v.fecha BETWEEN :fechaDesde AND :fechaHasta ";
        
        if (categoriaId != null) {
            sql += "AND c.id = :categoriaId ";
        }
        if (usuarioId != null) {
            sql += "AND u.id = :usuarioId ";
        }
        if (clienteId != null) {
            sql += "AND v.cliente_id = :clienteId ";
        }
        
        sql += "ORDER BY v.fecha DESC";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("fechaDesde", fechaDesde);
        query.setParameter("fechaHasta", fechaHasta);
        
        if (categoriaId != null) {
            query.setParameter("categoriaId", categoriaId);
        }
        if (usuarioId != null) {
            query.setParameter("usuarioId", usuarioId);
        }
        if (clienteId != null) {
            query.setParameter("clienteId", clienteId);
        }
        
        return query.getResultList();
    }
}
