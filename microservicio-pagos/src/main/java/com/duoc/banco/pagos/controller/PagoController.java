package com.duoc.banco.pagos.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.banco.pagos.model.Pago;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PagoController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/procesar")
    public ResponseEntity<Pago> procesarPago(@RequestBody Pago pago) {
        pago.setIdTransaccion(UUID.randomUUID().toString());
        pago.setEstado("COMPLETADO");

        // Publicar evento en Kafka para procesamiento asíncrono
        String mensaje = String.format("Pago procesado ID: %s | Origen: %s | Destino: %s | Monto: $%.2f",
                pago.getIdTransaccion(), pago.getCuentaOrigen(), pago.getCuentaDestino(), pago.getMonto());

        try {
            kafkaTemplate.send("transacciones-topic", pago.getIdTransaccion(), mensaje);
        } catch (Exception e) {
            System.err.println("Error publicando en Kafka: " + e.getMessage());
        }

        return ResponseEntity.ok(pago);
    }
}