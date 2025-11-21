package com.DangerBook.Agendamiento.API.Agendamiento.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Column(nullable = false)
    private String subtotal;
}