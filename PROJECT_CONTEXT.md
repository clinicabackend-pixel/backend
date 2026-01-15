# Contexto del Proyecto: Backend Clínica Jurídica

Este documento es la fuente de verdad para el desarrollo del backend. Incluye decisiones de arquitectura, estado actual y una guía de navegación para agentes de IA.

## 1. Stack Tecnológico

- **Framework**: Spring Boot 3.5.9 (Java 21).
- **Persistencia**: Spring Data JDBC / R2DBC.
- **Base de Datos**: PostgreSQL.
- **Autenticación**: JWT + Spring Security.
- **Build Tool**: Maven.
- **Documentación API**: SpringDoc OpenAPI (Swagger).

## 2. Arquitectura General

El proyecto sigue una arquitectura en capas clásica adaptada a Spring Boot:

1.  **Controller Layer** (`controller/`): Maneja las peticiones HTTP, validación de entrada y respuestas.
2.  **Service Layer** (`service/`): Contiene la lógica de negocio. Es donde ocurren las transacciones y reglas.
3.  **Repository Layer** (`repository/`): Interfaces para acceso a datos (Spring Data JDBC/R2DBC).
4.  **Model Layer** (`models/`): Entidades que mapean a tablas de la base de datos.
5.  **DTO Layer** (`dto/`): Objetos para transferir datos hacia/desde el cliente (evita exponer entidades directamente).

## 3. Estado Actual y Funcionalidades Principales

- **Gestión de Usuarios**: Login, registro y roles (Coordinador, Profesor, Estudiante).
- **Casos**: CRUD completo, asignación a estudiantes, supervisión de profesores.
- **Solicitantes**: Gestión de expedientes de personas que piden ayuda.
- **Encuestas**: Lógica para guardar datos socioeconómicos complejos.
- **Reportes**: Generación básica de reportes (Excel/CSV).
- **Notificaciones**: Envío de correos (SendGrid).

## 4. Guía de Navegación para Agentes de IA (Project Structure Breakdown)

Esta sección está diseñada para ayudar a los agentes de IA a localizar rápidamente dónde leer o escribir código según la tarea.

### Estructura de Directorios (`src/main/java/clinica_juridica/backend/`)

| Directorio | Propósito / Qué buscar aquí | Archivos Clave |
|------------|-----------------------------|----------------|
| **`config/`** | Configuración global de Spring y Beans. Si necesitas cambiar CORS, Seguridad o Swagger, busca aquí. | `SecurityConfig.java` (Auth), `OpenApiConfig.java`, `CorsConfig.java` |
| **`controller/`** | **Puntos de entrada de la API**. Busca aquí para ver rutas (`@RequestMapping`), añadir nuevos endpoints o revisar qué datos se reciben. | `CasoController.java`, `UsuarioController.java`, `SolicitanteController.java`, `ReporteController.java` |
| **`dto/`** | **Estructuras de datos JSON**. Si la API debe recibir un campo nuevo o devolver algo extra, modifica los Request/Response aquí. | `request/`, `response/` y `projection/` |
| **`exception/`** | Manejo de errores. Si necesitas añadir una excepción personalizada o cambiar cómo se devuelve un error 400/404/500. | `GlobalExceptionHandler.java` (centraliza errores), `ResourceNotFoundException.java` |
| **`models/`** | **Tablas de BD**. Si cambias el esquema de la base de datos, debes actualizar estas clases. | `Caso.java`, `Usuario.java`, `Solicitante.java` |
| **`repository/`** | **Queries SQL**. Si necesitas una búsqueda compleja por fecha, estatus, etc., añade métodos aquí. Usan nomenclatura de Spring Data o `@Query`. | `CasoRepository.java`, `UsuarioRepository.java` |
| **`security/`** | Lógica interna de JWT y UserDetails. Rara vez se toca a menos que cambie el mecanismo de login. | `JwtAuthenticationFilter.java`, `JwtUtil.java` |
| **`service/`** | **Lógica de Negocio (CEREBRO)**. Aquí está el "cómo" se hace todo. Validaciones complejas, orquestación de repositorios, envío de emails. | `CasoService.java` (Lógica principal), `UsuarioService.java`, `ReporteService.java`, `EmailService.java` |

### Mapa de Funcionalidades Comunes

Si necesitas implementar/corregir:

*   **Autenticación / Login / Tokens** \
    -> `controller/AuthController.java` (Endpoint) \
    -> `service/UsuarioService.java` (Lógica) \
    -> `security/` (Infraestructura JWT)

*   **Crear/Editar un Caso** \
    -> `dto/request/CasoRequest.java` (Revisar campos recibidos) \
    -> `controller/CasoController.java` \
    -> `service/CasoService.java` (Método `createCaso` o `updateCaso`)

*   **Exportar Excel/CSV/PDF** \
    -> `controller/ReporteController.java` \
    -> `service/ReporteService.java` (Lógica de generación de archivos con Apache POI)

*   **Manejo de Errores** \
    -> `exception/GlobalExceptionHandler.java` (Revisar `@ExceptionHandler`)

*   **Envío de Correos** \
    -> `service/EmailService.java`

*   **Datos Maestros (Estados, Municipios, S.Civil)** \
    -> `service/CatalogoService.java` \
    -> `controller/CatalogoController.java` (si existe)

## 5. Reglas de Desarrollo

1.  **DTOs**: Nunca devolver Entidades (`models`) directamente en el Controller. Usar siempre `SomeResponseDTO`.
2.  **Inyección de Dependencias**: Usar inyección por constructor en los servicios y controladores.
3.  **Transacciones**: Usar `@Transactional` en métodos de servicio que modifiquen múltiples tablas.
4.  **Java 21**: Aprovechar características modernas (e.g., `var`, `Records` si aplica para DTOs inmutables).
