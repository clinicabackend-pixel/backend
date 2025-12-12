-- -----------------------------------------------------
-- LIMPIEZA INICIAL (Para evitar errores si ya existe)
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- -----------------------------------------------------
-- 1. TABLAS CATÁLOGOS E INDEPENDIENTES
-- -----------------------------------------------------

CREATE TABLE ambitos_legales (
  id_ambito_legal SERIAL PRIMARY KEY,
  materia VARCHAR(100),
  tipo VARCHAR(50),
  descripcion TEXT
);

CREATE TABLE estados (
  id_estado SERIAL PRIMARY KEY,
  nombre_estado VARCHAR(100) NOT NULL
);

CREATE TABLE niveles_educativos (
  id_nivel_edu SERIAL PRIMARY KEY,
  nivel VARCHAR(100),
  anio INTEGER
);

CREATE TABLE electrodomesticos (
  id_electrodomestico SERIAL PRIMARY KEY,
  nombre_electrodomestico VARCHAR(100)
);

CREATE TABLE tribunales (
  id_tribunal SERIAL PRIMARY KEY,
  tipo_tribunal VARCHAR(100),
  nombre_tribunal VARCHAR(150)
);

CREATE TABLE trabajos (
  id_trabajo VARCHAR(50) PRIMARY KEY,
  trabaja VARCHAR(2), -- 'SI'/'NO'
  condicion_actividad VARCHAR(100),
  condicion_trabajo VARCHAR(100),
  esta_buscando VARCHAR(2) -- 'SI'/'NO'
);

CREATE TABLE viviendas (
  id_vivienda VARCHAR(50) PRIMARY KEY,
  tipo VARCHAR(50),
  cant_habitaciones INTEGER,
  cant_banos INTEGER,
  material_paredes VARCHAR(100), -- Ajustado a 100 caracteres
  aguas_negras VARCHAR(100),
  servicio_agua VARCHAR(100),
  material_techo VARCHAR(100),
  material_piso VARCHAR(100),
  servicio_aseo VARCHAR(100)
);

CREATE TABLE semester (
  id SERIAL PRIMARY KEY,
  termino VARCHAR(50),
  periodo VARCHAR(50),
  fecha_ini DATE,
  fecha_fin DATE
);

CREATE TABLE materia (
  nrc INTEGER PRIMARY KEY,
  nombre_materia VARCHAR(100)
);

CREATE TABLE coordinador (
  cedula VARCHAR(20) PRIMARY KEY,
  nombre VARCHAR(100),
  sexo VARCHAR(20),
  email VARCHAR(100),
  estatus VARCHAR(20)
);

CREATE TABLE profesor (
  cedula VARCHAR(20) PRIMARY KEY,
  nombre VARCHAR(100),
  sexo VARCHAR(20),
  email VARCHAR(100)
);

-- -----------------------------------------------------
-- 2. TABLAS DEPENDIENTES (NIVEL 1)
-- -----------------------------------------------------

CREATE TABLE municipios (
  id_municipio SERIAL PRIMARY KEY,
  id_estado INTEGER NOT NULL,
  nombre_municipio VARCHAR(100),
  CONSTRAINT fk_municipios_id_estado 
    FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

CREATE TABLE personal (
  id_usuario VARCHAR(50) PRIMARY KEY,
  nombre VARCHAR(100),
  sexo VARCHAR(20),
  email VARCHAR(100) UNIQUE,
  username VARCHAR(50) UNIQUE,
  contrasena VARCHAR(255),
  estatus VARCHAR(20),
  tipo_usuario VARCHAR(50)
);

CREATE TABLE estudiante (
  id_estudiante VARCHAR(50) PRIMARY KEY,
  nombre VARCHAR(100),
  sexo VARCHAR(20),
  email VARCHAR(100),
  username VARCHAR(50) UNIQUE,
  contrasena VARCHAR(255),
  estatus VARCHAR(20),
  culminado BOOLEAN,
  tipo VARCHAR(50)
);

CREATE TABLE usuarios (
  id_usuario VARCHAR(50) PRIMARY KEY, -- Cliente externo
  nombre VARCHAR(100),
  sexo VARCHAR(20),
  email VARCHAR(100),
  edad INTEGER,
  nacionalidad VARCHAR(50),
  f_nacimiento DATE,
  concubinato VARCHAR(50),
  estado_civil VARCHAR(50),
  descripcion_trabajo TEXT,
  id_nivel_edu INTEGER,
  id_vivienda VARCHAR(50),
  id_trabajo VARCHAR(50),
  id_parroquia INTEGER,
  -- Constraint de Parroquia se agrega luego porque la tabla aun no existe
  CONSTRAINT fk_usuarios_id_nivel_edu FOREIGN KEY (id_nivel_edu) REFERENCES niveles_educativos(id_nivel_edu),
  CONSTRAINT fk_usuarios_id_vivienda FOREIGN KEY (id_vivienda) REFERENCES viviendas(id_vivienda),
  CONSTRAINT fk_usuarios_id_trabajo FOREIGN KEY (id_trabajo) REFERENCES trabajos(id_trabajo)
);

-- -----------------------------------------------------
-- 3. TABLAS DEPENDIENTES (NIVEL 2)
-- -----------------------------------------------------

CREATE TABLE parroquia (
  id_parroquia SERIAL PRIMARY KEY,
  id_municipio INTEGER NOT NULL,
  nombre_parroquia VARCHAR(100),
  CONSTRAINT fk_parroquia_id_municipio 
    FOREIGN KEY (id_municipio) REFERENCES municipios(id_municipio)
);

-- Agregar FK pendiente a Usuarios
ALTER TABLE usuarios ADD CONSTRAINT fk_usuarios_id_parroquia 
    FOREIGN KEY (id_parroquia) REFERENCES parroquia(id_parroquia);

CREATE TABLE centros (
  id_centro SERIAL PRIMARY KEY,
  nombre VARCHAR(100),
  id_parroquia INTEGER,
  CONSTRAINT fk_centros_id_parroquia 
    FOREIGN KEY (id_parroquia) REFERENCES parroquia(id_parroquia)
);

CREATE TABLE familias (
  id_solicitante VARCHAR(50) PRIMARY KEY,
  ingresos_mes DECIMAL(10,2),
  cant_personas INTEGER,
  jefe_familia BOOLEAN,
  cant_sin_trabajo INTEGER,
  cant_estudiando INTEGER,
  cant_ninos INTEGER,
  cant_trabaja INTEGER,
  id_nivel_edu INTEGER,
  CONSTRAINT fk_familias_id_solicitante 
    FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_familias_id_nivel_edu 
    FOREIGN KEY (id_nivel_edu) REFERENCES niveles_educativos(id_nivel_edu)
);

CREATE TABLE telefonos (
  id_solicitante VARCHAR(50),
  telefono VARCHAR(20),
  PRIMARY KEY (id_solicitante, telefono),
  CONSTRAINT fk_telefonos_id_solicitante 
    FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario)
);

CREATE TABLE electrodomesticos_solicitantes (
  id_solicitante VARCHAR(50),
  id_electrodomestico INTEGER,
  PRIMARY KEY (id_solicitante, id_electrodomestico),
  CONSTRAINT fk_electro_sol_id_electro 
    FOREIGN KEY (id_electrodomestico) REFERENCES electrodomesticos(id_electrodomestico),
  CONSTRAINT fk_electro_sol_id_solicitante 
    FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario)
);

CREATE TABLE secciones (
  id_materia INTEGER,
  id_seccion INTEGER,
  id_semestre INTEGER,
  id_profesor VARCHAR(20),
  id_coordinador VARCHAR(20),
  PRIMARY KEY (id_materia, id_seccion, id_semestre),
  CONSTRAINT fk_secciones_id_semestre FOREIGN KEY (id_semestre) REFERENCES semester(id),
  CONSTRAINT fk_secciones_id_profesor FOREIGN KEY (id_profesor) REFERENCES profesor(cedula),
  CONSTRAINT fk_secciones_id_coordinador FOREIGN KEY (id_coordinador) REFERENCES coordinador(cedula),
  CONSTRAINT fk_secciones_id_materia FOREIGN KEY (id_materia) REFERENCES materia(nrc)
);

-- -----------------------------------------------------
-- 4. TABLAS DEL SISTEMA DE CASOS
-- -----------------------------------------------------

CREATE TABLE casos (
  num_caso VARCHAR(50) PRIMARY KEY,
  fecha_recepcion DATE,
  cant_beneficiarios INTEGER,
  tramite VARCHAR(100),
  estatus VARCHAR(50),
  sintesis TEXT,
  id_centro INTEGER,
  id_ambito_legal INTEGER,
  id_solicitante VARCHAR(50),
  CONSTRAINT fk_casos_id_ambito_legal FOREIGN KEY (id_ambito_legal) REFERENCES ambitos_legales(id_ambito_legal),
  CONSTRAINT fk_casos_id_solicitante FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_casos_id_centro FOREIGN KEY (id_centro) REFERENCES centros(id_centro)
);

CREATE TABLE beneficiarios_casos (
  id_beneficiario VARCHAR(50),
  num_caso VARCHAR(50),
  tipo_beneficiario VARCHAR(50),
  parentesco VARCHAR(50),
  PRIMARY KEY (id_beneficiario, num_caso),
  CONSTRAINT fk_beneficiarios_casos_id FOREIGN KEY (id_beneficiario) REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_beneficiarios_casos_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE citas (
  fecha TIMESTAMP,
  num_caso VARCHAR(50),
  estado VARCHAR(50),
  orientacion TEXT,
  PRIMARY KEY (fecha, num_caso),
  CONSTRAINT fk_citas_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE citas_atendidas (
  num_caso VARCHAR(50),
  fecha TIMESTAMP,
  id_usuario VARCHAR(50),
  PRIMARY KEY (num_caso, fecha, id_usuario),
  CONSTRAINT fk_citas_atendidas_id_usuario FOREIGN KEY (id_usuario) REFERENCES personal(id_usuario),
  CONSTRAINT fk_citas_atendidas_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE pruebas (
  id_caso VARCHAR(50),
  id_prueba SERIAL,
  fecha DATE,
  documento VARCHAR(255),
  PRIMARY KEY (id_caso, id_prueba),
  CONSTRAINT fk_pruebas_id_caso FOREIGN KEY (id_caso) REFERENCES casos(num_caso)
);

CREATE TABLE acciones (
  id_accion SERIAL,
  num_caso VARCHAR(50),
  titulo VARCHAR(100),
  descripcion TEXT,
  id_usuario VARCHAR(50),
  f_registro DATE,
  f_ejecucion DATE,
  PRIMARY KEY (id_accion, num_caso),
  CONSTRAINT fk_acciones_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_acciones_id_usuario FOREIGN KEY (id_usuario) REFERENCES personal(id_usuario)
);

CREATE TABLE cambios_estatus (
  id_usuario VARCHAR(50),
  num_caso VARCHAR(50),
  fecha_cambio DATE,
  status_ant VARCHAR(50),
  PRIMARY KEY (id_usuario, num_caso, fecha_cambio),
  CONSTRAINT fk_cambios_estatus_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_cambios_estatus_id_usuario FOREIGN KEY (id_usuario) REFERENCES personal(id_usuario)
);

-- -----------------------------------------------------
-- 5. TABLAS ACADÉMICAS Y LEGALES
-- -----------------------------------------------------

CREATE TABLE estudiantes_inscritos (
  id_estudiante VARCHAR(50),
  id_materia INTEGER,
  id_semestre INTEGER,
  id_seccion INTEGER,
  PRIMARY KEY (id_estudiante, id_materia, id_semestre, id_seccion),
  CONSTRAINT fk_est_ins_seccion FOREIGN KEY (id_materia, id_seccion, id_semestre) REFERENCES secciones(id_materia, id_seccion, id_semestre),
  CONSTRAINT fk_est_ins_id_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante)
);

CREATE TABLE asignaciones (
  id_asignacion SERIAL PRIMARY KEY,
  id_estudiante VARCHAR(50),
  num_caso VARCHAR(50),
  id_semestre INTEGER,
  id_profesor VARCHAR(20),
  tipo_estudiante VARCHAR(50),
  fecha_asignacion DATE,
  fecha_fin_asignacion DATE,
  id_materia INTEGER,
  id_seccion INTEGER,
  CONSTRAINT fk_asignaciones_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_asignaciones_id_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante),
  CONSTRAINT fk_asignaciones_seccion FOREIGN KEY (id_materia, id_seccion, id_semestre) REFERENCES secciones(id_materia, id_seccion, id_semestre)
);

CREATE TABLE expediente_tribunales (
  num_expediente VARCHAR(50) PRIMARY KEY,
  fecha_creacion DATE,
  id_tribunal INTEGER,
  id_caso VARCHAR(50),
  CONSTRAINT fk_expediente_trib_id_tribunal FOREIGN KEY (id_tribunal) REFERENCES tribunales(id_tribunal),
  CONSTRAINT fk_expediente_trib_id_caso FOREIGN KEY (id_caso) REFERENCES casos(num_caso)
);

CREATE TABLE documentos_tribunales (
  num_expediente VARCHAR(50),
  folio_ini INTEGER,
  folio_fin INTEGER,
  fecha DATE,
  titulo VARCHAR(100),
  descripcion TEXT,
  PRIMARY KEY (num_expediente, folio_ini),
  CONSTRAINT fk_doc_tribunales_num_exp FOREIGN KEY (num_expediente) REFERENCES expediente_tribunales(num_expediente)
);

-- -----------------------------------------------------
-- 6. RESTRICCIONES DE DOMINIO (CHECK CONSTRAINTS)
-- -----------------------------------------------------

-- Usuarios (Solicitantes) y Personal
ALTER TABLE usuarios ADD CONSTRAINT chk_nacionalidad 
    CHECK (nacionalidad IN ('Venezolano', 'Extranjero'));

ALTER TABLE usuarios ADD CONSTRAINT chk_sexo 
    CHECK (sexo IN ('Masculino', 'Femenino'));

ALTER TABLE usuarios ADD CONSTRAINT chk_estado_civil 
    CHECK (estado_civil IN ('Soltero', 'Casado', 'Divorciado', 'Viudo'));

ALTER TABLE usuarios ADD CONSTRAINT chk_concubinato 
    CHECK (concubinato IN ('SI', 'NO'));

ALTER TABLE personal ADD CONSTRAINT chk_tipo_usuario 
    CHECK (tipo_usuario IN ('COORDINADOR', 'PROFESOR', 'ESTUDIANTE'));

-- Casos
ALTER TABLE casos ADD CONSTRAINT chk_estatus_caso 
    CHECK (estatus IN ('POR APROBAR', 'ABIERTO', 'CERRADO'));

ALTER TABLE casos ADD CONSTRAINT chk_tramite 
    CHECK (tramite IN ('ASESORIO', 'MEDIACIÓN Y CONCILIACIÓN', 'REDACCIÓN DE DOCUMENTO'));

-- Citas
ALTER TABLE citas ADD CONSTRAINT chk_estado_cita 
    CHECK (estado IN ('AGENDADA', 'PENDIENTE', 'CANCELADA', 'CONCRETADA'));

-- Viviendas
ALTER TABLE viviendas ADD CONSTRAINT chk_tipo_vivienda 
    CHECK (tipo IN ('Quinta/Casa Urb.', 'Apartamento', 'Bloque', 'Casa de Barrio', 'Casa rural', 'Rancho', 'Refugio', 'Otros'));

ALTER TABLE viviendas ADD CONSTRAINT chk_material_paredes 
    CHECK (material_paredes IN ('Cartón / Palma / Desechos', 'Bahareque', 'Bloque sin frizar', 'Bloque frizado'));

ALTER TABLE viviendas ADD CONSTRAINT chk_material_piso 
    CHECK (material_piso IN ('Tierra', 'Cemento', 'Cerámica', 'Granito / Parquet / Mármol'));

ALTER TABLE viviendas ADD CONSTRAINT chk_material_techo 
    CHECK (material_techo IN ('Madera / Cartón / Palma', 'Zinc / Acerolit', 'Platabanda / Tejas'));

ALTER TABLE viviendas ADD CONSTRAINT chk_aguas_negras 
    CHECK (aguas_negras IN ('Poceta a cloaca / Pozo séptico', 'Poceta sin conexión (tubo)', 'Excusado de hoyo o letrina', 'No tiene'));

ALTER TABLE viviendas ADD CONSTRAINT chk_servicio_agua 
    CHECK (servicio_agua IN ('Dentro de la vivienda', 'Fuera de la vivienda', 'No tiene servicio'));

ALTER TABLE viviendas ADD CONSTRAINT chk_servicio_aseo 
    CHECK (servicio_aseo IN ('Llega a la vivienda', 'No llega a la vivienda / Container', 'No tiene'));

-- Niveles y Trabajos
ALTER TABLE niveles_educativos ADD CONSTRAINT chk_nivel_educativo 
    CHECK (nivel IN ('Sin Nivel', 'Primaria', 'Básica', 'Media Diversificada', 'Técnico Medio', 'Técnico Superior', 'Universitaria'));

ALTER TABLE trabajos ADD CONSTRAINT chk_condicion_trabajo 
    CHECK (condicion_trabajo IN ('Patrono', 'Empleado', 'Obrero', 'Cuenta propia'));

ALTER TABLE trabajos ADD CONSTRAINT chk_condicion_actividad 
    CHECK (condicion_actividad IN ('Ama de casa', 'Estudiante', 'Pensionado/Jubilado', 'Otra'));

ALTER TABLE trabajos ADD CONSTRAINT chk_trabaja_bool 
    CHECK (trabaja IN ('SI', 'NO'));

ALTER TABLE trabajos ADD CONSTRAINT chk_buscando_bool 
    CHECK (esta_buscando IN ('SI', 'NO'));

-- Beneficiarios
ALTER TABLE beneficiarios_casos ADD CONSTRAINT chk_tipo_beneficiario 
    CHECK (tipo_beneficiario IN ('Directo', 'Indirecto'));