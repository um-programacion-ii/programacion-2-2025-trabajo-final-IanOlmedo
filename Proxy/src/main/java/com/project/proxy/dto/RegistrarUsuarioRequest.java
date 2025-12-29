package com.project.proxy.dto;

import lombok.Data;
@Data
public class RegistrarUsuarioRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String nombreAlumno;
    private String descripcionProyecto;
}
