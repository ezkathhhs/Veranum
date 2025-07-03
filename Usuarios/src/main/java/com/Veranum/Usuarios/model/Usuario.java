package com.Veranum.Usuarios.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Anotación de Lombok para generar getters, setters, toString, etc.
@Entity // Le dice a JPA que esta clase es una entidad que se mapea a una tabla.
@Table(name = "usuarios") // Especifica el nombre de la tabla en la base de datos.
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;
}
