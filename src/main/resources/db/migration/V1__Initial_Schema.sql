-- ==========================================================
-- 0. LIMPIEZA Y ESQUEMA
-- ==========================================================
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- ==========================================================
-- 1. TABLAS CATÁLOGOS E INDEPENDIENTES
-- ==========================================================

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
  material_paredes VARCHAR(100),
  aguas_negras VARCHAR(100),
  servicio_agua VARCHAR(100),
  material_techo VARCHAR(100),
  material_piso VARCHAR(100),
  servicio_aseo VARCHAR(100)
);

CREATE TABLE semester (
  id SERIAL PRIMARY KEY,
  termino VARCHAR(10),       -- Ahora guardará: '15', '20', '25', '30'
  periodo VARCHAR(10),       -- Año principal (Ej: '2024')
  descripcion VARCHAR(100),  -- NUEVO: Ej 'Semestre Sep/Ene 24-25'
  fecha_ini DATE,
  fecha_fin DATE
);

CREATE TABLE materia (
  nrc INTEGER PRIMARY KEY,
  nombre_materia VARCHAR(100)
);

-- ==========================================================
-- 2. JERARQUÍA DE PERSONAL (NORMALIZADA)
-- ==========================================================

-- TABLA MAESTRA (Padre)
CREATE TABLE personal (
  id_usuario VARCHAR(50) PRIMARY KEY,
  cedula VARCHAR(20) NOT NULL UNIQUE, -- Movido aquí (Dato único real)
  nombre VARCHAR(150) NOT NULL,
  sexo VARCHAR(20),
  email VARCHAR(100) NOT NULL UNIQUE,
  username VARCHAR(50) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  estatus VARCHAR(20) DEFAULT 'ACTIVO', -- Estatus general de acceso
  tipo_usuario VARCHAR(20) -- COORDINADOR, PROFESOR, ESTUDIANTE
);

-- TABLAS DE ROLES (Hijas) - Relación 1:1 por id_usuario

CREATE TABLE coordinador (
  id_usuario VARCHAR(50) PRIMARY KEY,
  estatus VARCHAR(20), -- Estatus específico del rol
  CONSTRAINT fk_coord_personal FOREIGN KEY (id_usuario) 
    REFERENCES personal(id_usuario) ON DELETE CASCADE
);

CREATE TABLE profesor (
  id_usuario VARCHAR(50) PRIMARY KEY,
  estatus VARCHAR(20), -- Estatus específico (ej: SABATICO)
  especialidad VARCHAR(100),
  CONSTRAINT fk_prof_personal FOREIGN KEY (id_usuario) 
    REFERENCES personal(id_usuario) ON DELETE CASCADE
);

CREATE TABLE estudiante (
  id_usuario VARCHAR(50) PRIMARY KEY,
  estatus VARCHAR(20), -- Estatus académico
  culminado BOOLEAN DEFAULT FALSE,
  tipo VARCHAR(50), -- Regular/Voluntario
  CONSTRAINT fk_est_personal FOREIGN KEY (id_usuario) 
    REFERENCES personal(id_usuario) ON DELETE CASCADE
);

-- ==========================================================
-- 3. GEOGRAFÍA (Niveles Dependientes)
-- ==========================================================

CREATE TABLE municipios (
  id_municipio SERIAL PRIMARY KEY,
  id_estado INTEGER NOT NULL,
  nombre_municipio VARCHAR(100),
  CONSTRAINT fk_municipios_id_estado 
    FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

CREATE TABLE parroquia (
  id_parroquia SERIAL PRIMARY KEY,
  id_municipio INTEGER NOT NULL,
  nombre_parroquia VARCHAR(100),
  CONSTRAINT fk_parroquia_id_municipio 
    FOREIGN KEY (id_municipio) REFERENCES municipios(id_municipio)
);

CREATE TABLE centros (
  id_centro SERIAL PRIMARY KEY,
  nombre VARCHAR(100),
  abreviatura VARCHAR(10), -- Nueva columna solicitada (Ej: GY, CB)
  id_parroquia INTEGER,
  
  CONSTRAINT fk_centros_id_parroquia 
    FOREIGN KEY (id_parroquia) REFERENCES parroquia(id_parroquia)
);
-- ==========================================================
-- 4. USUARIOS EXTERNOS (CLIENTES)
-- ==========================================================

CREATE TABLE usuarios (
  id_usuario VARCHAR(50) PRIMARY KEY, -- Cédula o ID del cliente
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
  
  CONSTRAINT fk_usuarios_id_nivel_edu FOREIGN KEY (id_nivel_edu) REFERENCES niveles_educativos(id_nivel_edu),
  CONSTRAINT fk_usuarios_id_vivienda FOREIGN KEY (id_vivienda) REFERENCES viviendas(id_vivienda),
  CONSTRAINT fk_usuarios_id_trabajo FOREIGN KEY (id_trabajo) REFERENCES trabajos(id_trabajo),
  CONSTRAINT fk_usuarios_id_parroquia FOREIGN KEY (id_parroquia) REFERENCES parroquia(id_parroquia)
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
  CONSTRAINT fk_familias_id_solicitante FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_familias_id_nivel_edu FOREIGN KEY (id_nivel_edu) REFERENCES niveles_educativos(id_nivel_edu)
);

CREATE TABLE telefonos (
  id_solicitante VARCHAR(50),
  telefono VARCHAR(20),
  PRIMARY KEY (id_solicitante, telefono),
  CONSTRAINT fk_telefonos_id_solicitante FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario)
);

CREATE TABLE electrodomesticos_solicitantes (
  id_solicitante VARCHAR(50),
  id_electrodomestico INTEGER,
  PRIMARY KEY (id_solicitante, id_electrodomestico),
  CONSTRAINT fk_electro_sol_id_electro FOREIGN KEY (id_electrodomestico) REFERENCES electrodomesticos(id_electrodomestico),
  CONSTRAINT fk_electro_sol_id_solicitante FOREIGN KEY (id_solicitante) REFERENCES usuarios(id_usuario)
);

-- ==========================================================
-- 5. ACADEMIA (Secciones e Inscripciones)
-- ==========================================================

CREATE TABLE secciones (
  id_materia INTEGER,
  id_seccion INTEGER,
  id_semestre INTEGER,
  id_profesor VARCHAR(50),    -- Ahora referencia al ID de usuario del profesor
  id_coordinador VARCHAR(50), -- Ahora referencia al ID de usuario del coordinador
  
  PRIMARY KEY (id_materia, id_seccion, id_semestre),
  
  CONSTRAINT fk_secciones_id_semestre FOREIGN KEY (id_semestre) REFERENCES semester(id),
  CONSTRAINT fk_secciones_id_materia FOREIGN KEY (id_materia) REFERENCES materia(nrc),
  CONSTRAINT fk_secciones_id_profesor FOREIGN KEY (id_profesor) REFERENCES profesor(id_usuario),
  CONSTRAINT fk_secciones_id_coordinador FOREIGN KEY (id_coordinador) REFERENCES coordinador(id_usuario)
);

CREATE TABLE estudiantes_inscritos (
  id_estudiante VARCHAR(50), -- Referencia a estudiante(id_usuario)
  id_materia INTEGER,
  id_semestre INTEGER,
  id_seccion INTEGER,
  
  PRIMARY KEY (id_estudiante, id_materia, id_semestre, id_seccion),
  
  CONSTRAINT fk_est_ins_seccion FOREIGN KEY (id_materia, id_seccion, id_semestre) 
    REFERENCES secciones(id_materia, id_seccion, id_semestre),
  CONSTRAINT fk_est_ins_id_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_usuario)
);

-- ==========================================================
-- 6. SISTEMA DE CASOS
-- ==========================================================

CREATE TABLE casos (
  num_caso VARCHAR(50) PRIMARY KEY, --Ej: GY-2024-20-0002 
  fecha_recepcion DATE,
  cant_beneficiarios INTEGER,
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

CREATE TABLE asignaciones (
  id_asignacion SERIAL PRIMARY KEY,
  id_estudiante VARCHAR(50),
  num_caso VARCHAR(50),
  id_semestre INTEGER,
  id_profesor VARCHAR(50),
  tipo_estudiante VARCHAR(50),
  fecha_asignacion DATE,
  fecha_fin_asignacion DATE,
  id_materia INTEGER,
  id_seccion INTEGER,
  
  CONSTRAINT fk_asignaciones_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_asignaciones_id_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_usuario),
  CONSTRAINT fk_asignaciones_id_profesor FOREIGN KEY (id_profesor) REFERENCES profesor(id_usuario),
  CONSTRAINT fk_asignaciones_seccion FOREIGN KEY (id_materia, id_seccion, id_semestre) 
    REFERENCES secciones(id_materia, id_seccion, id_semestre)
);

CREATE TABLE citas (
  fecha TIMESTAMP,
  num_caso VARCHAR(50),
  estado VARCHAR(50),
  orientacion TEXT,
  tramite VARCHAR(100),
  
  PRIMARY KEY (fecha, num_caso),
  
  CONSTRAINT fk_citas_num_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);
CREATE TABLE citas_atendidas (
  num_caso VARCHAR(50),
  fecha TIMESTAMP,
  id_usuario VARCHAR(50), -- Cualquier personal puede atender
  
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

CREATE TABLE cambios_estatus_casos (
  id_usuario VARCHAR(50),
  num_caso VARCHAR(50),
  fecha_cambio TIMESTAMP, -- Ahora incluye hora (DATETIME)
  status_ant VARCHAR(50),
  
  -- La Primary Key incluye la fecha/hora para permitir múltiples cambios
  PRIMARY KEY (id_usuario, num_caso, fecha_cambio),
  
  CONSTRAINT fk_cambios_estatus_num_caso FOREIGN KEY (num_caso) 
    REFERENCES casos(num_caso) ON DELETE CASCADE,
  CONSTRAINT fk_cambios_estatus_id_usuario FOREIGN KEY (id_usuario) 
    REFERENCES personal(id_usuario) ON DELETE CASCADE
);  

-- ==========================================================
-- 7. LEGAL (Expedientes)
-- ==========================================================

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

-- ==========================================================
-- 8. RESTRICCIONES DE DOMINIO (CHECK CONSTRAINTS)
-- ==========================================================

-- PERSONAL
ALTER TABLE personal ADD CONSTRAINT chk_tipo_usuario 
    CHECK (tipo_usuario IN ('COORDINADOR', 'PROFESOR', 'ESTUDIANTE'));
ALTER TABLE personal ADD CONSTRAINT chk_sexo_personal 
    CHECK (sexo IN ('Masculino', 'Femenino'));

ALTER TABLE coordinador ADD CONSTRAINT chk_estatus_coord 
    CHECK (estatus IN ('ACTIVO', 'INACTIVO'));

ALTER TABLE profesor ADD CONSTRAINT chk_estatus_prof 
    CHECK (estatus IN ('ACTIVO', 'INACTIVO', 'SABATICO'));

ALTER TABLE estudiante ADD CONSTRAINT chk_estatus_est 
    CHECK (estatus IN ('ACTIVO', 'RETIRADO', 'GRADUADO'));
ALTER TABLE estudiante ADD CONSTRAINT chk_tipo_est 
    CHECK (tipo IN ('Regular', 'Voluntario'));

-- USUARIOS EXTERNOS
ALTER TABLE usuarios ADD CONSTRAINT chk_nacionalidad 
    CHECK (nacionalidad IN ('Venezolano', 'Extranjero'));
ALTER TABLE usuarios ADD CONSTRAINT chk_sexo_usuario 
    CHECK (sexo IN ('Masculino', 'Femenino'));
ALTER TABLE usuarios ADD CONSTRAINT chk_estado_civil 
    CHECK (estado_civil IN ('Soltero', 'Casado', 'Divorciado', 'Viudo'));
ALTER TABLE usuarios ADD CONSTRAINT chk_concubinato 
    CHECK (concubinato IN ('SI', 'NO'));

-- CASOS Y CITAS
ALTER TABLE casos ADD CONSTRAINT chk_estatus_caso 
    CHECK (estatus IN ('POR APROBAR', 'ABIERTO', 'CERRADO'));
ALTER TABLE citas ADD CONSTRAINT chk_estado_cita 
    CHECK (estado IN ('AGENDADA', 'PENDIENTE', 'CANCELADA', 'CONCRETADA'));

-- SOCIOECONÓMICO
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

ALTER TABLE beneficiarios_casos ADD CONSTRAINT chk_tipo_beneficiario 
    CHECK (tipo_beneficiario IN ('Directo', 'Indirecto'));

    -- NUEVA RESTRICCIÓN EN CITAS
ALTER TABLE citas ADD CONSTRAINT chk_tramite 
    CHECK (tramite IN ('ASESORIA', 'MEDIACIÓN Y CONCILIACIÓN', 'REDACCIÓN DE DOCUMENTO'));
