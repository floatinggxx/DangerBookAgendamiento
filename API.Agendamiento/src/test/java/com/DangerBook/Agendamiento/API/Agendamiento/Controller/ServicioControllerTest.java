package com.DangerBook.Agendamiento.API.Agendamiento.Controller;

import com.DangerBook.Agendamiento.API.Agendamiento.controller.ServicioController;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import com.DangerBook.Agendamiento.API.Agendamiento.service.ServicioService;
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

@WebMvcTest(ServicioController.class)
class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioService servicioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Servicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new Servicio();
        servicio.setIdServicio(1);
        servicio.setNombre("Corte clásico");
        servicio.setDescripcion("Corte tradicional de cabello");
        servicio.setPrecio("7000");
        servicio.setFoto("corte.jpg");
    }

    @Test
    void testListarServicios() throws Exception {
        // Arrange
        when(servicioService.listarTodos()).thenReturn(List.of(servicio));

        // Act & Assert
        mockMvc.perform(get("/api/v1/servicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Corte clásico"))
                .andExpect(jsonPath("$[0].precio").value("7000"));

        verify(servicioService, times(1)).listarTodos();
    }

    @Test
    void testListarServiciosVacio() throws Exception {
        // Arrange
        when(servicioService.listarTodos()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/v1/servicios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerPorIdExitoso() throws Exception {
        // Arrange
        when(servicioService.buscarPorId(1)).thenReturn(servicio);

        // Act & Assert
        mockMvc.perform(get("/api/v1/servicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Corte clásico"))
                .andExpect(jsonPath("$.precio").value("7000"));

        verify(servicioService, times(1)).buscarPorId(1);
    }

    @Test
    void testObtenerPorIdNoEncontrado() throws Exception {
        // Arrange
        when(servicioService.buscarPorId(999))
                .thenThrow(new RuntimeException("Servicio no encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/servicios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearServicio() throws Exception {
        // Arrange
        when(servicioService.crear(any(Servicio.class))).thenReturn(servicio);

        // Act & Assert
        mockMvc.perform(post("/api/v1/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idServicio").value(1));

        verify(servicioService, times(1)).crear(any(Servicio.class));
    }
}
