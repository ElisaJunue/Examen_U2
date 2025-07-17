package com.consultas.backend.controller;

import com.consultas.backend.model.Cita;
import com.consultas.backend.model.Medico;
import com.consultas.backend.model.Paciente;
import com.consultas.backend.repository.CitaRepository;
import com.consultas.backend.repository.MedicoRepository;
import com.consultas.backend.repository.PacienteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

import com.consultas.backend.dto.CitaRequest;

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
    public ResponseEntity<?> agendarCita(@RequestBody CitaRequest request) {
        try {
            Medico medico = medicoRepo.findById(request.getIdMedico())
                    .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));
            Paciente paciente = pacienteRepo.findById(request.getIdPaciente())
                    .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

            Cita cita = new Cita();
            cita.setFecha(LocalDate.parse(request.getFecha()));
            cita.setMedico(medico);
            cita.setPaciente(paciente);

            citaRepo.save(cita);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Cita guardada exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la cita: " + e.getMessage());
        }
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
                    map.put("idPaciente", cita.getPaciente().getId()); // <-- Aquí agregas el idPaciente
                    return map;
                })
                .toList();
    }

    @GetMapping("/paciente/{id}")
    public List<Map<String, Object>> citasPorPaciente(@PathVariable Long id) {
        return citaRepo.findByPacienteId(id).stream()
                .map(cita -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", cita.getId());
                    map.put("fecha", cita.getFecha().toString());
                    map.put("medico", cita.getMedico().getNombre());
                    map.put("especialidad", cita.getMedico().getEspecialidad());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable Long id) {
        citaRepo.deleteById(id);
    }
}
