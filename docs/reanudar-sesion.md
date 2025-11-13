### Descripción
Se implementa la persistencia y reanudación del flujo de usuario:

- Servicio `FlujoSesionService` para guardar, recuperar y limpiar el estado de sesión por `userId`.
- Uso de la entidad `SesionUsuario` asociada a `User`.
- Controlador REST `FlujoSesionResource` con endpoints:
  - POST `/api/sesion/guardar`
  - GET `/api/sesion/recuperar/{userId}`
  - DELETE `/api/sesion/limpiar/{userId}`

### Pruebas realizadas
- Autenticación vía `/api/authenticate` con usuario `admin`.
- GET `/api/eventos` con JWT → OK.
- POST `/api/sesion/guardar` → persiste estado.
- GET `/api/sesion/recuperar/1` → retorna estado guardado.

### Issue asociado
Closes #<número del issue de reanudación de flujo>

### Milestone
Backend JHipster (autenticación + modelo de dominio)

