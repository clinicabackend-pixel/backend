-- -----------------------------------------------------
-- Limpieza inicial (Opcional, para reiniciar la BD)
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- -----------------------------------------------------
-- TABLAS CATÁLOGOS E INDEPENDIENTES
-- -----------------------------------------------------

CREATE TABLE "Ambitos_Legales" (
  "id_ambito_legal" SERIAL PRIMARY KEY,
  "materia" VARCHAR(100),
  "tipo" VARCHAR(50),
  "descripcion" TEXT
);

CREATE TABLE "Estados" (
  "id_estado" SERIAL PRIMARY KEY,
  "nombre_estado" VARCHAR(100) NOT NULL
);

CREATE TABLE "Niveles_Educativos" (
  "id_nivel_edu" SERIAL PRIMARY KEY,
  "nivel" VARCHAR(100),
  "anio" INTEGER
);

CREATE TABLE "Electrodomesticos" (
  "id_electrodomestico" SERIAL PRIMARY KEY,
  "nombre_electrodomestico" VARCHAR(100)
);

CREATE TABLE "Tribunales" (
  "id_tribunal" SERIAL PRIMARY KEY,
  "tipo_tribunal" VARCHAR(100),
  "nombre_tribunal" VARCHAR(150)
);

CREATE TABLE "Trabajos" (
  "id_trabajo" VARCHAR(50) PRIMARY KEY,
  "trabaja" VARCHAR(2), -- SI/NO
  "condicion_actividad" VARCHAR(100),
  "condicion_trabajo" VARCHAR(100),
  "esta_buscando" VARCHAR(2)
);

CREATE TABLE "Viviendas" (
  "id_vivienda" VARCHAR(50) PRIMARY KEY,
  "tipo" VARCHAR(50),
  "cant_habitaciones" INTEGER,
  "cant_banos" INTEGER,
  "material_paredes" VARCHAR(50), -- Cambiado de entero a varchar (usualmente es descripción)
  "aguas_negras" VARCHAR(50),
  "servicio_agua" VARCHAR(50),
  "material_techo" VARCHAR(50),
  "material_piso" VARCHAR(50),
  "servicio_aseo" VARCHAR(50)
);

CREATE TABLE "Semestre" (
  "id" SERIAL PRIMARY KEY,
  "termino" VARCHAR(50),
  "periodo" VARCHAR(50),
  "fecha_ini" DATE,
  "fecha_fin" DATE
);

CREATE TABLE "Materia" (
  "nrc" INTEGER PRIMARY KEY,
  "nombre_materia" VARCHAR(100)
);

CREATE TABLE "Coordinador" (
  "cedula" VARCHAR(20) PRIMARY KEY,
  "nombre" VARCHAR(100),
  "sexo" VARCHAR(20),
  "email" VARCHAR(100),
  "estatus" VARCHAR(20)
);

CREATE TABLE "Profesor" (
  "cedula" VARCHAR(20) PRIMARY KEY,
  "nombre" VARCHAR(100),
  "sexo" VARCHAR(20),
  "email" VARCHAR(100)
);

-- -----------------------------------------------------
-- TABLAS DEPENDIENTES (NIVEL 1)
-- -----------------------------------------------------

CREATE TABLE "Municipios" (
  "id_municipio" SERIAL PRIMARY KEY,
  "id_estado" INTEGER NOT NULL,
  "nombre_municipio" VARCHAR(100),
  CONSTRAINT "FK_Municipios_id_estado"
    FOREIGN KEY ("id_estado") REFERENCES "Estados"("id_estado")
);

CREATE TABLE "Personal" (
  "id_usuario" VARCHAR(50) PRIMARY KEY,
  "nombre" VARCHAR(100),
  "sexo" VARCHAR(20),
  "email" VARCHAR(100) UNIQUE,
  "username" VARCHAR(50) UNIQUE, -- Agregado Unique
  "contrasena" VARCHAR(255),
  "estatus" VARCHAR(20),
  "tipo_usuario" VARCHAR(50)
);

CREATE TABLE "Estudiante" (
  "id_estudiante" VARCHAR(50) PRIMARY KEY,
  "nombre" VARCHAR(100),
  "sexo" VARCHAR(20),
  "email" VARCHAR(100),
  "username" VARCHAR(50),
  "contrasena" VARCHAR(255),
  "estatus" VARCHAR(20),
  "culminado" BOOLEAN,
  "tipo" VARCHAR(50)
);

CREATE TABLE "Usuarios" (
  "id_solicitante" VARCHAR(50) PRIMARY KEY, -- Equivalente al usuario externo/cliente
  "nombre" VARCHAR(100),
  "sexo" VARCHAR(20),
  "email" VARCHAR(100),
  "edad" INTEGER,
  "nacionalidad" VARCHAR(50),
  "f_nacimiento" DATE,
  "concubinato" VARCHAR(50), -- Corregido typo 'conbinato'
  "estado_civil" VARCHAR(50),
  "descripcion_trabajo" TEXT,
  "id_nivel_edu" INTEGER,
  "id_vivienda" VARCHAR(50), -- Ajustado para coincidir con PK de Viviendas
  "id_trabajo" VARCHAR(50),  -- Ajustado para coincidir con PK de Trabajos
  "id_familia" INTEGER,      -- Nota: La tabla Familias se crea después, ver lógica circular abajo
  "id_parroquia" INTEGER,
  CONSTRAINT "FK_Usuarios_id_nivel_edu" FOREIGN KEY ("id_nivel_edu") REFERENCES "Niveles_Educativos"("id_nivel_edu"),
  CONSTRAINT "FK_Usuarios_id_vivienda" FOREIGN KEY ("id_vivienda") REFERENCES "Viviendas"("id_vivienda"),
  CONSTRAINT "FK_Usuarios_id_trabajo" FOREIGN KEY ("id_trabajo") REFERENCES "Trabajos"("id_trabajo")
);

-- -----------------------------------------------------
-- TABLAS DEPENDIENTES (NIVEL 2)
-- -----------------------------------------------------

CREATE TABLE "Parroquia" (
  "id_parroquia" SERIAL PRIMARY KEY,
  "id_municipio" INTEGER NOT NULL,
  "nombre_parroquia" VARCHAR(100),
  CONSTRAINT "FK_Parroquia_id_municipio"
    FOREIGN KEY ("id_municipio") REFERENCES "Municipios"("id_municipio")
);

-- Ahora que existe Parroquia, agregamos la FK a Usuarios (que la dejamos pendiente o se altera)
ALTER TABLE "Usuarios" ADD CONSTRAINT "FK_Usuarios_id_parroquia"
    FOREIGN KEY ("id_parroquia") REFERENCES "Parroquia"("id_parroquia");

CREATE TABLE "Centros" (
  "id_centro" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(100),
  "id_parroquia" INTEGER,
  CONSTRAINT "FK_Centros_id_parroquia"
    FOREIGN KEY ("id_parroquia") REFERENCES "Parroquia"("id_parroquia")
);

CREATE TABLE "Familias" (
  "id_solicitante" VARCHAR(50) PRIMARY KEY,
  "ingresos_mes" DECIMAL(10,2),
  "cant_personas" INTEGER,
  "jefe_familia" BOOLEAN,
  "cant_sin_trabajo" INTEGER,
  "cant_estudiando" INTEGER,
  "cant_ninos" INTEGER,
  "cant_trabaja" INTEGER,
  "id_nivel_edu" INTEGER,
  CONSTRAINT "FK_Familias_id_solicitante"
    FOREIGN KEY ("id_solicitante") REFERENCES "Usuarios"("id_solicitante"),
  CONSTRAINT "FK_Familias_id_nivel_edu"
    FOREIGN KEY ("id_nivel_edu") REFERENCES "Niveles_Educativos"("id_nivel_edu")
);

CREATE TABLE "Telefonos" (
  "id_solicitante" VARCHAR(50),
  "telefono" VARCHAR(20),
  PRIMARY KEY ("id_solicitante", "telefono"),
  CONSTRAINT "FK_Telefonos_id_solicitante"
    FOREIGN KEY ("id_solicitante") REFERENCES "Usuarios"("id_solicitante")
);

CREATE TABLE "Electrodomesticos_Solicitantes" (
  "id_solicitante" VARCHAR(50),
  "id_electrodomestico" INTEGER,
  PRIMARY KEY ("id_solicitante", "id_electrodomestico"),
  CONSTRAINT "FK_Electro_Sol_id_electro"
    FOREIGN KEY ("id_electrodomestico") REFERENCES "Electrodomesticos"("id_electrodomestico"),
  CONSTRAINT "FK_Electro_Sol_id_solicitante"
    FOREIGN KEY ("id_solicitante") REFERENCES "Usuarios"("id_solicitante")
);

CREATE TABLE "Secciones" (
  "id_materia" INTEGER,
  "id_seccion" INTEGER,
  "id_semestre" INTEGER,
  "id_profesor" VARCHAR(20),
  "id_coordinador" VARCHAR(20),
  PRIMARY KEY ("id_materia", "id_seccion", "id_semestre"),
  CONSTRAINT "FK_Secciones_id_semestre" FOREIGN KEY ("id_semestre") REFERENCES "Semestre"("id"),
  CONSTRAINT "FK_Secciones_id_profesor" FOREIGN KEY ("id_profesor") REFERENCES "Profesor"("cedula"),
  CONSTRAINT "FK_Secciones_id_coordinador" FOREIGN KEY ("id_coordinador") REFERENCES "Coordinador"("cedula"),
  CONSTRAINT "FK_Secciones_id_materia" FOREIGN KEY ("id_materia") REFERENCES "Materia"("nrc")
);

-- -----------------------------------------------------
-- TABLAS DEL SISTEMA DE CASOS
-- -----------------------------------------------------

CREATE TABLE "CASOS" (
  "num_caso" VARCHAR(50) PRIMARY KEY,
  "fecha_recepcion" DATE,
  "cant_beneficiarios" INTEGER,
  "tramite" VARCHAR(100),
  "estatus" VARCHAR(50),
  "sintesis" TEXT,
  "id_centro" INTEGER,
  "id_ambito_legal" INTEGER,
  "id_solicitante" VARCHAR(50),
  CONSTRAINT "FK_CASOS_id_ambito_legal" FOREIGN KEY ("id_ambito_legal") REFERENCES "Ambitos_Legales"("id_ambito_legal"),
  CONSTRAINT "FK_CASOS_id_solicitante" FOREIGN KEY ("id_solicitante") REFERENCES "Usuarios"("id_solicitante"),
  CONSTRAINT "FK_CASOS_id_centro" FOREIGN KEY ("id_centro") REFERENCES "Centros"("id_centro")
);

CREATE TABLE "Beneficiarios_casos" (
  "id_beneficiario" VARCHAR(50), -- Asumiendo que refiere a un usuario/solicitante adicional
  "num_caso" VARCHAR(50),
  "tipo_beneficiario" VARCHAR(50),
  "parentesco" VARCHAR(50),
  PRIMARY KEY ("id_beneficiario", "num_caso"),
  CONSTRAINT "FK_Beneficiarios_casos_id" FOREIGN KEY ("id_beneficiario") REFERENCES "Usuarios"("id_solicitante"),
  CONSTRAINT "FK_Beneficiarios_casos_caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Citas" (
  "fecha" TIMESTAMP, -- Cambiado a timestamp para hora exacta
  "num_caso" VARCHAR(50),
  "estado" VARCHAR(50),
  "orientacion" TEXT,
  PRIMARY KEY ("fecha", "num_caso"),
  CONSTRAINT "FK_Citas_num_caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Citas_atendidas" (
  "num_caso" VARCHAR(50),
  "fecha" TIMESTAMP, -- Debe coincidir con Citas si es la misma logica, o ser independiente
  "id_usuario" VARCHAR(50),
  PRIMARY KEY ("num_caso", "fecha", "id_usuario"),
  CONSTRAINT "FK_Citas_atendidas_id_usuario" FOREIGN KEY ("id_usuario") REFERENCES "Personal"("id_usuario"),
  CONSTRAINT "FK_Citas_atendidas_num_caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Pruebas" (
  "id_caso" VARCHAR(50), -- Ajustado para coincidir con num_caso que es VARCHAR
  "id_prueba" SERIAL,
  "fecha" DATE,
  "documento" VARCHAR(255), -- Ruta o nombre archivo
  PRIMARY KEY ("id_caso", "id_prueba"),
  CONSTRAINT "FK_Pruebas_id_caso" FOREIGN KEY ("id_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Acciones" (
  "id_accion" SERIAL,
  "num_caso" VARCHAR(50),
  "titulo" VARCHAR(100),
  "descripcion" TEXT,
  "id_usuario" VARCHAR(50), -- CORREGIDO: En Personal era varchar, aquí estaba int
  "f_registro" DATE,
  "f_ejecucion" DATE, -- Corregido de 'Tipo' a DATE
  PRIMARY KEY ("id_accion", "num_caso"),
  CONSTRAINT "FK_Acciones_num_caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_Acciones_id_usuario" FOREIGN KEY ("id_usuario") REFERENCES "Personal"("id_usuario")
);

CREATE TABLE "Cambios_estatus" (
  "id_usuario" VARCHAR(50),
  "num_caso" VARCHAR(50),
  "fecha_cambio" DATE,
  "status_ant" VARCHAR(50),
  PRIMARY KEY ("id_usuario", "num_caso", "fecha_cambio"), -- Agregué fecha a la PK por lógica histórica
  CONSTRAINT "FK_Cambios_estatus_num_caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_Cambios_estatus_id_usuario" FOREIGN KEY ("id_usuario") REFERENCES "Personal"("id_usuario")
);

-- -----------------------------------------------------
-- TABLAS ACADÉMICAS Y LEGALES
-- -----------------------------------------------------

CREATE TABLE "Estudiantes_inscritos" (
  "id_estudiante" VARCHAR(50),
  "id_materia" INTEGER,
  "id_semestre" INTEGER,
  "id_seccion" INTEGER,
  PRIMARY KEY ("id_estudiante", "id_materia", "id_semestre", "id_seccion"),
  CONSTRAINT "FK_Estudiantes_inscritos_seccion" 
    FOREIGN KEY ("id_materia", "id_seccion", "id_semestre") 
    REFERENCES "Secciones"("id_materia", "id_seccion", "id_semestre"),
  CONSTRAINT "FK_Estudiantes_inscritos_id_estudiante" 
    FOREIGN KEY ("id_estudiante") REFERENCES "Estudiante"("id_estudiante")
);

CREATE TABLE "Asignaciones" (
  "id_asignacion" SERIAL PRIMARY KEY, -- Agregué PK surrogate para simplificar
  "id_estudiante" VARCHAR(50),
  "num_caso" VARCHAR(50),
  "id_semestre" INTEGER,
  "id_profesor" VARCHAR(20),
  "tipo_estudiante" VARCHAR(50),
  "fecha_asignacion" DATE,
  "fecha_fin_asignacion" DATE,
  "id_materia" INTEGER,
  "id_seccion" INTEGER,
  CONSTRAINT "FK_Asignaciones_num_caso" FOREIGN KEY ("num_caso") REFERENCES "CASOS"("num_caso"),
  CONSTRAINT "FK_Asignaciones_id_estudiante" FOREIGN KEY ("id_estudiante") REFERENCES "Estudiante"("id_estudiante"),
  -- FK compuesta hacia Secciones
  CONSTRAINT "FK_Asignaciones_seccion" 
    FOREIGN KEY ("id_materia", "id_seccion", "id_semestre") 
    REFERENCES "Secciones"("id_materia", "id_seccion", "id_semestre")
);

CREATE TABLE "Expediente_Tribunales" (
  "num_expediente" VARCHAR(50) PRIMARY KEY,
  "fecha_creacion" DATE,
  "id_tribunal" INTEGER,
  "id_caso" VARCHAR(50), -- Ajustado a varchar por CASOS
  CONSTRAINT "FK_Expediente_Trib_id_tribunal" FOREIGN KEY ("id_tribunal") REFERENCES "Tribunales"("id_tribunal"),
  CONSTRAINT "FK_Expediente_Trib_id_caso" FOREIGN KEY ("id_caso") REFERENCES "CASOS"("num_caso")
);

CREATE TABLE "Documentos_Tribunales" (
  "num_expediente" VARCHAR(50),
  "folio_ini" INTEGER,
  "folio_fin" INTEGER,
  "fecha" DATE,
  "titulo" VARCHAR(100),
  "descripcion" TEXT,
  PRIMARY KEY ("num_expediente", "folio_ini"),
  CONSTRAINT "FK_Doc_Tribunales_num_exp" FOREIGN KEY ("num_expediente") REFERENCES "Expediente_Tribunales"("num_expediente")
);