package com.duoc.banco.bffweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/bff/web")
public class BffWebController {

    private final RestTemplate restTemplate;

    public BffWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/resumen-cliente/{clienteId}")
    @CircuitBreaker(name = "clientesService", fallbackMethod = "fallbackObtenerResumen")
    public ResponseEntity<Map<String, Object>> obtenerResumenCompleto(@PathVariable String clienteId) {
        // Llamada al microservicio de clientes
        Object cliente = restTemplate.getForObject("http://microservicio-clientes/api/clientes/" + clienteId, Object.class);

        Map<String, Object> respuestaWeb = new HashMap<>();
        respuestaWeb.put("canal", "WEB_PORTAL_COMPLETO");
        respuestaWeb.put("perfilCliente", cliente);

        return ResponseEntity.ok(respuestaWeb);
    }

    // Método Fallback explícito para Resilience4j en caso de falla
    public ResponseEntity<Map<String, Object>> fallbackObtenerResumen(String clienteId, Throwable t) {
        Map<String, Object> respuestaFallback = new HashMap<>();
        respuestaFallback.put("canal", "WEB_PORTAL_COMPLETO");
        respuestaFallback.put("estado", "DEGRADADO");
        respuestaFallback.put("mensaje", "Información de cliente temporalmente no disponible. Intente más tarde.");
        respuestaFallback.put("clienteId", clienteId);

        return ResponseEntity.status(503).body(respuestaFallback);
    }
}