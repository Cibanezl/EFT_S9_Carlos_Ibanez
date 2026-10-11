package com.duoc.banco.batch.config;

import com.duoc.banco.batch.model.Transaccion;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BatchConfig {

    // Job 1: Reporte de Transacciones Diarias
    @Bean
    public Job reporteTransaccionesJob(JobRepository jobRepository, Step stepTransacciones) {
        return new JobBuilder("reporteTransaccionesJob", jobRepository)
                .start(stepTransacciones)
                .build();
    }

    @Bean
    public Step stepTransacciones(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        List<Transaccion> datosLegacy = Arrays.asList(
                new Transaccion("TX-001", "CTA-1001", 50000.0, "DEPOSITO", "OK"),
                new Transaccion("TX-002", "CTA-1002", 1200000.0, "TRANSFERENCIA", "ANOMALA"),
                new Transaccion("TX-003", "CTA-1001", 15000.0, "GIRO", "OK")
        );

        return new StepBuilder("stepTransacciones", jobRepository)
                .<Transaccion, Transaccion>chunk(10, transactionManager)
                .reader(new ListItemReader<>(datosLegacy))
                .processor(transaccionProcessor())
                .writer(transaccionWriter())
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                .build();
    }

    @Bean
    public ItemProcessor<Transaccion, Transaccion> transaccionProcessor() {
        return item -> {
            // Regla de detección de anomalías
            if (item.getMonto() > 1000000.0) {
                item.setEstado("REVISION_ALERTA_SEGURIDAD");
            } else {
                item.setEstado("PROCESADO_OK");
            }
            return item;
        };
    }

    @Bean
    public ItemWriter<Transaccion> transaccionWriter() {
        return items -> {
            for (Transaccion t : items) {
                System.out.println("[SPRING BATCH OUTPUT] Transacción Procesada -> ID: " + t.getId() + " | Estado: " + t.getEstado());
            }
        };
    }
}