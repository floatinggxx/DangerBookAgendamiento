package com.DangerBook.Agendamiento.API.Agendamiento.config;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Agenda;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.AgendaRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.DetalleRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private DetalleRepository detalleRepository;
    
    @Autowired
    private AgendaRepository agendaRepository;

    @Override
    public void run(String... args) throws Exception {
        
        if (servicioRepository.count() == 0) {
            cargarServicios();
        }

        if (detalleRepository.count() == 0) {
            cargarDetalles();
        }
        
        if (agendaRepository.count() == 0) {
            cargarAgendas();
        }

        System.out.println("✅ Datos de Agendamiento cargados");
    }
    
    private void cargarServicios() {
        Servicio s1 = new Servicio();
        s1.setNombre("Corte clásico");
        s1.setDescripcion("Corte tradicional de cabello");
        s1.setFoto("corte.jpg");
        s1.setPrecio("7000");
        servicioRepository.save(s1);

        Servicio s2 = new Servicio();
        s2.setNombre("Fade + Barba");
        s2.setDescripcion("Perfilado y degradado completo");
        s2.setFoto("fade.jpg");
        s2.setPrecio("9000");
        servicioRepository.save(s2);
        
        Servicio s3 = new Servicio();
        s3.setNombre("Barba completa");
        s3.setDescripcion("Arreglo y perfilado de barba");
        s3.setFoto("barba.jpg");
        s3.setPrecio("5000");
        servicioRepository.save(s3);
    }

    private void cargarDetalles() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Detalle d = new Detalle();
            d.setSubtotal(String.valueOf(random.nextInt(7000) + 3000));
            detalleRepository.save(d);
        }
    }
    
    private void cargarAgendas() {
        Random random = new Random();
        List<Servicio> servicios = servicioRepository.findAll();
        List<Detalle> detalles = detalleRepository.findAll();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (int i = 0; i < 8; i++) {
            Agenda agenda = new Agenda();
            agenda.setIdUsuario((long) (random.nextInt(10) + 1));
            agenda.setIdHorario(random.nextInt(10) + 1);
            
            LocalDateTime fecha = LocalDateTime.now().plusDays(random.nextInt(7));
            agenda.setFechaSolicitud(fecha.format(formatter));
            
            Servicio servicio = servicios.get(random.nextInt(servicios.size()));
            Detalle detalle = detalles.get(random.nextInt(detalles.size()));
            
            agenda.setServicio(servicio);
            agenda.setDetalle(detalle);
            
            double total = Double.parseDouble(servicio.getPrecio()) + 
                          Double.parseDouble(detalle.getSubtotal());
            agenda.setTotal(total);
            
            agendaRepository.save(agenda);
        }
    }
}