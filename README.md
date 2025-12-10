# Trabajo Final Programación 2025 – Gestión de Eventos

Este repositorio contiene el desarrollo del trabajo de regularización 2025.

## Componentes

- `backend/` – Backend, desarrollado con Spring Boot usando JHipster.
- `proxy/` – Servicio proxy, con acceso a Kafka y Redis de la cátedra.
- `mobile/` – Cliente móvil en Kotlin Multiplatform (KMP).
- `docs/` – Documentación adicional, diagramas, notas, etc.

## Ejecucion del proyecto

1. Backend: ./mvnw -DskipTests=true (por el momento ignoramos los tests)
2. Proxy: ./mvnw spring-boot:run

