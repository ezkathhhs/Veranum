// js/register.js

document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');
    const errorMessage = document.getElementById('errorMessage');

    registerForm.addEventListener('submit', async (event) => {
        // Previene que el formulario se envíe de la forma tradicional (recargando la página)
        event.preventDefault();

        // 1. Obtener los datos del formulario
        const correo = document.getElementById('correo').value;
        const contrasena = document.getElementById('contrasena').value;

        errorMessage.textContent = ''; // Limpiar errores previos

        // 2. Enviar los datos al backend con Fetch
        try {
            const response = await fetch('http://localhost:8080/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ correo, contrasena }) // Convertimos los datos a JSON
            });

            const result = await response.json(); // Leemos la respuesta del backend

            // 3. Actuar según la respuesta
            if (response.ok) { // Si el backend respondió con un código 2xx (éxito)
                alert('¡Registro exitoso! Ahora puedes iniciar sesión.');
                window.location.href = 'index.html'; // Redirigir al login
            } else {
                // Si el backend respondió con un error (4xx o 5xx)
                errorMessage.textContent = result.message || 'Ocurrió un error.';
            }

        } catch (error) {
            // Error de red (ej: el backend no está corriendo)
            console.error('Error de conexión:', error);
            errorMessage.textContent = 'No se pudo conectar con el servidor. Inténtalo más tarde.';
        }
    });
});