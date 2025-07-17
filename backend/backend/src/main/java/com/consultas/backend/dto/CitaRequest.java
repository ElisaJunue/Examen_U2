package com.consultas.backend.dto;

public class CitaRequest {
    private Long idMedico;
    private Long idPaciente;
    private String fecha;

    // Constructor vacío necesario para deserialización JSON
    public CitaRequest() {}

    // Getters y setters
    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }

    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
