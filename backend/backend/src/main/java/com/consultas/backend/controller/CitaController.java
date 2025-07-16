package com.consultas.backend.controller;

import com.consultas.backend.model.Cita;
import com.consultas.backend.model.Medico;
import com.consultas.backend.model.Paciente;
import com.consultas.backend.repository.CitaRepository;
import com.consultas.backend.repository.MedicoRepository;
import com.consultas.backend.repository.PacienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

@RestController
@RequestMapping("/appointments")
public class CitaController {

    private final CitaRepository citaRepo;
    private final MedicoRepository medicoRepo;
    private final PacienteRepository pacienteRepo;

    public CitaController(CitaRepository citaRepo, MedicoRepository medicoRepo, PacienteRepository pacienteRepo) {
        this.citaRepo = citaRepo;
        this.medicoRepo = medicoRepo;
        this.pacienteRepo = pacienteRepo;
    }

    @PostMapping
    public Map<String, Object> agendar(@RequestBody Map<String, String> datos) {
        // Buscar médico por nombre
        Medico medico = medicoRepo.findAll().stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(datos.get("medico")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        // Usar paciente fijo (el primero de la base)
        Paciente paciente = pacienteRepo.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        // Crear la cita
        Cita cita = new Cita();
        cita.setFecha(LocalDate.parse(datos.get("fecha")));
        cita.setMedico(medico);
        cita.setPaciente(paciente);

        cita = citaRepo.save(cita);

        // Formato para frontend
        Map<String, Object> response = new HashMap<>();
        response.put("appointment", Map.of(
                "id", cita.getId(),
                "medico", medico.getNombre(),
                "especialidad", medico.getEspecialidad(),
                "fecha", cita.getFecha().toString()));
        return response;
    }

    @GetMapping
    public List<Map<String, Object>> listarCitas() {
        return citaRepo.findAll().stream()
                .map(cita -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", cita.getId());
                    map.put("medico", cita.getMedico().getNombre());
                    map.put("especialidad", cita.getMedico().getEspecialidad());
                    map.put("fecha", cita.getFecha().toString());
                    return map;
                })
                .toList();
    }

    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable Long id) {
        citaRepo.deleteById(id);
    }
}
