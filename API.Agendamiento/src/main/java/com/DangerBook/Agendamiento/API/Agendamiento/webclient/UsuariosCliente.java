package com.DangerBook.Agendamiento.API.Agendamiento.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class UsuariosCliente {

    private WebClient webClient;

    public UsuariosCliente(@Value("${usuarios.service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // metodo para traer usuario por id
    public Map<String, Object> obtenerUsuario(Long id) {
        try {
            Map<String, Object> resultado = webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }
}