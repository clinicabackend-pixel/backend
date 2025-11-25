-- =====================================================
-- SCRIPT DE INICIALIZACIÓN PARA MÓDULO CASO
-- =====================================================
-- Este script crea las tablas mínimas necesarias para probar
-- el módulo de Caso con el CasoJdbcAdapter.
--
-- INSTRUCCIONES:
-- 1. Conectarse a PostgreSQL: psql -U postgres
-- 2. Crear la base de datos: CREATE DATABASE clinica_juridica;
-- 3. Conectarse a la BD: \c clinica_juridica
-- 4. Ejecutar este script: \i init-caso-module.sql
-- =====================================================

-- Crear tabla de Solicitantes (necesaria por FK)
CREATE TABLE IF NOT EXISTS "Solicitante" (
  "id_solicitante" VARCHAR(20) NOT NULL,
  "nombres" VARCHAR(100),
  "apellidos" VARCHAR(100),
  "fecha_nacimiento" DATE,
  "telefono" VARCHAR(20),
  "email" VARCHAR(100),
  CONSTRAINT "PK_Solicitante" PRIMARY KEY ("id_solicitante")
);

-- Crear tabla de Centros (opcional por FK)
CREATE TABLE IF NOT EXISTS "Centros" (
  "id_centro" INTEGER NOT NULL,
  "nombre" VARCHAR(150),
  CONSTRAINT "PK_Centros" PRIMARY KEY ("id_centro")
);

-- Crear tabla de Ámbitos Legales (opcional por FK)
CREATE TABLE IF NOT EXISTS "Ambito_Legal" (
  "id_ambito_legal" INTEGER NOT NULL,
  "nombre" VARCHAR(100),
  "descripcion" TEXT,
  CONSTRAINT "PK_Ambito_Legal" PRIMARY KEY ("id_ambito_legal")
);

-- Crear tabla principal CASOS
CREATE TABLE IF NOT EXISTS "CASOS" (
  "num_caso" VARCHAR(50) NOT NULL,
  "fecha_recepción" DATE,
  "cant_beneficiarios" INTEGER,
  "tramite" VARCHAR(100),
  "estatus" VARCHAR(50),
  "sintesis" TEXT,
  "id_centro" INTEGER,
  "id_ambito_legal" INTEGER,
  "id_solicitante" VARCHAR(20),
  CONSTRAINT "PK_CASOS" PRIMARY KEY ("num_caso"),
  CONSTRAINT "FK_CASOS_Solicitante" FOREIGN KEY ("id_solicitante") 
    REFERENCES "Solicitante"("id_solicitante"),
  CONSTRAINT "FK_CASOS_Centro" FOREIGN KEY ("id_centro") 
    REFERENCES "Centros"("id_centro"),
  CONSTRAINT "FK_CASOS_AmbitoLegal" FOREIGN KEY ("id_ambito_legal") 
    REFERENCES "Ambito_Legal"("id_ambito_legal")
);

-- =====================================================
-- DATOS DE PRUEBA
-- =====================================================

-- Insertar centros de prueba
INSERT INTO "Centros" ("id_centro", "nombre") VALUES
  (1, 'Centro Jurídico Central'),
  (2, 'Centro Jurídico Norte'),
  (3, 'Centro Jurídico Sur')
ON CONFLICT ("id_centro") DO NOTHING;

-- Insertar ámbitos legales de prueba
INSERT INTO "Ambito_Legal" ("id_ambito_legal", "nombre", "descripcion") VALUES
  (1, 'Civil', 'Derecho Civil'),
  (2, 'Penal', 'Derecho Penal'),
  (3, 'Familia', 'Derecho de Familia'),
  (4, 'Laboral', 'Derecho Laboral'),
  (5, 'Mercantil', 'Derecho Mercantil')
ON CONFLICT ("id_ambito_legal") DO NOTHING;

-- Insertar solicitantes de prueba
INSERT INTO "Solicitante" ("id_solicitante", "nombres", "apellidos", "fecha_nacimiento", "telefono", "email") VALUES
  ('V-12345678', 'Juan', 'Pérez García', '1985-05-15', '0412-1234567', 'juan.perez@email.com'),
  ('V-23456789', 'María', 'González López', '1990-08-20', '0424-2345678', 'maria.gonzalez@email.com'),
  ('V-34567890', 'Pedro', 'Rodríguez Martínez', '1978-12-10', '0414-3456789', 'pedro.rodriguez@email.com')
ON CONFLICT ("id_solicitante") DO NOTHING;

-- Insertar un caso de ejemplo
INSERT INTO "CASOS" 
  ("num_caso", "fecha_recepción", "cant_beneficiarios", "tramite", 
   "estatus", "sintesis", "id_centro", "id_ambito_legal", "id_solicitante")
VALUES
  ('CASO-EJEMPLO-001', '2024-01-15', 2, 'Divorcio', 'ABIERTO', 
   'Solicitud de asesoría legal para proceso de divorcio contencioso con menores de edad',
   1, 3, 'V-12345678')
ON CONFLICT ("num_caso") DO NOTHING;

-- =====================================================
-- VERIFICACIÓN
-- =====================================================

-- Consultar los datos insertados
SELECT 'Centros insertados:' as info, COUNT(*) as total FROM "Centros"
UNION ALL
SELECT 'Ámbitos Legales:', COUNT(*) FROM "Ambito_Legal"
UNION ALL
SELECT 'Solicitantes:', COUNT(*) FROM "Solicitante"
UNION ALL
SELECT 'Casos:', COUNT(*) FROM "CASOS";

-- Mostrar el caso de ejemplo
SELECT * FROM "CASOS";

