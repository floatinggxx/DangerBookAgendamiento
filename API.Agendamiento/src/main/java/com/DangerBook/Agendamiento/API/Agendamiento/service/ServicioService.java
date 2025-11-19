package com.DangerBook.Agendamiento.API.Agendamiento.service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Servicio buscarPorId(Integer id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
    }

    public Servicio crear(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
}