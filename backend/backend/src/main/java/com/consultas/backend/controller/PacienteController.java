package com.consultas.backend.controller;

import com.consultas.backend.model.Paciente;
import com.consultas.backend.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> cred) {
        Optional<Paciente> p = repo.findByCorreo(cred.get("correo"));

        if (p.isPresent() && p.get().getPassword().equals(cred.get("password"))) {
            return ResponseEntity.ok(p.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Credenciales inválidas");
    }
}
