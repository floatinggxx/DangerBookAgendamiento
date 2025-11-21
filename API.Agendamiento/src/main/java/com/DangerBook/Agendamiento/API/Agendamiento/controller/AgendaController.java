package com.DangerBook.Agendamiento.API.Agendamiento.controller;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping
    public ResponseEntity<List<Agenda>> listar() {
        List<Agenda> agendas = agendaService.obtenerTodas();
        
        if (agendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            Agenda agenda = agendaService.buscarPorId(id);
            return ResponseEntity.ok(agenda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Agenda no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Agenda agenda) {
        try {
            Agenda nuevaAgenda = agendaService.guardar(agenda);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaAgenda);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        agendaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}