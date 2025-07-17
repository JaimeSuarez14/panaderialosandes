document.addEventListener('DOMContentLoaded', function () {
    
    const botonExportar =document.getElementById('btnExportarPDF');

    botonExportar.addEventListener('click', function() {
        // Captura los datos de la tabla
        const filas = Array.from(document.querySelectorAll('table tbody tr'))
            .filter(tr => !tr.classList.contains('text-center')); // Excluye fila vacía
    
        const datos = filas.map(tr => {
            const celdas = tr.querySelectorAll('td');
            return {
                numero: celdas[0]?.innerText,
                producto: celdas[1]?.innerText,
                categoria: celdas[2]?.innerText,
                stock: celdas[3]?.innerText,
                condicion: celdas[4]?.innerText,
                estado: celdas[5]?.innerText,
                fecha: celdas[6]?.innerText
            };
        });
    
        fetch('/productos/exportar-pdf', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        })
        .then(response => {
            if (!response.ok) throw new Error('Error al generar PDF');
            return response.blob();
        })
        .then(blob => {
            // Descarga el PDF
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'reporte_inventario.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
        })
        .catch(error => {
            alert('Error al exportar PDF');
            console.error(error);
        });
    }); 

});