package com.DangerBook.Agendamiento.API.Agendamiento.Service;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.service.AgendaService;
import com.DangerBook.Agendamiento.API.Agendamiento.webclient.HorariosCliente;
import com.DangerBook.Agendamiento.API.Agendamiento.webclient.UsuariosCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private UsuariosCliente usuariosCliente;

    @Mock
    private HorariosCliente horariosCliente;

    @InjectMocks
    private AgendaService agendaService;

    private Agenda agenda;
    private Detalle detalle;
    private Servicio servicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear datos de prueba
        detalle = new Detalle();
        detalle.setIdDetalle(1);
        detalle.setSubtotal("5000");

        servicio = new Servicio();
        servicio.setIdServicio(1);
        servicio.setNombre("Corte clásico");
        servicio.setDescripcion("Corte tradicional");
        servicio.setPrecio("7000");
        servicio.setFoto("corte.jpg");

        agenda = new Agenda();
        agenda.setIdAgenda(1);
        agenda.setFechaSolicitud("2024-11-19");
        agenda.setTotal(12000.0);
        agenda.setIdUsuario(1L);
        agenda.setIdHorario(1);
        agenda.setDetalle(detalle);
        agenda.setServicio(servicio);
    }

    @Test
    void testObtenerTodas() {
        // Arrange
        List<Agenda> agendas = List.of(agenda);
        when(agendaRepository.findAll()).thenReturn(agendas);

        // Act
        List<Agenda> resultado = agendaService.obtenerTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("2024-11-19", resultado.get(0).getFechaSolicitud());
        verify(agendaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // Arrange
        when(agendaRepository.findById(1)).thenReturn(Optional.of(agenda));

        // Act
        Agenda resultado = agendaService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAgenda());
        assertEquals(12000.0, resultado.getTotal());
        verify(agendaRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // Arrange
        when(agendaRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> agendaService.buscarPorId(999));
        verify(agendaRepository, times(1)).findById(999);
    }

    @Test
    void testGuardarAgendaExitoso() {
        // Arrange
        Map<String, Object> usuarioMock = Map.of("id", 1L, "nombre", "Juan");
        Map<String, Object> horarioMock = Map.of("id", 1, "hora", "10:00");

        when(usuariosCliente.obtenerUsuario(1L)).thenReturn(usuarioMock);
        when(horariosCliente.obtenerHorario(1)).thenReturn(horarioMock);
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agenda);

        // Act
        Agenda resultado = agendaService.guardar(agenda);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAgenda());
        verify(usuariosCliente, times(1)).obtenerUsuario(1L);
        verify(horariosCliente, times(1)).obtenerHorario(1);
        verify(agendaRepository, times(1)).save(agenda);
    }

    @Test
    void testGuardarAgendaUsuarioNoExiste() {
        // Arrange
        when(usuariosCliente.obtenerUsuario(1L)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> agendaService.guardar(agenda));
        assertEquals("El usuario no existe", exception.getMessage());
        verify(agendaRepository, never()).save(any());
    }

    @Test
    void testGuardarAgendaHorarioNoExiste() {
        // Arrange
        Map<String, Object> usuarioMock = Map.of("id", 1L);
        when(usuariosCliente.obtenerUsuario(1L)).thenReturn(usuarioMock);
        when(horariosCliente.obtenerHorario(1)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> agendaService.guardar(agenda));
        assertEquals("El horario no existe", exception.getMessage());
        verify(agendaRepository, never()).save(any());
    }

    @Test
    void testEliminarAgenda() {
        // Act
        agendaService.eliminar(1);

        // Assert
        verify(agendaRepository, times(1)).deleteById(1);
    }
}