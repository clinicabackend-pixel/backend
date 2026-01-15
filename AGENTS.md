# Agentes del Backend (Backend Agents)

Este documento define la implementación técnica de los actores y roles en el backend (`clinica_juridica.backend`).

## 1. Mapeo de Roles de Usuario

El sistema utiliza la entidad `Usuario` para representar a todos los actores internos. La diferenciación se realiza a través del campo `tipo` y `status`.

### Entidades y Enums Relacionados
- **Entidad Principal**: `clinica_juridica.backend.models.Usuario`
- **Tabla Base de Datos**: `usuarios`
- **Enum de Roles**: `clinica_juridica.backend.models.enums.TipoUsuario`

### Definición de Roles

| Actor Lógico | Enum `TipoUsuario` | Rol Spring Security | Descripción Técnica |
|--------------|--------------------|---------------------|---------------------|
| **Coordinador**| `COORDINADOR` | `ROLE_COORDINADOR` | Administrador con acceso total a endpoints protegidos. |
| **Profesor** | `PROFESOR` | `ROLE_PROFESOR` | Acceso a endpoints de supervisión y gestión de casos asignados. |
| **Estudiante** | `ESTUDIANTE` | `ROLE_ESTUDIANTE` | Acceso limitado a operaciones operativas y casos propios ("Mis Casos"). |

> **Nota:** Spring Security añade automáticamente el prefijo `ROLE_` al nombre del rol cuando se utilizan anotaciones como `@PreAuthorize`.

## 2. Implementación de Seguridad

### Autenticación y Autorización
- **Mecanismo**: JWT (JSON Web Tokens).
- **Filtro**: `JwtAuthenticationFilter` intercepta cada petición para validar el token y extraer el usuario/rol.
- **Configuración**: `SecurityConfig` define las reglas de acceso HTTP (rutas públicas vs privadas) y habilita la seguridad a nivel de método (`@EnableMethodSecurity`).

### Control de Acceso a Endpoints
El control de acceso se realiza principalmente a nivel de método en los controladores (`Controller`).

**Ejemplo de Autorización:**
```java
// Solo el coordinador puede eliminar usuarios
@PreAuthorize("hasRole('COORDINADOR')")
@DeleteMapping("/{id}")
public Mono<Void> deleteUsuario(@PathVariable String id) { ... }

// Profesores y Coordinadores pueden ver detalles
@PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR')")
@GetMapping("/{id}")
public Mono<CasoResponse> getCaso(@PathVariable Long id) { ... }
```

## 3. Relaciones de Entidad (Modelado de Datos)

Cómo se relacionan los agentes con los datos del negocio (`Caso`):

- **Estudiante -> Caso**: Relación M:N (o 1:N) gestionada (ej. `CasoAsignado`). El estudiante es el "operador" del caso.
- **Profesor -> Caso**: Relación de supervisión (`CasoSupervisado`). El profesor "audita" el caso.
- **Coordinador -> Caso**: No tiene relación directa obligatoria en la BD, pero tiene permiso implícito para ver/editar todos.

## 4. Agentes Externos (No Usuarios)

Estos actores no tienen credenciales de acceso (`login`) pero son entidades fundamentales en el modelo de datos.

- **Solicitante**: Entidad `Solicitante`. Vinculado a uno o más `Casos`.
- **Beneficiario**: Entidad `BeneficiarioCaso` (o similar). Relacionado a un `Caso` específico.
