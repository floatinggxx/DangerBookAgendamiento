package com.DangerBook.Agendamiento.API.Agendamiento.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class HorariosCliente {

    private WebClient webClient;

    public HorariosCliente(@Value("${horarios.service.url}") String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public Map<String, Object> obtenerHorario(Integer id) {
        try {
            Map<String, Object> horario = webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            
            return horario;
        } catch (Exception e) {
            // si falla retornamos null
            return null;
        }
    }
}