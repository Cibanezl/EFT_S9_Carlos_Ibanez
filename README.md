# Banco Duoc - Arquitectura de Microservicios (EFT)

Este repositorio contiene la solución técnica para el examen transversal, basada en una arquitectura distribuida de microservicios con Spring Boot, Spring Cloud y Docker.

---

## 🏗️ Estructura de la Arquitectura

El ecosistema está compuesto por 10 contenedores orquestados mediante Docker Compose:

1. **`eureka-server`** (Puerto 8761): Servidor de descubrimiento de servicios.
2. **`config-server`**: Servidor de configuración centralizada.
3. **`auth-server`** (Puerto 8090): Servidor de autenticación y emisión de tokens JWT.
4. **`bff-web`** (Puerto 8084): Backend for Frontend para la plataforma Web.
5. **`bff-mobile`** (Puerto 8085) y **`bff-atm`** (Puerto 8086): Capas adaptadoras adicionales.
6. **`microservicio-clientes`** (Puerto 8081): Gestión de datos de clientes.
7. **`microservicio-cuentas`** (Puerto 8082): Gestión de productos financieros.
8. **`microservicio-pagos`** (Puerto 8083): Gestión de transacciones.
9. **`batch-service`** (Puerto 8087): Procesamiento asíncrono e ingesta masiva de archivos CSV.

---

## 📁 Requisitos y Configuración de Archivos CSV

Para la ejecución del proceso Batch, el entorno requiere la carpeta externa `data` ubicada en el directorio superior (`../data`), organizada de la siguiente manera:

```text
Examen_transversal/
├── data/
│   ├── semana_1/
│   │   ├── estados_financieros_anuales.csv
│   │   ├── intereses_trimestrales.csv
│   │   └── movimientos_financieros_diarios.csv
│   ├── semana_2/
│   └── semana_3/
└── EFT_Carlos_Ibanez/  (Repositorio actual)

El servicio batch-service mapea este directorio mediante un volumen en docker-compose.yml:

YAML
    volumes:
      - ../data:/data
🚀 Instrucciones de Despliegue
Clonar el repositorio.

Asegurar que la carpeta data con los archivos .csv se encuentre en el directorio padre (../data).

Compilar los paquetes JAR ejecutando Maven:

Bash
mvn clean package -DskipTests
Construir y levantar la infraestructura completa en Docker:

Bash
docker-compose up -d --build
Verificar el registro ingresando a Eureka Server en http://localhost:8761 (deben aparecer los servicios en estado UP).

🧪 Endpoints de Prueba (Postman)
Autenticación (JWT):

POST http://localhost:8090/auth/login

Body (JSON): {"username": "carlos", "password": "123456"}

Consulta BFF Web (Agregación):

GET http://localhost:8084/api/bff/web/resumen-cliente/1

Header: Authorization: Bearer <TOKEN_JWT>

Ejecución Batch (Ingesta CSV):

POST http://localhost:8087/api/batch/ejecutar-reporte-diario


