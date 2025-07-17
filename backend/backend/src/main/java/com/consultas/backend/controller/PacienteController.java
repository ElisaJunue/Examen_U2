package com.consultas.backend.controller;

import com.consultas.backend.model.Paciente;
import com.consultas.backend.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.consultas.backend.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PacienteController {

    private final PacienteRepository repo;

    public PacienteController(PacienteRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/register")
    public Paciente registrar(@RequestBody Paciente p) {
        return repo.save(p);
    }

    @Operation(summary = "Login de paciente", description = "Autentica a un paciente usando correo y contraseña", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente autenticado correctamente", content = @Content(schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Paciente> p = repo.findByCorreo(loginRequest.getCorreo());

        if (p.isPresent() && p.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(p.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales inválidas");
    }
}
