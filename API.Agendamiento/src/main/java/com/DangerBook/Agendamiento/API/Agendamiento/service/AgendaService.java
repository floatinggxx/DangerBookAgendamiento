package com.DangerBook.Agendamiento.API.Agendamiento.service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public List<Agenda> obtenerTodas() {
        return agendaRepository.findAll();
    }

    public Agenda buscarPorId(Integer id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
    }

    public Agenda guardar(Agenda agenda) {
        return agendaRepository.save(agenda);
    }
 
    public void eliminar(Integer id) {
        agendaRepository.deleteById(id);
    }
}
