package com.duoc.banco.clientes.controller;

import com.duoc.banco.clientes.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final Map<String, Cliente> clientes = new HashMap<>();

    public ClienteController() {
        // Datos de prueba iniciales
        clientes.put("1", new Cliente("1", "12345678-9", "Carlos Ibáñez", "carlos@bancoxyz.cl", "VIP"));
        clientes.put("2", new Cliente("2", "98765432-1", "María González", "maria@bancoxyz.cl", "Estándar"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String id) {
        Cliente cliente = clientes.get(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }
}