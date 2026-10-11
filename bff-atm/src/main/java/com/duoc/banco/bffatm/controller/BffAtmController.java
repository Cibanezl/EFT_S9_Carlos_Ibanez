package com.duoc.banco.bffatm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/bff/atm")
public class BffAtmController {

    private final RestTemplate restTemplate;

    public BffAtmController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/giro")
    @CircuitBreaker(name = "pagosService", fallbackMethod = "fallbackProcesarGiro")
    public ResponseEntity<Map<String, Object>> procesarGiro(@RequestBody Map<String, Object> solicitudGiro) {
        // Redirigir la operación crítica de giro al microservicio de pagos
        Object resultado = restTemplate.postForObject("http://microservicio-pagos/api/pagos/procesar", solicitudGiro, Object.class);

        Map<String, Object> respuestaAtm = new HashMap<>();
        respuestaAtm.put("canal", "CAJERO_AUTOMATICO_ATM");
        respuestaAtm.put("operacion", "GIRO_EFECTIVO");
        respuestaAtm.put("detalle", resultado);

        return ResponseEntity.ok(respuestaAtm);
    }

    public ResponseEntity<Map<String, Object>> fallbackProcesarGiro(Map<String, Object> solicitudGiro, Throwable t) {
        Map<String, Object> respuestaFallback = new HashMap<>();
        respuestaFallback.put("canal", "CAJERO_AUTOMATICO_ATM");
        respuestaFallback.put("estado", "ERROR_SISTEMA");
        respuestaFallback.put("mensaje", "Cajero fuera de servicio temporalmente para dispensar dinero.");

        return ResponseEntity.status(503).body(respuestaFallback);
    }
}