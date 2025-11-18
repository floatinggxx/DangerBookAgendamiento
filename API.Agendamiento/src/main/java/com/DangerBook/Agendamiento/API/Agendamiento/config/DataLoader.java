package com.DangerBook.Agendamiento.API.Agendamiento.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.DetalleRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.ServicioRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.webclient.UsuariosClient;

import lombok.RequiredArgsConstructor;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AgendaRepository agendaRepository;
    private final DetalleRepository DetalleRepository;
    private final ServicioRepository servicioRepository;
    private final UsuariosClient usuariosClient;

    @Override
    public void run(String... args) {

        if (horarioRepository.count() > 0) return;

        Faker faker = new Faker(new Locale("es-CL"), new Random(321));

        // 1) Crear Días
        if (diaRepository.count() == 0) {
            List<String> nombres = List.of(
                    "Lunes", "Martes", "Miércoles", "Jueves",
                    "Viernes", "Sábado", "Domingo"
            );

            nombres.forEach(n -> diaRepository.save(Dia.builder().dia(n).build()));
        }

        List<Dia> dias = diaRepository.findAll();

        // 2) Crear Bloques
        for (int i = 0; i < 10; i++) {

            LocalDateTime inicio = LocalDateTime.now()
                    .plusDays(faker.number().numberBetween(0, 6))
                    .withHour(faker.number().numberBetween(9, 20))
                    .withMinute(0);

            Bloque bloque = Bloque.builder()
                    .fechaInicio(inicio)
                    .fechaFin(inicio.plusHours(1))
                    .build();

            bloqueRepository.save(bloque);
        }

        List<Bloque> bloques = bloqueRepository.findAll();

        // 3) Crear Horarios
        for (int i = 0; i < 20; i++) {

            Dia dia = dias.get(faker.number().numberBetween(0, dias.size()));
            Bloque bloque = bloques.get(faker.number().numberBetween(0, bloques.size()));

            Horario horario = Horario.builder()
                    .id_dia(dia.getId_dia())
                    .id_bloque(bloque.getId_bloque())
                    .build();

            horarioRepository.save(horario);
        }

        List<Horario> horarios = horarioRepository.findAll();

        // 4) Obtener usuarios reales desde Usuarios API
        List<Map> usuarios = usuariosClient.getAllUsuarios();

        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println(" No hay usuarios disponibles desde Usuarios API.");
            return;
        }

        // 5) Crear Disponibilidades reales
        for (Horario h : horarios) {

            Map<String, Object> usuarioRandom =
                    usuarios.get(faker.number().numberBetween(0, usuarios.size()));

            Long idUsuario = Long.valueOf(usuarioRandom.get("id_usuario").toString());

            Disponibilidad disp = Disponibilidad.builder()
                    .id_horario(h.getId_horario())
                    .id_usuario(idUsuario)
                    .build();

            disponibilidadRepository.save(disp);
        }

        System.out.println("✅ DataLoader de Horarios completado con usuarios reales.");
    }
}
