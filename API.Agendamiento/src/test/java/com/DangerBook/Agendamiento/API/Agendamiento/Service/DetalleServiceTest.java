package com.DangerBook.Agendamiento.API.Agendamiento.Service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.DetalleRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.service.DetalleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DetalleServiceTest {

    @Mock
    private DetalleRepository detalleRepository;

    @InjectMocks
    private DetalleService detalleService;

    private Detalle detalle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        detalle = new Detalle();
        detalle.setIdDetalle(1);
        detalle.setSubtotal("5000");
    }

    @Test
    void testListarTodos() {
        // Arrange
        List<Detalle> detalles = List.of(detalle);
        when(detalleRepository.findAll()).thenReturn(detalles);

        // Act
        List<Detalle> resultado = detalleService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("5000", resultado.get(0).getSubtotal());
        verify(detalleRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // Arrange
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalle));

        // Act
        Detalle resultado = detalleService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetalle());
        assertEquals("5000", resultado.getSubtotal());
        verify(detalleRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // Arrange
        when(detalleRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> detalleService.buscarPorId(999));
        assertEquals("Detalle no encontrado", exception.getMessage());
    }

    @Test
    void testCrearDetalle() {
        // Arrange
        when(detalleRepository.save(any(Detalle.class))).thenReturn(detalle);

        // Act
        Detalle resultado = detalleService.crear(detalle);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetalle());
        verify(detalleRepository, times(1)).save(detalle);
    }
}