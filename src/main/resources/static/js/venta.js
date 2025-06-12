document.addEventListener("DOMContentLoaded", function () {
    // Variables globales
    let productosAgregados = [];
    let clienteActual = null;
    let clientes = [];
    let productos = [];
    let totalVenta = null;

    // Función para cargar los datos de clientes y productos desde el servidor
    async function cargarDatosVentas() {
        try {
            const response = await fetch("/venta/datosventas");
            const data = await response.json();

            // Usamos reduce para acumular todos los clientes y productos
            clientes = data.clientes || [];
            productos = data.productos || [];

            productos.forEach((element) => {
                console.log("productos:", element.categoria_id);
            });

            return { clientes, productos };
        } catch (error) {
            console.error("Error al cargar datos:", error);
            throw error;
        }
    }

    // Cargar los datos de clientes y productos al iniciar la página
    cargarDatosVentas().then(() => {
        console.log("Informacion para la venta cargados correctamente");
    });

    // Elementos del DOM que vamos a usarlos
    const categoriaSelect = document.getElementById('categoria');
    const productoSelect = document.getElementById('producto');
    const precioInput = document.getElementById('precio');
    const cantidadInput = document.getElementById('cantidad');
    const agregarBtn = document.getElementById('agregarProducto');
    const detallesTable = document.getElementById('detallesVenta');
    const totalVentaSpan = document.getElementById('totalVenta');
    const dniInput = document.getElementById('dni');
    const buscarClienteBtn = document.getElementById('buscarCliente');
    const nombreClienteInput = document.getElementById('nombreCliente');
    const celularInput = document.getElementById('celular');
    const correoInput = document.getElementById('correo');
    const direccionInput = document.getElementById('direccion');
    const procesarVentaBtn = document.getElementById('procesarVenta');
    const cancelarVentaBtn = document.getElementById('cancelarVenta');

    // // Eventos que estan escuchando los elementos del DOM
    categoriaSelect.addEventListener('change', cargarProductosPorCategoria);
    productoSelect.addEventListener('change', actualizarPrecio);
    agregarBtn.addEventListener('click', agregarProductoAVenta);
    buscarClienteBtn.addEventListener("click", buscarClientePorDNI);
    procesarVentaBtn.addEventListener('click', procesarVenta);
    cancelarVentaBtn.addEventListener('click', limpiarFormulario);

    // Funciones para manejar la lógica de la venta

    function cargarProductosPorCategoria() {
        const categoriaId = categoriaSelect.value;

        if (!categoriaId) {
            productoSelect.innerHTML = '<option value="">-- Seleccione --</option>';
            return;
        }

        productoSelect.innerHTML = '<option value="">-- Seleccione --</option>';

        productos.forEach(producto => {

            if(producto.categoria_id.id == categoriaId){
                // Crear una opción para el producto
                // Verificar si el producto ya existe en el select
                const option = document.createElement('option');
                option.value = producto.id;
                option.textContent = `${producto.nombre} - S/${producto.precio.toFixed(2)}`;
                option.dataset.precio = producto.precio; //esta opcion sirve para guardar el precio del producto
                productoSelect.appendChild(option);
            }                
        });
        

        precioInput.value = '';


    }

    function actualizarPrecio() {
        const selectedOption = productoSelect.options[productoSelect.selectedIndex];
        if (selectedOption && selectedOption.dataset.precio) {
            precioInput.value = parseFloat(selectedOption.dataset.precio).toFixed(2);
        } else {
            precioInput.value = '';
        }
    }

    function agregarProductoAVenta() {
        const productoId = productoSelect.value;
        const productoNombre = productoSelect.options[productoSelect.selectedIndex].text.split(' - ')[0];
        const precio = parseFloat(precioInput.value);
        const cantidad = parseInt(cantidadInput.value);

        if (!productoId || !precio || !cantidad || cantidad < 1) {
            alert('Por favor complete todos los campos correctamente');
            return;
        }

        if (!validarStock(productoId, cantidad)) {
            return;
        }

        //actualizar la cantidad del producto en el inventario
        productos.forEach(producto =>{ 
            if(producto.id === parseInt(productoId)){
            producto.cantidad-=cantidad;}
        });

        // Verificar si el producto ya fue agregado
        const productoExistente = productosAgregados.find(p => p.id === productoId);

        
        if (productoExistente) {
            productoExistente.cantidad += cantidad;
            productoExistente.subtotal = productoExistente.cantidad * productoExistente.precio;
        } else {
            productosAgregados.push({
                id: productoId,
                nombre: productoNombre,
                precio: precio,
                cantidad: cantidad,
                subtotal: precio * cantidad
            });
        }

        actualizarTablaProductos();
        calcularTotal();

        // Limpiar campos
        productoSelect.selectedIndex = 0;
        precioInput.value = '';
        cantidadInput.value = 1;
        categoriaSelect.focus();
    }

    function actualizarTablaProductos() {
        detallesTable.innerHTML = '';

        productosAgregados.forEach((producto, index) => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${producto.nombre}</td>
                <td class="text-end">S/${producto.precio.toFixed(2)}</td>
                <td class="text-center">${producto.cantidad}</td>
                <td class="text-end">S/${producto.subtotal.toFixed(2)}</td>
                <td class="text-center">
                    <button class="btn btn-sm btn-success btn-agregar" data-index="${index}">
                        <i class="fa fa-plus" aria-hidden="true"></i>
                    </button>

                    <button class="btn btn-sm btn-warning btn-disminuir" data-index="${index}">
                        <i class="fa fa-minus" aria-hidden="true"></i>
                    </button>

                    <button class="btn btn-sm btn-danger btn-eliminar" data-index="${index}">
                        <i class="fas fa-trash"></i>
                    </button>

                </td>
            `;

            detallesTable.appendChild(row);
        });

        // Agregar event listeners a los botones eliminar
        document.querySelectorAll('.btn-eliminar').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                productos.forEach(producto =>{ 
                    if(producto.id === parseInt(productosAgregados[index].id)){
                    producto.cantidad+=productosAgregados[index].cantidad;}
                });
                productosAgregados.splice(index, 1);
                actualizarTablaProductos();
                calcularTotal();
            });
        });

        // Agregar event listeners a los botones agregar
        document.querySelectorAll('.btn-agregar').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                const producto = productosAgregados[index];
                if (producto && validarStock(producto.id, 1)) {
                    
                    productos.forEach(producto =>{ 
                        if(producto.id === productosAgregados[index].id){
                        producto.cantidad-=1;}
                    });
                    
                    producto.cantidad++;
                    producto.subtotal = producto.cantidad * producto.precio;
                    actualizarTablaProductos();
                    calcularTotal();
                }
            });
        });

        // Agregar event listeners a los botones disminuir
        document.querySelectorAll('.btn-disminuir').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                const producto = productosAgregados[index];

                const buscado = productos.find(p => p.id === parseInt(producto.id));

                if (producto && producto.cantidad > 1 && !(buscado.cantidad<-1)) {
                    productos.forEach(p =>{ 
                        if(p.id === parseInt(producto.id)){
                        p.cantidad+=1;
                        console.log("cantidad del producto: "+p.cantidad);
                    }
                        
                    });
                    producto.cantidad--;
                    producto.subtotal = producto.cantidad * producto.precio;
                    actualizarTablaProductos();
                    calcularTotal();
                } else if (producto) {

                    productos.forEach(p =>{ 
                        if(p.id === parseInt(producto.id)){
                        p.cantidad+=1;
                        console.log("cantidad del producto: "+p.cantidad);}
                    });
                    // Si la cantidad es 1, eliminar el producto de la lista
                    productosAgregados.splice(index, 1);
                    actualizarTablaProductos();
                    calcularTotal();
                }
            });
        });
    }

    function calcularTotal() {
        totalVenta = productosAgregados.reduce((sum, producto) => sum + producto.subtotal, 0);
        totalVentaSpan.textContent = `S/${totalVenta.toFixed(2)}`;
    }

    function validarStock(productoId, cantidad){
        const producto = productos.find(p => p.id === parseInt(productoId));
        console.log("cantidad productos: "+producto.cantidad," lo que compra es: "+cantidad)

        if(!producto){
            alert("Producto no encontrado");
            return false;
        }

        if (producto.cantidad < cantidad || producto.cantidad===0) {
            alert(`No hay suficiente stock para el producto ${producto.nombre}. Stock disponible: ${producto.cantidad}`);
            return false;
        }

        return true;
    }

    function buscarClientePorDNI() {
        const dni = dniInput.value.trim();

        if (!dni || dni.length !== 8) {
            alert("Por favor ingrese un DNI válido (8 dígitos)");
            return;
        }

        // Buscar cliente por DNI
        const encontrado = clientes.find(cliente => cliente.dni === dni);

        if (encontrado) {
            // Cliente encontrado, llenar datos
            nombreClienteInput.value = encontrado.nombre;
            celularInput.value = encontrado.telefono;
            correoInput.value = encontrado.correo;
            direccionInput.value = encontrado.direccion;
            clienteActual = encontrado;
        } else {
            // Cliente no encontrado, permitir edición
            nombreClienteInput.value = "";
            celularInput.value = "";
            correoInput.value = "";
            direccionInput.value = "";
            clienteActual = null;
            nombreClienteInput.focus();
            console.error("Error al buscar cliente:", dni);
            alert("Cliente no encontrado. Por favor, complete los datos del cliente.");
        }

    }

    function procesarVenta() {
        const dni = dniInput.value.trim();
        const nombre = nombreClienteInput.value.trim();
        const celular = celularInput.value.trim();
        const correo = correoInput.value.trim();
        const direccion = direccionInput.value.trim();

        // Validaciones
        if (!dni || dni.length !== 8) {
            alert('Por favor ingrese un DNI válido (8 dígitos)');
            return;
        }

        if (!nombre || !celular || !correo || !direccion) {
            alert('Por favor complete todos los datos del cliente');
            return;
        }

        if (productosAgregados.length === 0) {
            alert('Debe agregar al menos un producto a la venta');
            return;
        }

        // Crear objeto cliente (nuevo o existente)
        const cliente = clienteActual || {
            dni: dni,
            nombre: nombre,
            telefono: celular,
            correo: correo,
            direccion: direccion
        };

        // Si es cliente nuevo, guardarlo primero
        const guardarClientePromise = clienteActual
            ? Promise.resolve(clienteActual)
            : fetch('/venta/guardar_cliente', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cliente)
            }).then(response => response.json());

        guardarClientePromise
            .then(clienteGuardado => {  // Recibe el cliente guardado y continúa con el proceso
                // Crear objeto venta con la estructura necesaria
                const venta = {
                    cliente_id: { id: clienteGuardado.id },  // Asigna el ID del cliente guardado
                    detalles: productosAgregados.map(p => ({  // Mapea cada producto agregado a un formato de detalle
                        producto_id: { id: p.id },  // ID del producto
                        cantidad: p.cantidad,    // Cantidad seleccionada
                        subtotal: p.precio*p.cantidad // Precio unitario del producto
                    })),
                    total: totalVenta  // Total de la venta
                };

                // Envía la venta al servidor mediante una petición POST
                return fetch('/venta', {
                    method: 'POST',  // Método HTTP POST
                    headers: {
                        'Content-Type': 'application/json'  // Especifica que se envía JSON
                    },
                    body: JSON.stringify(venta)  // Convierte el objeto venta a JSON
                });
            })
            .then(response => {  // Maneja la respuesta del servidor
                if (response.ok) {  // Si la respuesta es exitosa
                    alert('Venta registrada exitosamente');  // Muestra mensaje de éxito
                    limpiarFormulario();  // Limpia el formulario
                } else {
                    throw new Error('Error al registrar la venta');  // Lanza error si falla
                }
            })
            .catch(error => {  // Captura cualquier error en el proceso
                console.error('Error:', error);  // Registra el error en la consola
                alert('Error al procesar la venta');  // Muestra mensaje de error al usuario
            });

    }

    function limpiarFormulario() {
        // Limpiar sección de productos
        categoriaSelect.selectedIndex = 0;
        productoSelect.innerHTML = '<option value="">-- Seleccione --</option>';
        precioInput.value = '';
        cantidadInput.value = 1;
        productosAgregados = [];
        detallesTable.innerHTML = '';
        totalVentaSpan.textContent = 'S/ 0.00';

        // Limpiar sección de cliente
        dniInput.value = '';
        nombreClienteInput.value = '';
        celularInput.value = '';
        correoInput.value = '';
        direccionInput.value = '';
        clienteActual = null;

        // Enfocar primer campo
        categoriaSelect.focus();
    }

    limpiarFormulario();
});
