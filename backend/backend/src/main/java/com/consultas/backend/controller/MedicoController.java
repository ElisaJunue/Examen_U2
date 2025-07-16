package com.consultas.backend.controller;

import com.consultas.backend.model.Medico;
import com.consultas.backend.repository.MedicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class MedicoController {

    private final MedicoRepository repo;

    public MedicoController(MedicoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Medico> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Medico create(@RequestBody Medico m) {
        return repo.save(m);
    }
}
