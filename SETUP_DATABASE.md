# 🗄️ Configuración de Base de Datos PostgreSQL

## 📋 Pre-requisitos

- PostgreSQL instalado y corriendo en tu máquina local
- Puerto por defecto: **5432**
- Usuario por defecto: **postgres**

---

## 🚀 Pasos para Configurar la Base de Datos

### **Opción 1: Configuración Rápida (Solo Módulo Caso)**

Esta opción crea solo las tablas necesarias para probar el módulo de Caso.

#### 1. Conectarse a PostgreSQL

```bash
# Linux/Mac
psql -U postgres

# Windows
psql -U postgres -W
```

#### 2. Crear la base de datos

```sql
CREATE DATABASE clinica_juridica;
```

#### 3. Conectarse a la base de datos

```sql
\c clinica_juridica
```

#### 4. Ejecutar el script de inicialización

```sql
\i src/main/resources/init-caso-module.sql
```

O desde la línea de comandos:

```bash
psql -U postgres -d clinica_juridica -f src/main/resources/init-caso-module.sql
```

**¡Listo!** Tu base de datos está configurada con:
- ✅ Tabla `CASOS`
- ✅ Tablas relacionadas (`Solicitante`, `Centros`, `Ambito_Legal`)
- ✅ Datos de prueba (3 solicitantes, 5 ámbitos legales, 3 centros, 1 caso ejemplo)

---

### **Opción 2: Configuración Completa (Todo el Schema)**

Esta opción crea TODAS las tablas del sistema.

#### 1-3. Igual que Opción 1

#### 4. Ejecutar el schema completo

```sql
\i src/main/resources/schema.sql
```

O desde la línea de comandos:

```bash
psql -U postgres -d clinica_juridica -f src/main/resources/schema.sql
```

---

## ⚙️ Configuración de la Aplicación

### **Variables de Entorno (Recomendado)**

Crea un archivo `.env` en la raíz del proyecto:

```bash
# Desde la terminal en /backend
cat > .env << 'EOF'
DB_URL=jdbc:postgresql://localhost:5432/clinica_juridica
DB_USERNAME=postgres
DB_PASSWORD=postgres
EOF
```

### **Archivo application.properties (Ya configurado)**

El archivo `application.properties` ya tiene valores por defecto:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/clinica_juridica}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

Si tus credenciales son diferentes, puedes:

1. **Usar variables de entorno** (recomendado):
   ```bash
   export DB_URL="jdbc:postgresql://localhost:5432/tu_base_datos"
   export DB_USERNAME="tu_usuario"
   export DB_PASSWORD="tu_password"
   ```

2. **Crear archivo `.env`** (recomendado para desarrollo):
   ```
   DB_URL=jdbc:postgresql://localhost:5432/tu_base_datos
   DB_USERNAME=tu_usuario
   DB_PASSWORD=tu_password
   ```

3. **Editar directamente application.properties** (no recomendado):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_password
   ```

---

## 🧪 Verificar la Conexión

### **1. Compilar el proyecto**

```bash
mvn clean compile
```

### **2. Ejecutar la aplicación**

```bash
mvn spring-boot:run
```

### **3. Verificar logs**

Deberías ver en los logs algo como:

```
INFO  HikariDataSource - ClinicaJuridicaHikariPool - Starting...
INFO  HikariDataSource - ClinicaJuridicaHikariPool - Start completed.
INFO  TomcatWebServer  - Tomcat started on port 8080
INFO  BackendApplication - Started BackendApplication in X seconds
```

### **4. Probar el endpoint de salud**

```bash
curl http://localhost:8080/
```

Respuesta esperada:
```
Welcome to the Clinica Juridica Backend!
```

---

## 🔍 Probar el Módulo de Caso

### **1. Listar casos de un solicitante**

```bash
curl http://localhost:8080/api/casos/solicitante/V-12345678
```

### **2. Crear un nuevo caso**

```bash
curl -X POST http://localhost:8080/api/casos \
  -H "Content-Type: application/json" \
  -d '{
    "sintesis": "Consulta sobre pensión alimenticia",
    "idSolicitante": "V-12345678",
    "tramite": "Pensión Alimenticia",
    "cantBeneficiarios": 2,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }'
```

Respuesta esperada (201 CREATED):
```json
{
  "numCaso": "CASO-20241125-143022-456",
  "sintesis": "Consulta sobre pensión alimenticia",
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

## 🐛 Solución de Problemas

### **Error: "Connection refused"**

**Causa**: PostgreSQL no está corriendo o no está en el puerto esperado.

**Solución**:
```bash
# Linux
sudo systemctl status postgresql
sudo systemctl start postgresql

# Mac
brew services list
brew services start postgresql

# Windows
# Verificar en Servicios de Windows
```

### **Error: "password authentication failed"**

**Causa**: Credenciales incorrectas.

**Solución**:
1. Verificar usuario y contraseña de PostgreSQL
2. Actualizar variables de entorno o .env
3. Reiniciar la aplicación

### **Error: "database 'clinica_juridica' does not exist"**

**Causa**: No se creó la base de datos.

**Solución**:
```bash
psql -U postgres -c "CREATE DATABASE clinica_juridica;"
```

### **Error: "relation 'CASOS' does not exist"**

**Causa**: No se ejecutó el script de inicialización.

**Solución**:
```bash
psql -U postgres -d clinica_juridica -f src/main/resources/init-caso-module.sql
```

---

## 📊 Consultas Útiles de PostgreSQL

### **Ver todas las tablas**

```sql
\dt
```

### **Ver estructura de tabla CASOS**

```sql
\d "CASOS"
```

### **Consultar todos los casos**

```sql
SELECT * FROM "CASOS";
```

### **Consultar solicitantes**

```sql
SELECT * FROM "Solicitante";
```

### **Eliminar todos los casos (para pruebas)**

```sql
TRUNCATE TABLE "CASOS" CASCADE;
```

### **Eliminar y recrear la base de datos**

```sql
-- Desde psql como usuario postgres
DROP DATABASE IF EXISTS clinica_juridica;
CREATE DATABASE clinica_juridica;
\c clinica_juridica
\i src/main/resources/init-caso-module.sql
```

---

## 📝 Notas Importantes

1. **Puerto de PostgreSQL**: Por defecto es 5432. Si cambiaste el puerto, actualiza la URL de conexión.

2. **Nombres entre comillas**: PostgreSQL requiere comillas dobles para nombres en mayúsculas/mixtos.

3. **Logging SQL**: Configurado en `application.properties` para ver las queries en los logs:
   ```properties
   logging.level.org.springframework.jdbc.core=DEBUG
   ```

4. **Pool de Conexiones**: HikariCP está configurado con:
   - Máximo: 10 conexiones
   - Mínimo: 5 conexiones
   - Timeout: 20 segundos

5. **Spring Security**: Configurado en modo permisivo para desarrollo. En producción, implementar autenticación adecuada.

---

## 🎯 Próximos Pasos

Una vez que la base de datos esté configurada y la aplicación corriendo:

1. ✅ Probar los endpoints REST con Postman o curl
2. ✅ Revisar los logs para ver las queries SQL ejecutadas
3. ✅ Explorar la documentación en `CASO_VERTICAL_SLICE.md`
4. ✅ Agregar más casos de uso según necesites

---

**¿Necesitas ayuda?** Revisa los logs de la aplicación en la consola al ejecutar `mvn spring-boot:run`.

