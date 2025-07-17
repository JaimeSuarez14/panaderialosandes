const usuarioLogeado = JSON.parse(localStorage.getItem('usuario'));
console.log(usuarioLogeado);
if (usuarioLogeado != null) {
    document.getElementById("nombreUsuario").textContent = usuarioLogeado.usuario;
    document.getElementById("rolUsuario").textContent = usuarioLogeado.rol;
}

const botonCerrar = document.querySelector('.botonCerrar');
botonCerrar.addEventListener('click', function (event) {
    localStorage.removeItem('usuario');
});