package com.DangerBook.Agendamiento.API.Agendamiento.Service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.ServicioRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.service.ServicioService;

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

class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    private Servicio servicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        servicio = new Servicio();
        servicio.setIdServicio(1);
        servicio.setNombre("Corte clásico");
        servicio.setDescripcion("Corte tradicional de cabello");
        servicio.setPrecio("7000");
        servicio.setFoto("corte.jpg");
    }

    @Test
    void testListarTodos() {
        // Arrange
        List<Servicio> servicios = List.of(servicio);
        when(servicioRepository.findAll()).thenReturn(servicios);

        // Act
        List<Servicio> resultado = servicioService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Corte clásico", resultado.get(0).getNombre());
        verify(servicioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // Arrange
        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));

        // Act
        Servicio resultado = servicioService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdServicio());
        assertEquals("7000", resultado.getPrecio());
        verify(servicioRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // Arrange
        when(servicioRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> servicioService.buscarPorId(999));
        assertEquals("Servicio no encontrado", exception.getMessage());
    }

    @Test
    void testCrearServicio() {
        // Arrange
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        // Act
        Servicio resultado = servicioService.crear(servicio);

        // Assert
        assertNotNull(resultado);
        assertEquals("Corte clásico", resultado.getNombre());
        verify(servicioRepository, times(1)).save(servicio);
    }
}