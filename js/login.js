// js/login.js

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('errorMessage');

    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        // 1. Obtener los datos
        const correo = document.getElementById('correo').value;
        const contrasena = document.getElementById('contrasena').value;
        errorMessage.textContent = '';

        // 2. Enviar al backend
        try {
            const response = await fetch('http://localhost:8080/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ correo, contrasena })
            });

            const result = await response.json();

            // 3. Actuar según la respuesta
            if (response.ok) {
                // Si el login es correcto, redirigimos a la página principal
                window.location.href = 'inicio.html';
            } else {
                errorMessage.textContent = result.message || 'Credenciales incorrectas.';
            }

        } catch (error) {
            console.error('Error de conexión:', error);
            errorMessage.textContent = 'No se pudo conectar con el servidor.';
        }
    });
});