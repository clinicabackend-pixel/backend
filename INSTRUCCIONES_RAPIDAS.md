# ⚡ INSTRUCCIONES RÁPIDAS - Clínica Jurídica Backend

## 🎯 Para Empezar en 3 Pasos

### **Paso 1: Configurar PostgreSQL** 

Asegúrate de que PostgreSQL esté corriendo y ejecuta:

```bash
# Linux/Mac
./setup-db.sh

# Windows
setup-db.bat

# O manualmente con psql
psql -U postgres
CREATE DATABASE clinica_juridica;
\c clinica_juridica
\i src/main/resources/init-caso-module.sql
\q
```

### **Paso 2: Ejecutar la Aplicación**

```bash
mvn spring-boot:run
```

### **Paso 3: Probar**

```bash
# Ver mensaje de bienvenida
curl http://localhost:8080/

# Listar casos
curl http://localhost:8080/api/casos/solicitante/V-12345678

# Crear un caso
curl -X POST http://localhost:8080/api/casos \
  -H "Content-Type: application/json" \
  -d '{
    "sintesis": "Consulta legal sobre divorcio",
    "idSolicitante": "V-12345678",
    "tramite": "Divorcio",
    "cantBeneficiarios": 2,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }'
```

---

## 🔧 Configuración de PostgreSQL Local

**Por defecto, la aplicación espera:**
- **Host**: localhost
- **Puerto**: 5432
- **Base de datos**: clinica_juridica
- **Usuario**: postgres
- **Contraseña**: postgres

### Si tus credenciales son diferentes:

**Opción 1 - Variables de entorno (recomendado):**

```bash
# Linux/Mac
export DB_URL="jdbc:postgresql://localhost:5432/clinica_juridica"
export DB_USERNAME="tu_usuario"
export DB_PASSWORD="tu_password"

# Windows
set DB_URL=jdbc:postgresql://localhost:5432/clinica_juridica
set DB_USERNAME=tu_usuario
set DB_PASSWORD=tu_password
```

**Opción 2 - Editar application.properties:**

Cambia estos valores en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

---

## 📋 Endpoints Disponibles

### **GET /** - Mensaje de bienvenida
```bash
curl http://localhost:8080/
```

### **GET /api/casos/solicitante/{id}** - Listar casos de un solicitante
```bash
curl http://localhost:8080/api/casos/solicitante/V-12345678
```

### **POST /api/casos** - Crear nuevo caso
```bash
curl -X POST http://localhost:8080/api/casos \
  -H "Content-Type: application/json" \
  -d '{
    "sintesis": "Descripción del caso",
    "idSolicitante": "V-12345678",
    "tramite": "Tipo de trámite",
    "cantBeneficiarios": 1,
    "idCentro": 1,
    "idAmbitoLegal": 1
  }'
```

---

## 🐛 Problemas Comunes

### **Error: Connection refused**
PostgreSQL no está corriendo:
```bash
# Linux
sudo systemctl start postgresql

# Mac
brew services start postgresql

# Windows - Abrir Servicios y buscar PostgreSQL
```

### **Error: database "clinica_juridica" does not exist**
```bash
psql -U postgres -c "CREATE DATABASE clinica_juridica;"
psql -U postgres -d clinica_juridica -f src/main/resources/init-caso-module.sql
```

### **Error: password authentication failed**
Actualiza las credenciales (ver sección de configuración arriba)

### **Ver logs detallados**
```bash
mvn spring-boot:run --debug
```

---

## 📚 Documentación Completa

- **README_JDBC_SETUP.md** - Guía completa de configuración
- **SETUP_DATABASE.md** - Instrucciones detalladas de PostgreSQL
- **CASO_VERTICAL_SLICE.md** - Documentación técnica del módulo

---

## 🎓 Datos de Prueba Incluidos

### Solicitantes:
- V-12345678 (Juan Pérez García)
- V-23456789 (María González López)  
- V-34567890 (Pedro Rodríguez Martínez)

### Centros:
- 1 - Centro Jurídico Central
- 2 - Centro Jurídico Norte
- 3 - Centro Jurídico Sur

### Ámbitos Legales:
- 1 - Civil
- 2 - Penal
- 3 - Familia
- 4 - Laboral
- 5 - Mercantil

---

## ✅ Checklist de Verificación

- [ ] PostgreSQL instalado y corriendo
- [ ] Base de datos `clinica_juridica` creada
- [ ] Script `init-caso-module.sql` ejecutado
- [ ] Variables de entorno configuradas (si es necesario)
- [ ] Proyecto compila sin errores: `mvn clean compile`
- [ ] Aplicación inicia correctamente: `mvn spring-boot:run`
- [ ] Endpoint `/` responde correctamente
- [ ] Endpoint `/api/casos/solicitante/V-12345678` retorna el caso de ejemplo

---

## 🚀 ¡Listo para Desarrollar!

Una vez que todo funcione, puedes:

1. ✅ Crear y consultar casos vía API REST
2. ✅ Ver las queries SQL en los logs (logging configurado en DEBUG)
3. ✅ Agregar nuevos endpoints siguiendo la arquitectura limpia existente
4. ✅ Revisar el código en los paquetes:
   - `domain.entities` - Entidades
   - `application.usecase` - Casos de uso
   - `infrastructure.adapter.persistence` - Implementaciones JDBC
   - `infrastructure.web.controller` - Controladores REST

---

**¿Necesitas ayuda?** Consulta los archivos de documentación en el directorio raíz del proyecto.

