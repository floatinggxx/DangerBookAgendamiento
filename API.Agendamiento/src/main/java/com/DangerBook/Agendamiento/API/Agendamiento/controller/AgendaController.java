package com.DangerBook.Agendamiento.API.Agendamiento.controller;


import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @GetMapping
    public ResponseEntity<List<Agenda>> getAll() {
        List<Agenda> lista = agendaService.findAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agenda> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(agendaService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Agenda> create(@RequestBody Agenda agenda) {
        return ResponseEntity.status(201).body(agendaService.save(agenda));
    }
}
