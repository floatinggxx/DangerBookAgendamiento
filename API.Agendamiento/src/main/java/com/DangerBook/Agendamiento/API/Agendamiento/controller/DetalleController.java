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
    public ResponseEntity<List<Detalle>> listar() {
        List<Detalle> detalles = detalleService.listarTodos();
        
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        try {
            Detalle detalle = detalleService.buscarPorId(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Detalle> crear(@RequestBody Detalle detalle) {
        Detalle nuevoDetalle = detalleService.crear(detalle);
        return ResponseEntity.status(201).body(nuevoDetalle);
    }
}