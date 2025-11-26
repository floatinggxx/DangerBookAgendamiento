package com.DangerBook.Agendamiento.API.Agendamiento.controller;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agendas")
@CrossOrigin(origins = "http://localhost:5173")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    // obtener todas las agendas
    @GetMapping
    public ResponseEntity<List<Agenda>> findAll() {
        List<Agenda> lista = agendaService.findAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // obtener agenda por el id
    @GetMapping("/{id}")
    public ResponseEntity<Agenda> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(agendaService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // para guardar nueva reserva
    @PostMapping("/guardar")
    public ResponseEntity<Agenda> save(@RequestBody Agenda agenda) {
        return ResponseEntity.status(201).body(agendaService.save(agenda));
    }
}
