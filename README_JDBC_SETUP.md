# 🚀 Backend Clínica Jurídica - Configuración JDBC + PostgreSQL

Sistema de gestión de casos para clínicas jurídicas implementado con **Clean Architecture**, **Spring Boot 3** y **JdbcTemplate** (sin JPA/Hibernate).

---

## 📋 Requisitos

- ☕ **Java 21** o superior
- 🐘 **PostgreSQL 12+** corriendo localmente
- 📦 **Maven 3.6+**
- 🐧 **Alpine Linux** (para deployment en Docker)

---

## ⚡ Inicio Rápido

### **1. Configurar PostgreSQL**

#### Opción A: Script Automático (Recomendado)

**Linux/Mac:**
```bash
./setup-db.sh
```

**Windows:**
```batch
setup-db.bat
```

#### Opción B: Manual

```bash
# Conectarse a PostgreSQL
psql -U postgres

# Crear base de datos
CREATE DATABASE clinica_juridica;
\c clinica_juridica

# Ejecutar script de inicialización
\i src/main/resources/init-caso-module.sql
\q
```

### **2. Configurar Variables de Entorno (Opcional)**

Si tus credenciales de PostgreSQL son diferentes a las por defecto:

**Linux/Mac:**
```bash
export DB_URL="jdbc:postgresql://localhost:5432/clinica_juridica"
export DB_USERNAME="postgres"
export DB_PASSWORD="postgres"
```

**Windows:**
```batch
set DB_URL=jdbc:postgresql://localhost:5432/clinica_juridica
set DB_USERNAME=postgres
set DB_PASSWORD=postgres
```

### **3. Ejecutar la Aplicación**

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

---

## 🧪 Probar la API

### **Endpoint de Bienvenida**

```bash
curl http://localhost:8080/
```

**Respuesta:**
```
Welcome to the Clinica Juridica Backend!
```

### **Listar Casos de un Solicitante**

```bash
curl http://localhost:8080/api/casos/solicitante/V-12345678
```

**Respuesta (200 OK):**
```json
[
  {
    "numCaso": "CASO-EJEMPLO-001",
    "sintesis": "Solicitud de asesoría legal para proceso de divorcio contencioso...",
    "estatus": "ABIERTO",
    "fechaRecepcion": "2024-01-15",
    "idSolicitante": "V-12345678",
    "tramite": "Divorcio",
    "cantBeneficiarios": 2,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }
]
```

### **Crear un Nuevo Caso**

```bash
curl -X POST http://localhost:8080/api/casos \
  -H "Content-Type: application/json" \
  -d '{
    "sintesis": "Consulta sobre pensión alimenticia y custodia compartida",
    "idSolicitante": "V-12345678",
    "tramite": "Pensión Alimenticia",
    "cantBeneficiarios": 2,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }'
```

**Respuesta (201 CREATED):**
```json
{
  "numCaso": "CASO-20241125-143022-456",
  "sintesis": "Consulta sobre pensión alimenticia y custodia compartida",
  "estatus": "ABIERTO",
  "fechaRecepcion": "2024-11-25",
  "idSolicitante": "V-12345678",
  "tramite": "Pensión Alimenticia",
  "cantBeneficiarios": 2,
  "idCentro": 1,
  "idAmbitoLegal": 3
}
```

---

## 🏗️ Arquitectura

Este proyecto sigue **Clean Architecture** con separación estricta de capas:

```
src/main/java/clinica_juridica/backend/
├── domain/                    # Capa de Dominio
│   ├── entities/             # Entidades (POJOs)
│   └── repository/           # Interfaces (no usadas en nueva arquitectura)
│
├── application/              # Capa de Aplicación
│   ├── port/output/         # Puertos de salida (interfaces de repositorio)
│   └── usecase/             # Casos de uso (lógica de negocio)
│
└── infrastructure/           # Capa de Infraestructura
    ├── adapter/persistence/ # Adaptadores JDBC (implementaciones)
    ├── config/              # Configuración de Spring
    └── web/controller/      # Controladores REST
```

### Características Técnicas

- ✅ **JdbcTemplate con SQL nativo** (NO usa JPA/Hibernate)
- ✅ **Clean Architecture** estricta
- ✅ **RowMapper personalizado** para mapeo de entidades
- ✅ **HikariCP** como pool de conexiones
- ✅ **Transacciones** gestionadas con Spring
- ✅ **Spring Security** (modo permisivo para desarrollo)

---

## 📁 Archivos de Configuración

### **application.properties**

Configuración principal de la aplicación:
- Conexión a PostgreSQL
- Pool de conexiones HikariCP
- Logging de SQL (para debugging)
- Spring Security básica

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/clinica_juridica}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

### **DatabaseConfig.java**

Configuración de:
- `DataSource` con HikariCP
- `JdbcTemplate` bean
- `TransactionManager`

### **SecurityConfig.java**

Configuración de Spring Security:
- CSRF deshabilitado (para APIs REST)
- Acceso público a todos los endpoints (modo desarrollo)

---

## 🗄️ Base de Datos

### Tablas Principales (Módulo Caso)

1. **CASOS** - Tabla principal de casos
2. **Solicitante** - Personas que solicitan asesoría
3. **Centros** - Centros jurídicos
4. **Ambito_Legal** - Áreas del derecho

### Esquema de CASOS

```sql
"CASOS" (
  "num_caso" VARCHAR(50) PRIMARY KEY,
  "fecha_recepción" DATE,
  "cant_beneficiarios" INTEGER,
  "tramite" VARCHAR(100),
  "estatus" VARCHAR(50),
  "sintesis" TEXT,
  "id_centro" INTEGER FK → Centros,
  "id_ambito_legal" INTEGER FK → Ambito_Legal,
  "id_solicitante" VARCHAR(20) FK → Solicitante
)
```

### Datos de Prueba Incluidos

- **3 Solicitantes** de prueba (V-12345678, V-23456789, V-34567890)
- **5 Ámbitos Legales** (Civil, Penal, Familia, Laboral, Mercantil)
- **3 Centros** jurídicos
- **1 Caso** de ejemplo

---

## 🔧 Configuración Avanzada

### Cambiar Puerto del Servidor

```properties
# application.properties
server.port=9090
```

### Ajustar Pool de Conexiones

```properties
# application.properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=10
```

### Ver Queries SQL en Logs

```properties
# application.properties
logging.level.org.springframework.jdbc.core=DEBUG
```

### Deshabilitar Inicialización Automática de Schema

```properties
# application.properties
spring.sql.init.mode=never
```

---

## 📚 Documentación Adicional

- **[SETUP_DATABASE.md](SETUP_DATABASE.md)** - Guía detallada de configuración de PostgreSQL
- **[CASO_VERTICAL_SLICE.md](CASO_VERTICAL_SLICE.md)** - Documentación del módulo de Caso
- **[schema.sql](src/main/resources/schema.sql)** - Schema completo del sistema
- **[init-caso-module.sql](src/main/resources/init-caso-module.sql)** - Script de inicialización mínimo

---

## 🐛 Troubleshooting

### Error: "Connection refused"

PostgreSQL no está corriendo:

```bash
# Linux
sudo systemctl start postgresql

# Mac
brew services start postgresql
```

### Error: "database does not exist"

```bash
psql -U postgres -c "CREATE DATABASE clinica_juridica;"
```

### Error: "relation 'CASOS' does not exist"

```bash
psql -U postgres -d clinica_juridica -f src/main/resources/init-caso-module.sql
```

### Ver logs detallados

```bash
mvn spring-boot:run --debug
```

---

## 🚀 Deployment

### Construcción para Producción

```bash
mvn clean package -DskipTests
```

El JAR se generará en: `target/backend-0.0.1-SNAPSHOT.jar`

### Ejecutar JAR

```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Docker (Alpine Linux)

```dockerfile
FROM eclipse-temurin:21-jre-alpine
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

## 📊 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Mensaje de bienvenida |
| POST | `/api/casos` | Crear nuevo caso |
| GET | `/api/casos/solicitante/{id}` | Listar casos por solicitante |

---

## 🔐 Seguridad (Nota Importante)

⚠️ **La configuración actual es SOLO para desarrollo**. En producción:

1. Habilitar autenticación (JWT, OAuth2, etc.)
2. Configurar CSRF adecuadamente
3. Implementar autorización basada en roles
4. Usar HTTPS
5. No exponer credenciales en archivos de configuración

---

## 🤝 Contribución

Este proyecto es parte de un sistema académico. Para agregar nuevas funcionalidades:

1. Seguir la arquitectura limpia existente
2. Usar JdbcTemplate (no JPA)
3. Mantener separación de capas
4. Documentar los cambios

---

## 📄 Licencia

Proyecto académico - Universidad [Nombre]

---

## 📞 Soporte

Para problemas o preguntas:
1. Revisar los logs: `mvn spring-boot:run`
2. Consultar la documentación en `/docs`
3. Verificar la configuración de PostgreSQL

---

**Última actualización**: Noviembre 2024  
**Versión**: 1.0.0

