package com.consultas.backend.model;

import jakarta.persistence.*;

@Entity
public class Medico {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String especialidad;
    private String horario;

    // Getters y Setters (puedes generarlos con tu IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}
