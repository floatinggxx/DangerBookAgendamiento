package com.DangerBook.Agendamiento.API.Agendamiento.config;

import com.DangerBook.Agendamiento.API.Agendamiento.model.Detalle;
import com.DangerBook.Agendamiento.API.Agendamiento.model.Servicio;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.DetalleRepository;
import com.DangerBook.Agendamiento.API.Agendamiento.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private DetalleRepository detalleRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // cargar servicios si no hay datos
        if (servicioRepository.count() == 0) {
            Servicio s1 = new Servicio();
            s1.setNombre("Corte clasico");
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
            
            System.out.println("Servicios cargados: " + servicioRepository.count());
        }

        // cargar detalles de ejemplo
        if (detalleRepository.count() == 0) {
            Random random = new Random();
            
            for (int i = 0; i < 4; i++) {
                int precio = random.nextInt(7000) + 3000;
                Detalle d = new Detalle();
                d.setSubtotal(String.valueOf(precio));
                detalleRepository.save(d);
            }
            
            System.out.println("Detalles cargados: " + detalleRepository.count());
        }

        System.out.println("=== Datos de prueba cargados correctamente ===");
    }
}