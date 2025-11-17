package com.DangerBook.Agendamiento.API.Agendamiento.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;

@Service
@Transactional
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Agenda findById(Integer id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
    }

    public Agenda save(Agenda agenda) {
        return agendaRepository.save(agenda);
    }
}
   