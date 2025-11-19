package com.DangerBook.Agendamiento.API.Agendamiento.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agenda")
@Data
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    private Integer idAgenda;

    @Column(name = "fecha_solicitud", nullable = false)
    private String fechaSolicitud;

    @Column(nullable = false)
    private Double total;

    // ID del usuario (microservicio Usuarios)
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    // ID del horario (microservicio Horarios)
    @Column(name = "id_horario", nullable = false)
    private Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    private Detalle detalle;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;
}