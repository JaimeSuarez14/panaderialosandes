document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const form = document.getElementById('reporteForm');
    const btnGenerar = document.getElementById('btnGenerar');
    const btnLimpiar = document.getElementById('btnLimpiar');
    const btnExportar = document.getElementById('btnExportar');
    const loading = document.getElementById('loading');
    const resultados = document.getElementById('resultados');
    const tablaBody = document.querySelector('#tablaResultados tbody');
    
    // Variables para almacenar datos
    let ventasData = [];
    let opcionesFiltros = {};
    let filtrosActuales = {};
    
    // Configurar fechas predeterminadas (últimos 7 días)
    configurarFechasPredeterminadas();
    
    // Cargar opciones de filtros al iniciar
    cargarOpcionesFiltros();
    
    // Evento para generar reporte
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        generarReporte();
    });
    
    // Evento para limpiar filtros
    btnLimpiar.addEventListener('click', function() {
        form.reset();
        resultados.style.display = 'none';
        // Restablecer fechas a los últimos 7 días al limpiar
        configurarFechasPredeterminadas();
    });
    
    // Evento para exportar datos
    btnExportar.addEventListener('click', exportarCSV);
    
    // Función para configurar fechas predeterminadas (últimos 7 días)
    function configurarFechasPredeterminadas() {
        const fechaHasta = new Date();
        const fechaDesde = new Date();
        fechaDesde.setDate(fechaHasta.getDate() - 7); // Restar 7 días
        
        // Formatear fechas como YYYY-MM-DD (formato de input date)
        const formatDate = (date) => {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
        };
        
        document.getElementById('fechaDesde').value = formatDate(fechaDesde);
        document.getElementById('fechaHasta').value = formatDate(fechaHasta);
        
        // Guardar en filtros actuales
        filtrosActuales.fechaDesde = formatDate(fechaDesde);
        filtrosActuales.fechaHasta = formatDate(fechaHasta);
    }
    
    // Función para cargar opciones de filtros
    async function cargarOpcionesFiltros() {
        try {
            const response = await fetch('/api/reporte-ventas/filtros');
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            const data = await response.json();
            
            if (data.error) {
                throw new Error(data.error);
            }
            
            opcionesFiltros = data;
            llenarSelectsFiltros();
        } catch (error) {
            console.error('Error al cargar filtros:', error);
            mostrarError('Error al cargar opciones de filtros: ' + error.message);
        }
    }
    
    // Llenar selects con opciones de filtros
    function llenarSelectsFiltros() {
        const selectCategoria = document.getElementById('categoria');
        const selectUsuario = document.getElementById('usuario');
        
        // Limpiar selects primero
        selectCategoria.innerHTML = '<option value="">Todas</option>';
        selectUsuario.innerHTML = '<option value="">Todos</option>';
        
        if (opcionesFiltros.categorias) {
            opcionesFiltros.categorias.forEach(cat => {
                const option = document.createElement('option');
                option.value = cat.id;
                option.textContent = cat.nombre;
                selectCategoria.appendChild(option);
            });
        }
        
        if (opcionesFiltros.usuarios) {
            opcionesFiltros.usuarios.forEach(user => {
                const option = document.createElement('option');
                option.value = user.id;
                option.textContent = user.nombre;
                selectUsuario.appendChild(option);
            });
        }
    }
    
    // Función principal para generar el reporte
    async function generarReporte() {
        const fechaDesde = document.getElementById('fechaDesde').value;
        const fechaHasta = document.getElementById('fechaHasta').value;
        const categoria = document.getElementById('categoria').value || null;
        const usuario = document.getElementById('usuario').value || null;
        const agruparPor = document.getElementById('agruparPor').value || null;
        
        if (!fechaDesde || !fechaHasta) {
            mostrarError('Debe seleccionar un rango de fechas');
            return;
        }
        
        // Validar que fechaDesde no sea mayor que fechaHasta
        if (new Date(fechaDesde) > new Date(fechaHasta)) {
            mostrarError('La fecha "Desde" no puede ser mayor que la fecha "Hasta"');
            return;
        }
        
        // Guardar filtros actuales para exportación
        filtrosActuales = { fechaDesde, fechaHasta, categoria, usuario };
        
        try {
            loading.style.display = 'block';
            resultados.style.display = 'none';
            
            const params = new URLSearchParams();
            params.append('fechaDesde', fechaDesde);
            params.append('fechaHasta', fechaHasta);
            if (categoria) params.append('categoriaId', categoria);
            if (usuario) params.append('usuarioId', usuario);
            
            const response = await fetch(`/api/reporte-ventas/ventas?${params.toString()}`);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.error || `Error HTTP: ${response.status}`);
            }
            
            ventasData = await response.json();
            
            if (ventasData.length === 0) {
                mostrarError('No se encontraron ventas con los filtros seleccionados');
                return;
            }
            
            mostrarResultados(ventasData, agruparPor);
            
        } catch (error) {
            console.error('Error:', error);
            mostrarError(error.message || 'Error al generar el reporte');
        } finally {
            loading.style.display = 'none';
        }
    }
    
    // Mostrar resultados en la tabla
    function mostrarResultados(data, agruparPor) {
        tablaBody.innerHTML = '';
        
        // Agrupar datos si es necesario
        if (agruparPor) {
            data = agruparDatos(data, agruparPor);
        }
        
        let total = 0;
        
        data.forEach(item => {
            const row = document.createElement('tr');
            
            // Formatear fecha
            const fecha = new Date(item[0]);
            const fechaFormateada = fecha.toLocaleDateString('es-PE');
            
            row.innerHTML = `
                <td>${fechaFormateada}</td>
                <td>${item[1] || 'N/A'}</td>
                <td>${item[2]}</td>
                <td>${item[3]}</td>
                <td>${item[4]}</td>
                <td class="text-end">S/ ${parseFloat(item[5]).toFixed(2)}</td>
                <td class="text-end">S/ ${parseFloat(item[6]).toFixed(2)}</td>
            `;
            
            tablaBody.appendChild(row);
            total += parseFloat(item[6]);
        });
        
        // Agregar fila de total
        const totalRow = document.createElement('tr');
        totalRow.className = 'table-active';
        totalRow.innerHTML = `
            <td colspan="6" class="text-end"><strong>Total:</strong></td>
            <td class="text-end"><strong>S/ ${total.toFixed(2)}</strong></td>
        `;
        tablaBody.appendChild(totalRow);
        
        resultados.style.display = 'block';
    }
    
    // Función para agrupar datos
    function agruparDatos(data, criterio) {
        const grupos = {};
        const indexMap = {
            'categoria': 3,
            'usuario': 1
        };
        
        const index = indexMap[criterio];
        
        data.forEach(item => {
            const key = item[index] || 'Sin ' + criterio;
            if (!grupos[key]) {
                grupos[key] = {
                    count: 0,
                    subtotal: 0,
                    firstItem: item
                };
            }
            grupos[key].count += item[4]; // Cantidad
            grupos[key].subtotal += item[6]; // Subtotal
        });
        
        // Convertir a array para mostrar
        return Object.keys(grupos).map(key => {
            const grupo = grupos[key];
            const item = [...grupo.firstItem];
            item[4] = grupo.count;
            item[6] = grupo.subtotal;
            return item;
        });
    }
    
    // Exportar a CSV
    async function exportarCSV() {
        if (ventasData.length === 0) {
            mostrarError('No hay datos para exportar');
            return;
        }
        
        try {
            const { fechaDesde, fechaHasta, categoria, usuario } = filtrosActuales;
            
            const params = new URLSearchParams();
            params.append('fechaDesde', fechaDesde);
            params.append('fechaHasta', fechaHasta);
            if (categoria) params.append('categoriaId', categoria);
            if (usuario) params.append('usuarioId', usuario);
            
            const response = await fetch(`/api/reporte-ventas/exportar?${params.toString()}`);
            
            if (!response.ok) {
                throw new Error('Error al exportar datos');
            }
            
            const blob = await response.blob();
            const url = URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `reporte_ventas_${new Date().toISOString().slice(0,10)}.csv`);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            
        } catch (error) {
            console.error('Error al exportar:', error);
            mostrarError('Error al exportar el reporte');
        }
    }
    
    // Mostrar mensaje de error
    function mostrarError(mensaje) {
        const alertContainer = document.getElementById('alertContainer');
        alertContainer.innerHTML = '';
        
        const errorDiv = document.createElement('div');
        errorDiv.className = 'alert alert-danger alert-dismissible fade show';
        errorDiv.innerHTML = `
            <i class="fas fa-exclamation-circle me-2"></i>
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        
        alertContainer.appendChild(errorDiv);
        
        // Eliminar el mensaje después de 5 segundos
        setTimeout(() => {
            errorDiv.remove();
        }, 5000);
    }
});