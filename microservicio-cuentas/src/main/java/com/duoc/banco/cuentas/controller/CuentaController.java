package com.duoc.banco.cuentas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.banco.cuentas.model.Cuenta;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final Map<String, Cuenta> cuentas = new HashMap<>();

    public CuentaController() {
        // Datos de prueba iniciales
        cuentas.put("CTA-1001", new Cuenta("CTA-1001", "1", "Corriente", 1500000.0, "ACTIVA"));
        cuentas.put("CTA-1002", new Cuenta("CTA-1002", "2", "Ahorro", 850000.0, "ACTIVA"));
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable String numeroCuenta) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null) {
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.notFound().build();
    }
}