document.addEventListener('DOMContentLoaded', function() {
    // Variables
    const categoriaSelect = document.getElementById('categoria');
    const productoSelect = document.getElementById('producto');
    const precioInput = document.getElementById('precio');
    const cantidadInput = document.getElementById('cantidad');
    const agregarBtn = document.getElementById('agregar');
    const dniInput = document.getElementById('dni');
    const buscarClienteBtn = document.getElementById('buscarCliente');
    const nombreClienteInput = document.getElementById('nombreCliente');
    const celularInput = document.getElementById('celular');
    const correoInput = document.getElementById('correo');
    const direccionInput = document.getElementById('direccion');
    const venderBtn = document.getElementById('vender');
    const productosTable = document.getElementById('productosTable').getElementsByTagName('tbody')[0];
    const resumenSection = document.getElementById('resumenVenta');

    // Cargar productos cuando se selecciona categoría
    categoriaSelect.addEventListener('change', function() {
        const categoriaId = this.value;
        if (categoriaId) {
            // Aquí iría la llamada AJAX para obtener productos de la categoría
            // Por ahora usamos datos de ejemplo
            const productos = obtenerProductosPorCategoria(categoriaId);
            llenarProductosSelect(productos);
        }
    });

    // Actualizar precio cuando se selecciona producto
    productoSelect.addEventListener('change', function() {
        const productoId = this.value;
        if (productoId) {
            // Obtener precio del producto seleccionado
            const precio = obtenerPrecioProducto(productoId);
            precioInput.value = precio.toFixed(2);
        }
    });

    // Agregar producto a la tabla
    agregarBtn.addEventListener('click', function() {
        const productoId = productoSelect.value;
        const productoText = productoSelect.options[productoSelect.selectedIndex].text;
        const precio = parseFloat(precioInput.value);
        const cantidad = parseInt(cantidadInput.value);

        if (productoId && precio > 0 && cantidad > 0) {
            agregarProductoATabla(productoId, productoText, precio, cantidad);
            actualizarResumenVenta();
            limpiarCamposProducto();
        }
    });

    // Buscar cliente por DNI
    buscarClienteBtn.addEventListener('click', function() {
        const dni = dniInput.value;
        if (dni) {
            // Aquí iría la llamada AJAX para buscar cliente
            // Por ahora usamos datos de ejemplo
            const cliente = buscarClientePorDNI(dni);
            if (cliente) {
                nombreClienteInput.value = cliente.nombre;
                celularInput.value = cliente.celular;
                correoInput.value = cliente.correo;
                direccionInput.value = cliente.direccion;
            }
        }
    });

    // Función para agregar producto a la tabla
    function agregarProductoATabla(id, nombre, precio, cantidad) {
        const row = productosTable.insertRow();
        const subtotal = precio * cantidad;

        row.innerHTML = `
            <td>${nombre}</td>
            <td class="text-right">${precio.toFixed(2)}</td>
            <td class="text-center">${cantidad}</td>
            <td class="text-right">${subtotal.toFixed(2)}</td>
            <td class="text-center">
                <button class="btn-eliminar" data-id="${id}">×</button>
            </td>
        `;

        // Agregar evento para eliminar producto
        row.querySelector('.btn-eliminar').addEventListener('click', function() {
            row.remove();
            actualizarResumenVenta();
        });
    }

    // Función para actualizar el resumen de la venta
    function actualizarResumenVenta() {
        let subtotal = 0;
        const rows = productosTable.querySelectorAll('tr');

        rows.forEach(row => {
            const subtotalText = row.cells[3].textContent;
            subtotal += parseFloat(subtotalText);
        });

        const igv = subtotal * 0.18;
        const total = subtotal + igv;

        resumenSection.innerHTML = `
            <h3>Resumen de Venta</h3>
            <div class="resumen-item">
                <span>Subtotal:</span>
                <span>S/. ${subtotal.toFixed(2)}</span>
            </div>
            <div class="resumen-item">
                <span>IGV (18%):</span>
                <span>S/. ${igv.toFixed(2)}</span>
            </div>
            <div class="resumen-item total">
                <span>Total:</span>
                <span>S/. ${total.toFixed(2)}</span>
            </div>
        `;
    }

    // Funciones de ejemplo (deberían ser reemplazadas por llamadas AJAX) 
    /*
    function obtenerProductosPorCategoria(categoriaId) {
        // Datos de ejemplo
        return [
            {id: '1', nombre: 'Producto 1', precio: 10.50},
            {id: '2', nombre: 'Producto 2', precio: 15.75},
            {id: '3', nombre: 'Producto 3', precio: 20.00}
        ];
    }

    function llenarProductosSelect(productos) {
        productoSelect.innerHTML = '<option value="">-- Seleccione --</option>';
        productos.forEach(producto => {
            const option = document.createElement('option');
            option.value = producto.id;
            option.textContent = producto.nombre;
            productoSelect.appendChild(option);
        });
    }

    function obtenerPrecioProducto(productoId) {
        // Datos de ejemplo
        const productos = {
            '1': 10.50,
            '2': 15.75,
            '3': 20.00
        };
        return productos[productoId] || 0;
    }

    function buscarClientePorDNI(dni) {
        // Datos de ejemplo
        const clientes = {
            '12345678': {
                nombre: 'Juan Pérez',
                celular: '987654321',
                correo: 'juan@example.com',
                direccion: 'Av. Siempre Viva 123'
            }
        };
        return clientes[dni];
    }

    function limpiarCamposProducto() {
        productoSelect.value = '';
        precioInput.value = '';
        cantidadInput.value = '1';
    }*/
});
