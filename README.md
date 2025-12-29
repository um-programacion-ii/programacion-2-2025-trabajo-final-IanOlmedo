# Trabajo Final – Programación II

## Datos del estudiante

Nombre y apellido: **Ian Olmedo**  
Legajo: **62199**  
Correo institucional: **i.olmedo@alumno.um.edu.ar** 
Docentes: **Daniel Quinteros – Fernando Villarreal**

---

## Información académica

Asignatura: Programación II  
Carrera: Ingeniería en Informática  
Institución: Universidad de Mendoza  
Año de cursado: 2025  

---

## Descripción general del proyecto

El presente trabajo final consiste en el desarrollo de un sistema distribuido orientado a la gestión de eventos y ventas, con autenticación de usuarios y comunicación entre servicios desacoplados.

El sistema simula un escenario real de arquitectura moderna, incorporando persistencia de datos, mensajería asincrónica y mecanismos de almacenamiento en memoria para optimizar el rendimiento y la escalabilidad.

---

## Arquitectura del sistema

La solución se encuentra dividida en tres componentes principales, cada uno con responsabilidades bien definidas:

### Backend
Servicio principal encargado de la lógica de negocio. Administra usuarios, eventos y ventas, expone APIs REST y coordina la comunicación con el proxy y los servicios externos provistos por la cátedra.

### Proxy
Servicio intermedio que centraliza el acceso a Kafka y Redis. Su función es actuar como único punto de contacto con estos sistemas, desacoplando al backend de la infraestructura de mensajería y cacheo.

### Cliente móvil
Aplicación multiplataforma desarrollada con Kotlin Multiplatform (KMP). Permite a los usuarios autenticarse, consultar eventos disponibles y realizar operaciones de compra, consumiendo exclusivamente las APIs del backend.

---

## Uso de tecnologías de infraestructura

Kafka se emplea como sistema de mensajería asincrónica para la comunicación basada en eventos entre los distintos componentes del sistema, favoreciendo el desacoplamiento y la escalabilidad.

Redis se utiliza como almacenamiento en memoria para manejar información temporal, como el estado y bloqueo de asientos, reduciendo la carga sobre la base de datos y mejorando los tiempos de respuesta.

---

## Repositorio del proyecto

Repositorio GitHub:  
`git@github.com:um-programacion-ii/programacion-2-2025-trabajo-final-IanOlmedo.git`

---

## Instrucciones de ejecución

### 1. Clonar el repositorio
```bash
git clone git@github.com:um-programacion-ii/programacion-2-2025-trabajo-final-IanOlmedo.git

### 2. Ejecutar el Backend
cd Backend/ 

### Verificar si los contenedores de Kafka y Zookeeper están activos
sudo docker ps -a

### En caso de que no estén en ejecución, iniciarlos:
sudo docker start kafka zookeeper

### Iniciar el backend
./mvnw

### 3. Ejecutar el Proxy (Abrir en una terminal nueva)
cd Proxy/
mvn spring-boot:run

