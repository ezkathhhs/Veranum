package com.Veranum.Usuarios.controller;

import com.Veranum.Usuarios.model.Usuario;
import com.Veranum.Usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/") // Todas las rutas de este controlador empezarán con /api
@CrossOrigin(origins = "http://127.0.0.1:5500") // ¡IMPORTANTE! Permite peticiones desde tu frontend
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Endpoint de Registro
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Usuario nuevoUsuario) {
        // 1. Validar si el correo ya existe
        if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // HTTP 409 Conflict
                    .body(Map.of("status", "error", "message", "El correo ya está en uso"));
        }

        // 2. Aquí deberíamos encriptar la contraseña antes de guardarla.
        // Por ahora la guardaremos en texto plano para simplificar.
        // ¡En el siguiente paso lo haremos de forma segura!
        // String hashedPassword = passwordEncoder.encode(nuevoUsuario.getPassword());
        // nuevoUsuario.setPassword(hashedPassword);

        // 3. Guardar en la base de datos
        usuarioRepository.save(nuevoUsuario);

        // 4. Responder con éxito
        return ResponseEntity
                .status(HttpStatus.CREATED) // HTTP 201 Created
                .body(Map.of("status", "ok", "message", "Usuario registrado exitosamente"));
    }

    // Endpoint de Inicio de Sesión
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> credentials) {
        String correo = credentials.get("correo");
        String contrasena = credentials.get("contrasena");

        // 1. Buscar el usuario por correo
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) // HTTP 401 Unauthorized
                    .body(Map.of("status", "error", "message", "Credenciales inválidas"));
        }

        // 2. Comparar la contraseña (actualmente en texto plano)
        Usuario usuario = usuarioOpt.get();
        if (!contrasena.equals(usuario.getContrasena())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) // HTTP 401 Unauthorized
                    .body(Map.of("status", "error", "message", "Credenciales inválidas"));
        }

        // 3. Responder con éxito
        return ResponseEntity
                .ok(Map.of("status", "ok", "message", "Inicio de sesión exitoso"));
    }
}