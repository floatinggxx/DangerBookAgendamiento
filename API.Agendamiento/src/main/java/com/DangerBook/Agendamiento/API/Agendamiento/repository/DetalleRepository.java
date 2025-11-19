package com.DangerBook.Agendamiento.API.Agendamiento.repository;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
}