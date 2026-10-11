package com.duoc.banco.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batch")
public class BatchJobController {

    private final JobLauncher jobLauncher;
    private final Job reporteTransaccionesJob;

    public BatchJobController(JobLauncher jobLauncher, Job reporteTransaccionesJob) {
        this.jobLauncher = jobLauncher;
        this.reporteTransaccionesJob = reporteTransaccionesJob;
    }

    @PostMapping("/ejecutar-reporte-diario")
    public ResponseEntity<String> ejecutarReporteDiario() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(reporteTransaccionesJob, params);
            return ResponseEntity.ok("Job 'ReporteTransaccionesDiarias' ejecutado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error ejecutando Job Batch: " + e.getMessage());
        }
    }
}