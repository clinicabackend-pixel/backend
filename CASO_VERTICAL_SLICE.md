# Vertical Slice: Módulo CASO

## 📋 Resumen

Este documento describe la implementación completa del **Vertical Slice** para la entidad `CASO` siguiendo los principios de **Clean Architecture** y usando **JdbcTemplate con SQL nativo** (sin JPA/Hibernate).

---

## 🏗️ Arquitectura Implementada

### **1. DOMAIN (Dominio)**
Ubicación: `clinica_juridica.backend.domain`

#### Entidad: `Caso.java`
- **Paquete**: `clinica_juridica.backend.domain.entities`
- **Descripción**: POJO que representa la entidad de dominio Caso
- **Atributos principales**:
  - `numCaso` (String): Identificador único del caso
  - `sintesis` (String): Descripción del caso
  - `estatus` (String): Estado actual (ej. "ABIERTO", "CERRADO")
  - `fechaRecepcion` (LocalDate): Fecha de apertura del caso
  - `idSolicitante` (String): Referencia al solicitante
  - `tramite` (String): Tipo de trámite
  - `cantBeneficiarios` (Integer): Cantidad de beneficiarios
  - `idCentro` (Integer): Centro asociado
  - `idAmbitoLegal` (Integer): Ámbito legal del caso

---

### **2. APPLICATION (Casos de Uso)**
Ubicación: `clinica_juridica.backend.application`

#### Puerto de Salida: `CasoRepository.java`
- **Paquete**: `clinica_juridica.backend.application.port.output`
- **Tipo**: Interfaz (Puerto de salida)
- **Métodos**:
  - `Caso guardar(Caso caso)`: Persiste un nuevo caso
  - `Optional<Caso> buscarPorNumCaso(String numCaso)`: Busca por ID
  - `List<Caso> buscarPorSolicitante(String idSolicitante)`: Lista casos de un solicitante
  - `List<Caso> buscarTodos()`: Obtiene todos los casos

#### Caso de Uso 1: `RegistrarCaso.java`
- **Paquete**: `clinica_juridica.backend.application.usecase`
- **Responsabilidad**: Validar y crear nuevos casos
- **Reglas de negocio aplicadas**:
  - Validación de síntesis obligatoria
  - Validación de solicitante obligatorio
  - Estatus por defecto: `"ABIERTO"`
  - Fecha de recepción: `LocalDate.now()`
- **Comando**: `ComandoCrearCaso` (record)

#### Caso de Uso 2: `ObtenerCasosPorSolicitante.java`
- **Paquete**: `clinica_juridica.backend.application.usecase`
- **Responsabilidad**: Consultar todos los casos de un solicitante
- **Validaciones**: Verifica que el ID del solicitante no sea nulo/vacío

---

### **3. INFRASTRUCTURE (Infraestructura)**
Ubicación: `clinica_juridica.backend.infrastructure`

#### Adaptador de Persistencia: `CasoJdbcAdapter.java`
- **Paquete**: `clinica_juridica.backend.infrastructure.adapter.persistence`
- **Tipo**: Implementación de `CasoRepository` usando `JdbcTemplate`
- **Tecnología**: SQL nativo + JdbcTemplate (NO usa JPA/Hibernate)

**Características técnicas**:
- **RowMapper personalizado**: `CasoRowMapper` para mapear `ResultSet` → `Caso`
- **Generación de IDs**: Método `generarNumCaso()` con formato `CASO-YYYYMMDD-HHMMSS-millis`
- **SQL nativo optimizado**:
  ```sql
  -- INSERT
  INSERT INTO "CASOS" ("num_caso", "fecha_recepción", "cant_beneficiarios", ...)
  VALUES (?, ?, ?, ...)
  
  -- SELECT por solicitante (ordenado por fecha DESC)
  SELECT "num_caso", "fecha_recepción", "cant_beneficiarios", ...
  FROM "CASOS"
  WHERE "id_solicitante" = ?
  ORDER BY "fecha_recepción" DESC
  ```
- **Manejo de excepciones**: Captura `DataAccessException` y lanza excepciones de negocio

#### Controlador REST: `CasoController.java`
- **Paquete**: `clinica_juridica.backend.infrastructure.web.controller`
- **Base path**: `/api/casos`
- **Endpoints**:

| Método | Endpoint | Descripción | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| POST | `/api/casos` | Crear nuevo caso | `CrearCasoRequest` | `Caso` (201 CREATED) |
| GET | `/api/casos/solicitante/{id}` | Listar casos de un solicitante | - | `List<Caso>` (200 OK) |

**DTO**: `CrearCasoRequest` (record) con campos:
- `sintesis` (String)
- `idSolicitante` (String)
- `tramite` (String)
- `cantBeneficiarios` (Integer)
- `idCentro` (Integer)
- `idAmbitoLegal` (Integer)

#### Configuración de Beans: `UseCaseConfig.java`
- **Paquete**: `clinica_juridica.backend.infrastructure.config`
- **Tipo**: `@Configuration`
- **Beans definidos**:
  - `RegistrarCaso`: Inyecta `CasoRepository`
  - `ObtenerCasosPorSolicitante`: Inyecta `CasoRepository`

---

## 🔄 Flujo de Datos (Ejemplo: Crear Caso)

```
1. Cliente HTTP → POST /api/casos
   Body: {"sintesis": "Caso de divorcio", "idSolicitante": "V-12345678", ...}

2. CasoController (Infrastructure)
   ↓ Recibe request y crea ComandoCrearCaso
   
3. RegistrarCaso (Application - Use Case)
   ↓ Valida reglas de negocio
   ↓ Aplica estatus="ABIERTO", fecha=LocalDate.now()
   ↓ Llama al puerto de salida: casoRepository.guardar(caso)
   
4. CasoJdbcAdapter (Infrastructure - Persistence)
   ↓ Genera numCaso único
   ↓ Ejecuta INSERT con JdbcTemplate
   ↓ SQL nativo: INSERT INTO "CASOS" (...)
   
5. PostgreSQL (Base de Datos)
   ↓ Persiste el registro
   
6. Retorno de Caso con numCaso ← CasoJdbcAdapter ← RegistrarCaso ← CasoController
   Response: {"numCaso": "CASO-20231125-143022-123", "estatus": "ABIERTO", ...}
```

---

## 📦 Estructura de Archivos Generados

```
src/main/java/clinica_juridica/backend/
├── domain/
│   └── entities/
│       └── Caso.java (ya existía, sin cambios)
│
├── application/
│   ├── port/output/
│   │   └── CasoRepository.java (actualizado)
│   └── usecase/
│       ├── RegistrarCaso.java (nuevo)
│       └── ObtenerCasosPorSolicitante.java (nuevo)
│
└── infrastructure/
    ├── adapter/persistence/
    │   └── CasoJdbcAdapter.java (actualizado)
    ├── config/
    │   └── UseCaseConfig.java (actualizado)
    └── web/controller/
        └── CasoController.java (actualizado)
```

---

## 🧪 Ejemplos de Uso de la API

### **POST /api/casos** (Crear Caso)

**Request**:
```json
POST http://localhost:8080/api/casos
Content-Type: application/json

{
  "sintesis": "Solicitud de asesoría legal por divorcio contencioso",
  "idSolicitante": "V-12345678",
  "tramite": "Divorcio",
  "cantBeneficiarios": 2,
  "idCentro": 1,
  "idAmbitoLegal": 3
}
```

**Response** (201 CREATED):
```json
{
  "numCaso": "CASO-20231125-143022-456",
  "sintesis": "Solicitud de asesoría legal por divorcio contencioso",
  "estatus": "ABIERTO",
  "fechaRecepcion": "2023-11-25",
  "idSolicitante": "V-12345678",
  "tramite": "Divorcio",
  "cantBeneficiarios": 2,
  "idCentro": 1,
  "idAmbitoLegal": 3
}
```

### **GET /api/casos/solicitante/{id}** (Listar Casos)

**Request**:
```
GET http://localhost:8080/api/casos/solicitante/V-12345678
```

**Response** (200 OK):
```json
[
  {
    "numCaso": "CASO-20231125-143022-456",
    "sintesis": "Solicitud de asesoría legal por divorcio contencioso",
    "estatus": "ABIERTO",
    "fechaRecepcion": "2023-11-25",
    "idSolicitante": "V-12345678",
    ...
  },
  {
    "numCaso": "CASO-20231120-101530-123",
    "sintesis": "Consulta sobre pensión alimenticia",
    "estatus": "CERRADO",
    "fechaRecepcion": "2023-11-20",
    "idSolicitante": "V-12345678",
    ...
  }
]
```

---

## ✅ Reglas de Negocio Implementadas

1. ✅ **Estatus por defecto**: Todo caso nuevo se crea con estatus `"ABIERTO"`
2. ✅ **Fecha automática**: La fecha de recepción es `LocalDate.now()` (fecha actual)
3. ✅ **Validaciones**:
   - Síntesis obligatoria (no puede estar vacía)
   - ID de solicitante obligatorio
4. ✅ **Generación de ID único**: Formato `CASO-YYYYMMDD-HHMMSS-millis`
5. ✅ **Ordenamiento**: Los casos se listan por fecha de recepción descendente (más recientes primero)

---

## 🔧 Tecnologías Utilizadas

- ☕ **Java 21** (features: Records, Text Blocks)
- 🍃 **Spring Boot 3.x**
- 🗄️ **PostgreSQL** (con esquema que usa nombres entre comillas)
- 🔌 **JdbcTemplate** (acceso a datos sin JPA/Hibernate)
- 📦 **Maven** (gestión de dependencias)
- 🐳 **Alpine Linux** (target deployment)

---

## 🎯 Principios de Clean Architecture Aplicados

✅ **Dependency Rule**: Las dependencias apuntan hacia adentro (infraestructura → application → domain)  
✅ **Separation of Concerns**: Cada capa tiene responsabilidades bien definidas  
✅ **Dependency Inversion**: Los casos de uso dependen de interfaces (puertos), no de implementaciones  
✅ **Framework Independence**: La lógica de negocio no depende de Spring  
✅ **Testability**: Los casos de uso se pueden probar sin infraestructura  

---

## 📝 Notas Técnicas

1. **PostgreSQL y nombres con comillas**: La base de datos usa nombres de tabla/columnas entre comillas dobles (ej. `"CASOS"`, `"num_caso"`). El SQL nativo respeta este formato.

2. **RowMapper**: Se implementó un `CasoRowMapper` interno que maneja correctamente:
   - Conversión `java.sql.Date` → `LocalDate`
   - Manejo de valores NULL (ej. `idCentro`, `idAmbitoLegal`)
   - Mapeo de columnas con nombres especiales (ej. `"fecha_recepción"`)

3. **Records de Java**: Se usan `record` para DTOs inmutables (`ComandoCrearCaso`, `CrearCasoRequest`)

4. **Text Blocks**: El SQL usa sintaxis de Text Blocks (`"""..."""`) para mejor legibilidad

5. **Manejo de errores**: El controlador captura excepciones y retorna códigos HTTP apropiados:
   - 201 CREATED: Caso creado exitosamente
   - 400 BAD REQUEST: Validación fallida
   - 500 INTERNAL SERVER ERROR: Error de BD

---

## 🚀 Próximos Pasos (Opcional)

- [ ] Agregar endpoint `PUT /api/casos/{numCaso}` para actualizar casos
- [ ] Implementar `CerrarCaso` use case (cambiar estatus a "CERRADO")
- [ ] Agregar paginación a `GET /api/casos/solicitante/{id}`
- [ ] Implementar tests unitarios para casos de uso
- [ ] Implementar tests de integración con Testcontainers

---

**Autor**: Arquitecto de Software Senior  
**Fecha**: Noviembre 2024  
**Versión**: 1.0

