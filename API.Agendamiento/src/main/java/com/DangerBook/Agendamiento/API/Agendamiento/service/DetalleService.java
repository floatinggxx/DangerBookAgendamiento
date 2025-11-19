package com.DangerBook.Agendamiento.API.Agendamiento.service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    public List<Detalle> listarTodos() {
        return detalleRepository.findAll();
    }

    public Detalle buscarPorId(Integer id) {
        return detalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
    }

    public Detalle crear(Detalle detalle) {
        return detalleRepository.save(detalle);
    }
}