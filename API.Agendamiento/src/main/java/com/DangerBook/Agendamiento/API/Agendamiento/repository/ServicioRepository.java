package com.DangerBook.Agendamiento.API.Agendamiento.repository;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
}