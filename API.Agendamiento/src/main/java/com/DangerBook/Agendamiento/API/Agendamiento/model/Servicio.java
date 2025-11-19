package com.DangerBook.Agendamiento.API.Agendamiento.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "servicio")
@Data
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    private String foto;

    @Column(nullable = false)
    private String precio;
}