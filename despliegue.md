# Guía de Despliegue de Microservicios en la Nube (AWS) - Banco Duoc

Este archivo documenta la estrategia y los pasos técnicos para el despliegue escalable de la solución en la plataforma Amazon Web Services (AWS).

---

## 1. Arquitectura de Despliegue Propuesta

La infraestructura objetivo en AWS utiliza servicios administrados para garantizar alta disponibilidad y seguridad:

- **Amazon Elastic Container Registry (ECR):** Almacenamiento y gestión de las imágenes Docker de los microservicios.
- **Amazon Elastic Container Service (ECS) con AWS Fargate:** Orquestación de contenedores en modalidad *Serverless*.
- **Application Load Balancer (ALB):** Balanceo de carga de tráfico entrante y enrutamiento hacia el API Gateway / BFF.
- **Amazon ElastiCache (Redis):** Gestión de sesiones y caché distribuido.
- **Amazon EFS (Elastic File System):** Mapeo de almacenamiento persistente para la ingesta de archivos CSV en el `batch-service`.

---

## 2. Paso a Paso para el Despliegue en AWS

### Paso 1: Autenticación y Creación de Repositorios en Amazon ECR
```bash
# Autenticarse en el registro privado de AWS
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <AWS_ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com

# Crear repositorios para las imágenes
aws ecr create-repository --repository-name banco-duoc/auth-server
aws ecr create-repository --repository-name banco-duoc/bff-web
aws ecr create-repository --repository-name banco-duoc/batch-service