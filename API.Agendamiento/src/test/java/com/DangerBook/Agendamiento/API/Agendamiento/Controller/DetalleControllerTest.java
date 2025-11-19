package com.DangerBook.Agendamiento.API.Agendamiento.Controller;

import com.DangerBook.Agendamiento.API.Agendamiento.controller.DetalleController;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.service.DetalleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DetalleController.class)
class DetalleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleService detalleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Detalle detalle;

    @BeforeEach
    void setUp() {
        detalle = new Detalle();
        detalle.setIdDetalle(1);
        detalle.setSubtotal("5000");
    }

    @Test
    void testListarDetalles() throws Exception {
        // Arrange
        when(detalleService.listarTodos()).thenReturn(List.of(detalle));

        // Act & Assert
        mockMvc.perform(get("/api/v1/detalles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDetalle").value(1))
                .andExpect(jsonPath("$[0].subtotal").value("5000"));

        verify(detalleService, times(1)).listarTodos();
    }

    @Test
    void testListarDetallesVacio() throws Exception {
        // Arrange
        when(detalleService.listarTodos()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/v1/detalles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerPorIdExitoso() throws Exception {
        // Arrange
        when(detalleService.buscarPorId(1)).thenReturn(detalle);

        // Act & Assert
        mockMvc.perform(get("/api/v1/detalles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subtotal").value("5000"));

        verify(detalleService, times(1)).buscarPorId(1);
    }

    @Test
    void testObtenerPorIdNoEncontrado() throws Exception {
        // Arrange
        when(detalleService.buscarPorId(999))
                .thenThrow(new RuntimeException("Detalle no encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/detalles/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearDetalle() throws Exception {
        // Arrange
        when(detalleService.crear(any(Detalle.class))).thenReturn(detalle);

        // Act & Assert
        mockMvc.perform(post("/api/v1/detalles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(detalle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idDetalle").value(1));

        verify(detalleService, times(1)).crear(any(Detalle.class));
    }
}