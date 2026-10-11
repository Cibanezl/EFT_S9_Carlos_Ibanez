package com.duoc.banco.bffmobile.controller;

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
@RequestMapping("/api/bff/mobile")
public class BffMobileController {

    private final RestTemplate restTemplate;

    public BffMobileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/saldo-rapido/{numeroCuenta}")
    @CircuitBreaker(name = "cuentasService", fallbackMethod = "fallbackObtenerSaldo")
    public ResponseEntity<Map<String, Object>> obtenerSaldoRapido(@PathVariable String numeroCuenta) {
        // Consulta ligera al microservicio de cuentas
        Object cuenta = restTemplate.getForObject("http://microservicio-cuentas/api/cuentas/" + numeroCuenta, Object.class);

        Map<String, Object> respuestaMobile = new HashMap<>();
        respuestaMobile.put("canal", "APP_MOVIL_LIGERA");
        respuestaMobile.put("datosCuenta", cuenta);

        return ResponseEntity.ok(respuestaMobile);
    }

    public ResponseEntity<Map<String, Object>> fallbackObtenerSaldo(String numeroCuenta, Throwable t) {
        Map<String, Object> respuestaFallback = new HashMap<>();
        respuestaFallback.put("canal", "APP_MOVIL_LIGERA");
        respuestaFallback.put("estado", "DEGRADADO");
        respuestaFallback.put("mensaje", "Saldo no disponible momentáneamente.");
        respuestaFallback.put("numeroCuenta", numeroCuenta);

        return ResponseEntity.status(503).body(respuestaFallback);
    }
}