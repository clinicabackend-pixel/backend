-- Limpieza inicial (opcional)

DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-----------------------------------------------------------
-- 1. TABLAS DE LOCALIZACIÓN (Jerarquía Geográfica)
-----------------------------------------------------------

CREATE TABLE "Estados" (
  "id_estado" INTEGER NOT NULL,
  "nombre_estado" VARCHAR(100),
  CONSTRAINT "PK_Estados" PRIMARY KEY ("id_estado")
);

CREATE TABLE "Municipios" (
  "id_municipio" INTEGER NOT NULL,
  "id_estado" INTEGER NOT NULL,
  "nombre_municipio" VARCHAR(100),
  CONSTRAINT "PK_Municipios" PRIMARY KEY ("id_municipio"),
  CONSTRAINT "FK_Municipios_Estados" FOREIGN KEY ("id_estado") REFERENCES "Estados"("id_estado")
);

CREATE TABLE "Parroquia" (
  "id_parroquia" INTEGER NOT NULL,
  "id_municipio" INTEGER NOT NULL,
  "nombre_parroquia" VARCHAR(100),
  CONSTRAINT "PK_Parroquia" PRIMARY KEY ("id_parroquia"),
  CONSTRAINT "FK_Parroquia_Municipios" FOREIGN KEY ("id_municipio") REFERENCES "Municipios"("id_municipio")
);

CREATE TABLE "Centros" (
  "id_centro" INTEGER NOT NULL,
  "nombre" VARCHAR(150),
  "id_parroquia" INTEGER NOT NULL,
  CONSTRAINT "PK_Centros" PRIMARY KEY ("id_centro"),
  CONSTRAINT "FK_Centros_Parroquia" FOREIGN KEY ("id_parroquia") REFERENCES "Parroquia"("id_parroquia")
);

-----------------------------------------------------------
-- 2. TABLAS MAESTRAS Y CATÁLOGOS
-----------------------------------------------------------

CREATE TABLE "Niveles_Educativos" (
  "id_nivel_edu" INTEGER NOT NULL,
  "nivel" VARCHAR(100),
  "año" INTEGER,
  CONSTRAINT "PK_Niveles_Educativos" PRIMARY KEY ("id_nivel_edu")
);

CREATE TABLE "Ambitos_Legales" (
  "id_ambito_legal" INTEGER NOT NULL,
  "materia" VARCHAR(100),
  "tipo" VARCHAR(100),
  "descripción" TEXT,
  CONSTRAINT "PK_Ambitos_Legales" PRIMARY KEY ("id_ambito_legal")
);

CREATE TABLE "Tribunales" (
  "id_tribunal" INTEGER NOT NULL,
  "tipo_tribunal" VARCHAR(100),
  "nombre_tribunal" VARCHAR(150),
  CONSTRAINT "PK_Tribunales" PRIMARY KEY ("id_tribunal")
);

-----------------------------------------------------------
-- 3. PERSONAS Y ACTORES (Herencia aplicada)
-----------------------------------------------------------

CREATE TABLE "Solicitante" (
  "id_solicitante" VARCHAR(20) NOT NULL, -- Cédula
  "nombre" VARCHAR(150),
  "sexo" VARCHAR(20),
  "email" VARCHAR(150),
  "edad" INTEGER,
  "nacionalidad" VARCHAR(50),
  "f_nacimiento" DATE,
  "concubinato" VARCHAR(50), -- Corregido de 'conbinato'
  "estado_civil" VARCHAR(50),
  "descripcion_trabajo" TEXT,
  "tipo_trabajo" VARCHAR(50),
  "id_nivel_edu" INTEGER,
  CONSTRAINT "PK_Solicitante" PRIMARY KEY ("id_solicitante"),
  CONSTRAINT "FK_Solicitante_NivelEdu" FOREIGN KEY ("id_nivel_edu") REFERENCES "Niveles_Educativos"("id_nivel_edu")
);

CREATE TABLE "Beneficiario" (
  "id_beneficiario" VARCHAR(20) NOT NULL,
  "nombre" VARCHAR(150),
  "sexo" VARCHAR(20),
  "email" VARCHAR(150),
  "edad" INTEGER,
  "parentesco" VARCHAR(50),
  CONSTRAINT "PK_Beneficiario" PRIMARY KEY ("id_beneficiario")
);

CREATE TABLE "Coordinador" (
  "cedula" VARCHAR(20) NOT NULL,
  "nombre" VARCHAR(150),
  "sexo" VARCHAR(20),
  "email" VARCHAR(150),
  "estatus" VARCHAR(50),
  CONSTRAINT "PK_Coordinador" PRIMARY KEY ("cedula")
);

CREATE TABLE "Usuario" (
  "id_usuario" VARCHAR(50) NOT NULL,
  "nombre" VARCHAR(150),
  "sexo" VARCHAR(20),
  "email" VARCHAR(150),
  "username" VARCHAR(50) NOT NULL,
  "contraseña" VARCHAR(255),
  "estatus" VARCHAR(50),
  "tipo_usuario" VARCHAR(50),
  CONSTRAINT "PK_Usuario" PRIMARY KEY ("id_usuario")
  -- Nota: Eliminé 'username' de la PK compuesta para facilitar las FK
);

-----------------------------------------------------------
-- 4. INFORMACIÓN SOCIOECONÓMICA (Relación 1:1 con Solicitante)
-----------------------------------------------------------

CREATE TABLE "Viviendas" (
  "id_solicitante" VARCHAR(20) NOT NULL,
  "tipo" VARCHAR(50),
  "cant_habitaciones" INTEGER,
  "cant_baños" INTEGER,
  "material_paredes" VARCHAR(50),
  "aguas_negras" VARCHAR(50),
  "servicio_agua" VARCHAR(50),
  "material_techo" VARCHAR(50),
  "material_piso" VARCHAR(50),
  "servicio_aseo" VARCHAR(50),
  CONSTRAINT "PK_Viviendas" PRIMARY KEY ("id_solicitante"),
  CONSTRAINT "FK_Viviendas_Solicitante" FOREIGN KEY ("id_solicitante") REFERENCES "Solicitante"("id_solicitante")
);

CREATE TABLE "Familias" (
  "id_solicitante" VARCHAR(20) NOT NULL,
  "ingresos_mes" DECIMAL(10,2),
  "cant_personas" INTEGER,
  "jefe_familia" BOOLEAN,
  "cant_sin_trabajo" INTEGER,
  "cant_estudiando" INTEGER,
  "cant_niños" INTEGER,
  "cant_trabaja" INTEGER,
  "id_nivel_edu" INTEGER,
  CONSTRAINT "PK_Familias" PRIMARY KEY ("id_solicitante"),
  CONSTRAINT "FK_Familias_Solicitante" FOREIGN KEY ("id_solicitante") REFERENCES "Solicitante"("id_solicitante"),
  CONSTRAINT "FK_Familias_NivelEdu" FOREIGN KEY ("id_nivel_edu") REFERENCES "Niveles_Educativos"("id_nivel_edu")
);

CREATE TABLE "Trabajos" (
  "cedula" VARCHAR(20) NOT NULL,
  "trabaja" VARCHAR(50),
  "condición_actividad" VARCHAR(100),
  "condición_trabajo" VARCHAR(100),
  "esta_buscando" VARCHAR(50),
  CONSTRAINT "PK_Trabajos" PRIMARY KEY ("cedula"),
  CONSTRAINT "FK_Trabajos_Solicitante" FOREIGN KEY ("cedula") REFERENCES "Solicitante"("id_solicitante")
);

-----------------------------------------------------------
-- 5. GESTIÓN DE CASOS (Núcleo del sistema)
-----------------------------------------------------------

CREATE TABLE "CASOS" (
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
  CONSTRAINT "FK_Casos_Centro" FOREIGN KEY ("id_centro") REFERENCES "Centros"("id_centro"),
  CONSTRAINT "FK_Casos_Ambito" FOREIGN KEY ("id_ambito_legal") REFERENCES "Ambitos_Legales"("id_ambito_legal"),
  CONSTRAINT "FK_Casos_Solicitante" FOREIGN KEY ("id_solicitante") REFERENCES "Solicitante"("id_solicitante")
);

-- Tablas hijas de CASOS

CREATE TABLE "Solicitantes_casos" (
  "num_caso" VARCHAR(50) NOT NULL,
  "id_solicitante" VARCHAR(20) NOT NULL,
  CONSTRAINT "PK_Solicitantes_casos" PRIMARY KEY ("num_caso", "id_solicitante"),
  CONSTRAINT "FK_SC_Caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_SC_Solicitante" FOREIGN KEY ("id_solicitante") REFERENCES "Solicitante"("id_solicitante")
);

CREATE TABLE "Beneficiarios_casos" (
  "id_beneficiario" VARCHAR(20) NOT NULL,
  "num_caso" VARCHAR(50) NOT NULL,
  "tipo_beneficiario" VARCHAR(50),
  "parentesco" VARCHAR(50),
  CONSTRAINT "PK_Beneficiarios_casos" PRIMARY KEY ("id_beneficiario", "num_caso"),
  CONSTRAINT "FK_BC_Caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_BC_Beneficiario" FOREIGN KEY ("id_beneficiario") REFERENCES "Beneficiario"("id_beneficiario")
);

CREATE TABLE "Acciones" (
  "id_accion" SERIAL, -- Autoincremental recomendado
  "num_caso" VARCHAR(50) NOT NULL,
  "titulo" VARCHAR(150),
  "descripcion" TEXT,
  "id_usuario" VARCHAR(50),
  "f_registro" DATE,
  "f_ejecucion" DATE,
  CONSTRAINT "PK_Acciones" PRIMARY KEY ("id_accion"),
  CONSTRAINT "FK_Acciones_Caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_Acciones_Usuario" FOREIGN KEY ("id_usuario") REFERENCES "Usuario"("id_usuario")
);

CREATE TABLE "Pruebas" (
  "id_caso" VARCHAR(50) NOT NULL,
  "id_prueba" SERIAL,
  "fecha" DATE,
  "documento" VARCHAR(255), -- URL o ruta del archivo
  CONSTRAINT "PK_Pruebas" PRIMARY KEY ("id_caso", "id_prueba"),
  CONSTRAINT "FK_Pruebas_Caso" FOREIGN KEY ("id_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Expediente_Tribunales" (
  "num_expediente" VARCHAR(50) NOT NULL,
  "fecha_creación" DATE,
  "id_tribunal" INTEGER,
  "id_caso" VARCHAR(50),
  CONSTRAINT "PK_Expediente" PRIMARY KEY ("num_expediente"),
  CONSTRAINT "FK_Exp_Tribunal" FOREIGN KEY ("id_tribunal") REFERENCES "Tribunales"("id_tribunal"),
  CONSTRAINT "FK_Exp_Caso" FOREIGN KEY ("id_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Citas" (
  "id_cita" SERIAL,
  "num_caso" VARCHAR(50) NOT NULL,
  "fecha" DATE NOT NULL,
  "estado" VARCHAR(50),
  "orientación" VARCHAR(100),
  CONSTRAINT "PK_Citas" PRIMARY KEY ("id_cita"),
  CONSTRAINT "FK_Citas_Caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso")
);

-----------------------------------------------------------
-- 6. MÓDULO ACADÉMICO (Estudiantes y Materias)
-----------------------------------------------------------

CREATE TABLE "Semestre" (
  "id" INTEGER NOT NULL,
  "termino" VARCHAR(50),
  "periodo" VARCHAR(50),
  "fecha_ini" DATE,
  "fecha_fin" DATE,
  CONSTRAINT "PK_Semestre" PRIMARY KEY ("id")
);

CREATE TABLE "Materia" (
  "nrc" INTEGER NOT NULL,
  "nombre_materia" VARCHAR(150),
  CONSTRAINT "PK_Materia" PRIMARY KEY ("nrc")
);

CREATE TABLE "Profesor" (
  "cedula" VARCHAR(20) NOT NULL,
  "nombre" VARCHAR(150),
  "sexo" VARCHAR(20),
  "email" VARCHAR(150),
  CONSTRAINT "PK_Profesor" PRIMARY KEY ("cedula")
);

CREATE TABLE "Estudiante" (
  "id_estudiante" VARCHAR(20) NOT NULL,
  "nombre" VARCHAR(150),
  "sexo" VARCHAR(20),
  "email" VARCHAR(150),
  "username" VARCHAR(50),
  "contraseña" VARCHAR(255),
  "estatus" VARCHAR(50),
  "culminado" BOOLEAN,
  "tipo" VARCHAR(50),
  CONSTRAINT "PK_Estudiante" PRIMARY KEY ("id_estudiante")
);

CREATE TABLE "Secciones" (
  "id_materia" INTEGER NOT NULL,
  "id_sección" INTEGER NOT NULL,
  "id_semestre" INTEGER NOT NULL,
  "id_profesor" VARCHAR(20),
  "id_coordinador" VARCHAR(20),
  CONSTRAINT "PK_Secciones" PRIMARY KEY ("id_materia", "id_sección", "id_semestre"),
  CONSTRAINT "FK_Secc_Materia" FOREIGN KEY ("id_materia") REFERENCES "Materia"("nrc"),
  CONSTRAINT "FK_Secc_Semestre" FOREIGN KEY ("id_semestre") REFERENCES "Semestre"("id"),
  CONSTRAINT "FK_Secc_Profesor" FOREIGN KEY ("id_profesor") REFERENCES "Profesor"("cedula"),
  CONSTRAINT "FK_Secc_Coordinador" FOREIGN KEY ("id_coordinador") REFERENCES "Coordinador"("cedula")
);

CREATE TABLE "Estudiantes_inscritos" (
  "id_estudiante" VARCHAR(20) NOT NULL,
  "id_materia" INTEGER NOT NULL,
  "id_semestre" INTEGER NOT NULL,
  "id_sección" INTEGER NOT NULL,
  CONSTRAINT "PK_Estudiantes_inscritos" PRIMARY KEY ("id_estudiante", "id_materia", "id_semestre", "id_sección"),
  CONSTRAINT "FK_Insc_Estudiante" FOREIGN KEY ("id_estudiante") REFERENCES "Estudiante"("id_estudiante"),
  CONSTRAINT "FK_Insc_Seccion" FOREIGN KEY ("id_materia", "id_sección", "id_semestre")
    REFERENCES "Secciones"("id_materia", "id_sección", "id_semestre")
);

CREATE TABLE "Asignaciones" (
  "id_estudiante" VARCHAR(20) NOT NULL,
  "num_caso" VARCHAR(50) NOT NULL,
  "id_semestre" INTEGER NOT NULL,
  "id_profesor" VARCHAR(20) NOT NULL,
  "tipo_estudiante" VARCHAR(50),
  "fecha_asiganación" DATE,
  "fecha_fin_asignación" DATE,
  CONSTRAINT "PK_Asignaciones" PRIMARY KEY ("id_estudiante", "num_caso", "id_semestre", "id_profesor"),
  CONSTRAINT "FK_Asig_Estudiante" FOREIGN KEY ("id_estudiante") REFERENCES "Estudiante"("id_estudiante"),
  CONSTRAINT "FK_Asig_Caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_Asig_Semestre" FOREIGN KEY ("id_semestre") REFERENCES "Semestre"("id"),
  CONSTRAINT "FK_Asig_Profesor" FOREIGN KEY ("id_profesor") REFERENCES "Profesor"("cedula")
);
