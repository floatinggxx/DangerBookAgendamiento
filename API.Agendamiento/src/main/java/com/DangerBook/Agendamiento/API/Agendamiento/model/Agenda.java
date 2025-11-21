package com.DangerBook.Agendamiento.API.Agendamiento.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agenda")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    private Integer idAgenda;

    @Column(name = "fecha_solicitud", nullable = false)
    private String fechaSolicitud;

    @Column(nullable = false)
    private Double total;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_horario", nullable = false)
    private Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    private Detalle detalle;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;
}