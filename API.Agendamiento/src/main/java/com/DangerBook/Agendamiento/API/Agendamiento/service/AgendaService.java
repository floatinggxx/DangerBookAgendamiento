package com.DangerBook.Agendamiento.API.Agendamiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    public List<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Agenda findById(Integer id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    }

    public Agenda save(Agenda agenda) {
        return agendaRepository.save(agenda);
    }
}
