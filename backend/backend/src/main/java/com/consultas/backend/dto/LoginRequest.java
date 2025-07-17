package com.consultas.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    @Schema(description = "Correo electrónico del paciente", example = "usuario@example.com")
    private String correo;

    @Schema(description = "Contraseña del paciente", example = "password123")
    private String password;

    // Getters y setters
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
