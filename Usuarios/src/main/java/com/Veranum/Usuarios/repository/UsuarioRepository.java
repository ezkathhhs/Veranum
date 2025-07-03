package com.Veranum.Usuarios.repository;

import com.Veranum.Usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring Data JPA creará automáticamente la consulta para este metodo.
    // Lo usaremos para buscar un usuario por su correo.
    Optional<Usuario> findByCorreo(String correo);
}