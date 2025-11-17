package com.DangerBook.Agendamiento.API.Agendamiento.controller;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.service.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/detalles")
public class DetalleController {
    @Autowired
    private DetalleService detalleService;

    @GetMapping
    public ResponseEntity<List<Detalle>> getAll() {
        List<Detalle> lista = detalleService.findAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Detalle> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(detalleService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Detalle> create(@RequestBody Detalle detalle) {
        return ResponseEntity.status(201).body(detalleService.save(detalle));
    }
}