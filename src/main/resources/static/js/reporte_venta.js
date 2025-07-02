document.addEventListener('DOMContentLoaded', function() {
    console.log('Script de reporte de ventas cargado');

    // Elementos del DOM
    const formReporte = document.getElementById('reporteForm');
    const fechaDesdeInput = document.getElementById('fechaDesde');
    const fechaHastaInput = document.getElementById('fechaHasta');
    const categoriaSelect = document.getElementById('categoria');
    const usuarioSelect = document.getElementById('usuario');
    const btnGenerar = document.getElementById('btnGenerar');
    const btnExportar = document.getElementById('btnExportar');
    const errorMessage = document.getElementById('errorMessage');
    const noResults = document.getElementById('noResults');
    const resultsTable = document.getElementById('resultsTable');

    // Establecer fechas por defecto (hoy y hace 7 días)
    const hoy = new Date();
    const hace7Dias = new Date();
    hace7Dias.setDate(hoy.getDate() - 7);

    fechaDesdeInput.valueAsDate = hace7Dias;
    fechaHastaInput.valueAsDate = hoy;

    // Manejar el envío del formulario
    formReporte.addEventListener('submit', function(e) {
        e.preventDefault();
        console.log('Formulario enviado');
        
        // Validar fechas
        const fechaDesde = fechaDesdeInput.value;
        const fechaHasta = fechaHastaInput.value;
        
        if (new Date(fechaDesde) > new Date(fechaHasta)) {
            mostrarError('La fecha "Desde" no puede ser mayor a la fecha "Hasta"');
            return;
        }

        // Mostrar estado de carga
        btnGenerar.disabled = true;
        btnGenerar.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Generando...';

        // Obtener otros valores
        const categoria = categoriaSelect.value;
        const usuario = usuarioSelect.value;

        // Realizar la petición
        fetch(`${formReporte.action}?fechaDesde=${fechaDesde}&fechaHasta=${fechaHasta}&categoria=${categoria}&usuario=${usuario}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error en la respuesta del servidor');
                }
                return response.text();
            })
            .then(html => {
                // Recargar la página con los nuevos parámetros
                window.location.href = `${formReporte.action}?fechaDesde=${fechaDesde}&fechaHasta=${fechaHasta}&categoria=${categoria}&usuario=${usuario}`;
            })
            .catch(error => {
                console.error('Error:', error);
                mostrarError('Ocurrió un error al generar el reporte. Por favor, intente nuevamente.');
            })
            .finally(() => {
                btnGenerar.disabled = false;
                btnGenerar.innerHTML = '<i class="fas fa-file-alt me-2"></i>Generar Reporte';
            });
    });

    // Función para mostrar errores
    function mostrarError(mensaje) {
        if (errorMessage) {
            errorMessage.textContent = mensaje;
            errorMessage.style.display = 'block';
        }
        
        if (noResults) noResults.style.display = 'none';
        if (resultsTable) resultsTable.style.display = 'none';
    }

    // Actualizar enlace de exportación cuando cambian los filtros
    function actualizarEnlaceExportacion() {
        const params = new URLSearchParams();
        params.append('fechaDesde', fechaDesdeInput.value);
        params.append('fechaHasta', fechaHastaInput.value);
        params.append('categoria', categoriaSelect.value);
        params.append('usuario', usuarioSelect.value);
        
        btnExportar.href = `/reportes/exportar?${params.toString()}`;
    }

    // Event listeners para cambios en los filtros
    fechaDesdeInput.addEventListener('change', actualizarEnlaceExportacion);
    fechaHastaInput.addEventListener('change', actualizarEnlaceExportacion);
    categoriaSelect.addEventListener('change', actualizarEnlaceExportacion);
    usuarioSelect.addEventListener('change', actualizarEnlaceExportacion);

    // Inicializar
    actualizarEnlaceExportacion();
});