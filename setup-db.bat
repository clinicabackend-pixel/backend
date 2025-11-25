@echo off
REM =====================================================
REM Script de Configuración de Base de Datos PostgreSQL
REM para el Sistema de Clínica Jurídica (Windows)
REM =====================================================

echo ==================================================
echo   CONFIGURACION DE BASE DE DATOS POSTGRESQL
echo ==================================================
echo.

REM Variables de configuración
set DB_NAME=clinica_juridica
set DB_USER=postgres
set DB_HOST=localhost
set DB_PORT=5432

echo Configuracion:
echo    - Base de datos: %DB_NAME%
echo    - Usuario: %DB_USER%
echo    - Host: %DB_HOST%
echo    - Puerto: %DB_PORT%
echo.

REM Verificar si PostgreSQL está instalado
where psql >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: psql no encontrado en el PATH
    echo Por favor, asegurate de que PostgreSQL este instalado y en el PATH
    pause
    exit /b 1
)

echo Creando base de datos si no existe...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -c "CREATE DATABASE %DB_NAME%;" 2>nul
if %ERRORLEVEL% EQU 0 (
    echo Base de datos creada
) else (
    echo Base de datos ya existe
)
echo.

echo Ejecutando script de inicializacion...
if exist "src\main\resources\init-caso-module.sql" (
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f src\main\resources\init-caso-module.sql
    if %ERRORLEVEL% EQU 0 (
        echo Script ejecutado correctamente
    ) else (
        echo ERROR al ejecutar el script
        pause
        exit /b 1
    )
) else (
    echo ERROR: No se encontro el archivo init-caso-module.sql
    pause
    exit /b 1
)
echo.

echo ==================================================
echo   CONFIGURACION COMPLETADA EXITOSAMENTE
echo ==================================================
echo.
echo Proximos pasos:
echo    1. Ejecuta: mvn spring-boot:run
echo    2. La aplicacion estara disponible en: http://localhost:8080
echo.
pause

