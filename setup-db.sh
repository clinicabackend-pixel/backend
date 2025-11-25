#!/bin/bash

# =====================================================
# Script de Configuración de Base de Datos PostgreSQL
# para el Sistema de Clínica Jurídica
# =====================================================

set -e  # Salir si hay algún error

echo "=================================================="
echo "  CONFIGURACIÓN DE BASE DE DATOS POSTGRESQL"
echo "=================================================="
echo ""

# Variables de configuración
DB_NAME="clinica_juridica"
DB_USER="${DB_USERNAME:-postgres}"
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"

echo "📊 Configuración:"
echo "   - Base de datos: $DB_NAME"
echo "   - Usuario: $DB_USER"
echo "   - Host: $DB_HOST"
echo "   - Puerto: $DB_PORT"
echo ""

# Verificar si PostgreSQL está corriendo
echo "🔍 Verificando si PostgreSQL está corriendo..."
if ! pg_isready -h $DB_HOST -p $DB_PORT -U $DB_USER >/dev/null 2>&1; then
    echo "❌ ERROR: PostgreSQL no está corriendo o no es accesible."
    echo "   Por favor, inicia PostgreSQL y vuelve a intentar."
    exit 1
fi
echo "✅ PostgreSQL está corriendo"
echo ""

# Verificar si la base de datos existe
echo "🔍 Verificando si la base de datos existe..."
if psql -h $DB_HOST -p $DB_PORT -U $DB_USER -lqt | cut -d \| -f 1 | grep -qw $DB_NAME; then
    echo "⚠️  La base de datos '$DB_NAME' ya existe."
    read -p "¿Deseas eliminarla y recrearla? (s/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Ss]$ ]]; then
        echo "🗑️  Eliminando base de datos..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -c "DROP DATABASE IF EXISTS $DB_NAME;"
    else
        echo "ℹ️  Usando base de datos existente"
    fi
fi

# Crear la base de datos si no existe
if ! psql -h $DB_HOST -p $DB_PORT -U $DB_USER -lqt | cut -d \| -f 1 | grep -qw $DB_NAME; then
    echo "📦 Creando base de datos '$DB_NAME'..."
    psql -h $DB_HOST -p $DB_PORT -U $DB_USER -c "CREATE DATABASE $DB_NAME;"
    echo "✅ Base de datos creada"
fi
echo ""

# Ejecutar el script de inicialización
echo "📝 Ejecutando script de inicialización..."
if [ -f "src/main/resources/init-caso-module.sql" ]; then
    psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f src/main/resources/init-caso-module.sql
    echo "✅ Script de inicialización ejecutado correctamente"
else
    echo "❌ ERROR: No se encontró el archivo 'src/main/resources/init-caso-module.sql'"
    exit 1
fi
echo ""

# Verificar las tablas creadas
echo "📋 Verificando tablas creadas..."
TABLE_COUNT=$(psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -t -c "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public';")
echo "✅ Total de tablas creadas: $TABLE_COUNT"
echo ""

# Mostrar las tablas
echo "📊 Tablas en la base de datos:"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "\dt"
echo ""

# Verificar datos de prueba
echo "🔍 Verificando datos de prueba..."
CASOS_COUNT=$(psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -t -c 'SELECT COUNT(*) FROM "CASOS";')
SOLICITANTES_COUNT=$(psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -t -c 'SELECT COUNT(*) FROM "Solicitante";')
echo "   - Casos: $CASOS_COUNT"
echo "   - Solicitantes: $SOLICITANTES_COUNT"
echo ""

echo "=================================================="
echo "  ✅ CONFIGURACIÓN COMPLETADA EXITOSAMENTE"
echo "=================================================="
echo ""
echo "🚀 Próximos pasos:"
echo "   1. Ejecuta: mvn spring-boot:run"
echo "   2. La aplicación estará disponible en: http://localhost:8080"
echo "   3. Prueba el endpoint: curl http://localhost:8080/api/casos/solicitante/V-12345678"
echo ""
echo "📚 Documentación:"
echo "   - Ver: SETUP_DATABASE.md"
echo "   - Ver: CASO_VERTICAL_SLICE.md"
echo ""

