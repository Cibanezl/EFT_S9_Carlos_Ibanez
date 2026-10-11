# Instrucciones de Ejecución y Pruebas del Sistema - Banco Duoc

Este documento detalla el procedimiento paso a paso para ejecutar y probar cada uno de los componentes de la arquitectura de microservicios.

## 1. Requisitos Previos
- Docker Desktop instalado y en ejecución.
- Java OpenJDK 17 o superior.
- Apache Maven 3.8+.
- Cliente HTTP (Postman).
- Carpeta externa `data` dispuesta en el directorio padre (`../data`).

---

## 2. Puesta en Marcha de la Infraestructura

1. Posicionarse en la carpeta raíz del proyecto `EFT_Carlos_Ibanez`.
2. Compilar los paquetes ejecutables de cada microservicio:
mvn clean package -DskipTests

3. Construir e inicializar la red de contenedores con Docker Compose:

docker-compose up -d --build

4. Confirmar el estado de los contenedores:

docker ps


---

## 3. Matriz de Pruebas y Validación de Endpoints

### A. Servidor de Descubrimiento (Eureka Server)
- **URL:** `http://localhost:8761`
- **Acción:** Abrir en navegador.
- **Resultado Esperado:** Visualizar los microservicios clientes en estado `UP`.

### B. Autenticación Centralizada (Auth-Server)
- **Método:** `POST`
- **URL:** `http://localhost:8090/auth/login`
- **Headers:** `Content-Type: application/json`
- **Body:**

{
"username": "carlos",
"password": "123456"
}

- **Resultado Esperado:** Código HTTP `200 OK` con la devolución del `token` JWT.

### C. Agregación de Datos en Capa BFF (BFF Web)
- **Método:** `GET`
- **URL:** `http://localhost:8084/api/bff/web/resumen-cliente/1`
- **Headers:** `Authorization: Bearer <TOKEN_JWT>`
- **Resultado Esperado:** Código HTTP `200 OK` consolidando la información de perfil y cuentas.

### D. Prueba de Resiliencia y Fallback (Circuit Breaker)
1. Detener el contenedor del dominio clientes:

docker stop microservicio-clientes

2. Reejecutar la petición `GET http://localhost:8084/api/bff/web/resumen-cliente/1`.
3. **Resultado Esperado:** El Circuit Breaker de Resilience4j intercepta el fallo retornando una respuesta controlada de error (`500` / Fallback).
4. Reiniciar el contenedor:

docker start microservicio-clientes


### E. Procesamiento por Lotes (Batch-Service)
- **Método:** `POST`
- **URL:** `http://localhost:8087/api/batch/ejecutar-reporte-diario`
- **Resultado Esperado:** Mensaje `"Job 'ReporteTransaccionesDiarias' ejecutado exitosamente."`.
- **Verificación de Ingesta CSV:** Consultar los registros de ejecución en la consola de Docker:

docker logs --tail 30 batch-service