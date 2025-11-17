package com.DangerBook.Agendamiento.API.Agendamiento.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.DetalleRepository;

@Service
@Transactional
public class DetalleService {

    private final DetalleRepository detalleRepository;

    public DetalleService(DetalleRepository detalleRepository) {
        this.detalleRepository = detalleRepository;
    }

    public List<Detalle> findAll() {
        return detalleRepository.findAll();
    }

    public Detalle findById(Integer id) {
        return detalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
    }

    public Detalle save(Detalle detalle) {
        return detalleRepository.save(detalle);
    }
}