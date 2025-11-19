package com.DangerBook.Agendamiento.API.Agendamiento.service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.webclient.HorariosCliente;
import com.DangerBook.Agendamiento.API.Agendamiento.webclient.UsuariosCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;
    
    @Autowired
    private UsuariosCliente usuariosClient;
    
    @Autowired
    private HorariosCliente horariosClient;

    public List<Agenda> obtenerTodas() {
        return agendaRepository.findAll();
    }

    public Agenda buscarPorId(Integer id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isPresent()) {
            return agenda.get();
        } else {
            throw new RuntimeException("No se encontro la agenda con id: " + id);
        }
    }

    public Agenda guardar(Agenda agenda) {
        // validar que el usuario existe
        Map<String, Object> usuario = usuariosClient.obtenerUsuario(agenda.getIdUsuario());
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }

        // validar que el horario existe
        Map<String, Object> horario = horariosClient.obtenerHorario(agenda.getIdHorario());
        if (horario == null) {
            throw new RuntimeException("El horario no existe");
        }

        // si todo esta bien, guardamos
        return agendaRepository.save(agenda);
    }

    public void eliminar(Integer id) {
        agendaRepository.deleteById(id);
    }
}