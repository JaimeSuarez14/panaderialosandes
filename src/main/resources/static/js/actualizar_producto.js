document.addEventListener('DOMContentLoaded', function () {
    let dataProductos = [];
    let idProducto = "";

    async function cargarDatosVentas() {
        try {
            const response = await fetch("/productos/dataProductos");
            dataProductos = await response.json();
            console.log(dataProductos) // Simplificado
        } catch (error) {
            console.error("Error al cargar datos:", error);
        }
    }

    //datos del formulario
    const nombreProducto = document.getElementById("nombreEditar");
    const stockEditar = document.getElementById("stockEditar");
    const botonesEditar = document.querySelectorAll(".botonEditar")
    const btnGuardar = document.getElementById("btnGuardar");

    //al guardar el modal
    btnGuardar.addEventListener('click', guardarCambios);

    // Función para manejar el clic
    function manejarClickEditar(event) {
        const boton = event.currentTarget;
        idProducto = boton.getAttribute('data-id');
        console.log("ID usuario:", idProducto);

        // Aquí puedes cargar los datos del usuario en el modal
        const  producto = dataProductos.find(u => u.id == idProducto);
        console.log("Usuario encontrado:", producto);
        if (producto!=null) {
            nombreProducto.value = producto.nombre || '';
            stockEditar.value = parseInt(producto.cantidad) || '';

        }
    }

    // Cargar datos y configurar eventos
    cargarDatosVentas().then(() => {
        console.log("Datos cargados:", dataProductos);

        // Configurar event listeners para TODOS los botones de editar
        botonesEditar.forEach(boton => {
            boton.addEventListener('click', manejarClickEditar);
        });
    });

    function guardarCambios() {
        const nombre = nombreProducto.value.trim();
        const stock = stockEditar.value.trim();

        if (!idProducto || !nombre || !stock ) return;

        const id = idProducto;

        const datos = { 
            id: idProducto,
            nombre: nombre,
            cantidad: stock,
        };

        fetch(`/productos/actualizar/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datos)
        })
            .then(response => {
                if (response.ok) {
                    alert('Cambios guardados correctamente');
                    location.reload(); // Recargar la página
                }
            });
    }

});


