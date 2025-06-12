function cambiarEstado(checkbox) {
    const nuevoEstado = checkbox.checked ? 'activo' : 'inactivo';
    const userId = checkbox.form.querySelector('input[name="id"]').value;

    userId.addEventListener('change', function() {
        const cambioLetra = document.getElementById('cambioletra');
        cambioLetra.textContent = checkbox.checked ? 'Activo' : 'Inactivo';});

    fetch('/principal/cambiar_estado', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `id=${userId}&nuevoEstado=${nuevoEstado}`
    }).then(response => {
        if (!response.ok) {
            checkbox.checked = !checkbox.checked; // Revertir si falla
        }
    });
}