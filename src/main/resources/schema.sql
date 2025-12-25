-- ================================================================
-- SECCIÓN 1: CREACIÓN DE TABLAS (DDL) - VERSIÓN CORREGIDA
-- ================================================================

-- 1.1 CATÁLOGOS GLOBALES Y GEOGRAFÍA
-- ----------------------------------------------------------------
CREATE TABLE estados (
  id_estado INTEGER PRIMARY KEY,
  estado VARCHAR(100) NOT NULL
);

CREATE TABLE municipios (
  id_municipio INTEGER PRIMARY KEY,
  municipio VARCHAR(100) NOT NULL,
  id_estado INTEGER,
  CONSTRAINT fk_mun_est FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

CREATE TABLE parroquias (
  id_parroquia INTEGER PRIMARY KEY,
  parroquia VARCHAR(100) NOT NULL,
  id_municipio INTEGER,
  CONSTRAINT fk_parr_mun FOREIGN KEY (id_municipio) REFERENCES municipios(id_municipio)
);

CREATE TABLE centros (
  id_centro SERIAL PRIMARY KEY,
  nombre VARCHAR(150),
  abreviatura VARCHAR(20),
  id_parroquia INTEGER,
  CONSTRAINT fk_cen_parr FOREIGN KEY (id_parroquia) REFERENCES parroquias(id_parroquia)
);

CREATE TABLE semestre (
  termino VARCHAR(20) PRIMARY KEY, 
  nombre VARCHAR(100),
  fecha_inicio DATE,
  fecha_fin DATE
  -- Restricción de formato: 4 dígitos, guion, 2 dígitos
  CONSTRAINT chk_formato_termino CHECK (termino ~ '^[0-9]{4}-[0-9]{2}$')
);

CREATE TABLE tribunal (
  id_tribunal SERIAL PRIMARY KEY,    -- Ahora es autoincremental
  nombre_tribunal VARCHAR(150),
  materia VARCHAR(100),
  instancia VARCHAR(100),
  ubicacion VARCHAR(200),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_tribunal CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

-- 1.2 JERARQUÍA LEGAL
-- ----------------------------------------------------------------
CREATE TABLE materia_ambito_legal (
  cod_mat_amb_legal INTEGER PRIMARY KEY,
  mat_amb_legal VARCHAR(100)
);

CREATE TABLE categoria_ambito_legal (
  cod_cat_amb_legal INTEGER PRIMARY KEY,
  cod_mat_amb_legal INTEGER,
  cat_amb_legal VARCHAR(100),
  CONSTRAINT fk_cat_mat FOREIGN KEY (cod_mat_amb_legal) REFERENCES materia_ambito_legal(cod_mat_amb_legal)
);

CREATE TABLE subcategoria_ambito_legal (
  cod_sub_amb_legal INTEGER PRIMARY KEY,
  cod_cat_amb_legal INTEGER,
  nombre_subcategoria VARCHAR(100),
  CONSTRAINT fk_sub_cat FOREIGN KEY (cod_cat_amb_legal) REFERENCES categoria_ambito_legal(cod_cat_amb_legal)
);

CREATE TABLE ambito_legal (
  cod_amb_legal SERIAL PRIMARY KEY,
  cod_sub_amb_legal INTEGER,
  amb_legal VARCHAR(100),
  CONSTRAINT fk_amb_sub FOREIGN KEY (cod_sub_amb_legal) REFERENCES subcategoria_ambito_legal(cod_sub_amb_legal)
);

-- 1.3 CATÁLOGOS SOCIOECONÓMICOS (ESTANDARIZADOS A SERIAL)
-- ----------------------------------------------------------------

CREATE TABLE niveles_educativos (
  id_nivel SERIAL PRIMARY KEY,    -- Antes INTEGER
  nivel VARCHAR(100),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_nivel CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE condicion_laboral (
  id_condicion SERIAL PRIMARY KEY, -- Antes INTEGER
  condicion VARCHAR(100),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_cond CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE condicion_actividad (
  id_condicion_actividad SERIAL PRIMARY KEY, -- Antes INTEGER
  nombre_actividad VARCHAR(100),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_actividad CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE estado_civil (
  id_estado_civil SERIAL PRIMARY KEY, -- Antes INTEGER
  descripcion VARCHAR(50), 
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_civil CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

-- NOTA: Las tablas de vivienda las dejé igual por ahora porque usan 
-- llaves compuestas y dependen de IDs específicos para relacionarse entre sí.
CREATE TABLE tipos_categorias_viviendas (
  id_tipo_cat INTEGER PRIMARY KEY,
  tipo_categoria VARCHAR(100)
);

CREATE TABLE categorias_de_vivienda (
  id_cat_vivienda INTEGER,
  id_tipo_cat INTEGER,
  descripcion VARCHAR(150),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id_cat_vivienda, id_tipo_cat),
  CONSTRAINT fk_cat_viv_tipo FOREIGN KEY (id_tipo_cat) REFERENCES tipos_categorias_viviendas(id_tipo_cat),
  CONSTRAINT chk_estatus_viv CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

-- 1.4 USUARIOS Y ACTORES
-- ----------------------------------------------------------------
CREATE TABLE usuarios (
  username VARCHAR(50) PRIMARY KEY,
  cedula VARCHAR(20),
  contrasena VARCHAR(255),
  nombre VARCHAR(150),
  email VARCHAR(150),
  status VARCHAR(20) DEFAULT 'ACTIVO',
  tipo VARCHAR(20),
  CONSTRAINT uq_usuarios_cedula UNIQUE (cedula),
  CONSTRAINT chk_usuarios_tipo CHECK (tipo IN ('COORDINADOR', 'PROFESOR', 'ESTUDIANTE', 'ADMIN'))
);

CREATE TABLE coordinadores (
  username VARCHAR(50) PRIMARY KEY,
  CONSTRAINT fk_coord_user FOREIGN KEY (username) REFERENCES usuarios(username)
);

CREATE TABLE profesores (
  username VARCHAR(50) PRIMARY KEY,
  termino VARCHAR(20),
  CONSTRAINT fk_prof_user FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_prof_sem FOREIGN KEY (termino) REFERENCES semestre(termino)
);

CREATE TABLE estudiantes (
  username VARCHAR(50),
  termino VARCHAR(20),
  tipo_de_estudiante VARCHAR(50),
  nrc INTEGER,
  PRIMARY KEY (username, termino),
  CONSTRAINT fk_est_user FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_est_sem FOREIGN KEY (termino) REFERENCES semestre(termino)
);

-- 1.5 FAMILIAS Y SOLICITANTES
-- ----------------------------------------------------------------
CREATE TABLE familias (
  cedula VARCHAR(20) PRIMARY KEY,
  cant_personas INTEGER,
  cant_estudiando INTEGER,
  ingreso_mes DECIMAL(12,2),
  jefe_familia BOOLEAN DEFAULT TRUE,
  cant_sin_trabajo INTEGER,
  cant_ninos INTEGER,
  cant_trabaja INTEGER,
  id_nivel_edu_jefe INTEGER,
  tiempo_estudio VARCHAR(50),
  CONSTRAINT fk_fam_niv FOREIGN KEY (id_nivel_edu_jefe) REFERENCES niveles_educativos(id_nivel)
);

CREATE TABLE solicitantes (
  cedula VARCHAR(20) PRIMARY KEY,
  nombre VARCHAR(150),
  nacionalidad VARCHAR(50),
  sexo VARCHAR(20),
  email VARCHAR(150),
  concubinato VARCHAR(10),
  id_estado_civil INTEGER,
  telf_celular VARCHAR(20),
  telf_casa VARCHAR(20),
  f_nacimiento DATE,
  edad INTEGER,
  
  id_condicion INTEGER,          -- FK a condicion_laboral
  id_condicion_actividad INTEGER, -- FK ACTUALIZADA a condicion_actividad
  id_nivel INTEGER,
  tiempo_estudio VARCHAR(50),
  
  CONSTRAINT chk_sol_sexo CHECK (sexo IN ('Masculino', 'Femenino')),
  CONSTRAINT chk_sol_nacionalidad CHECK (nacionalidad IN ('Venezolano', 'Extranjero')),
  CONSTRAINT chk_sol_concubinato CHECK (concubinato IN ('SI', 'NO')),
  
  CONSTRAINT fk_sol_fam FOREIGN KEY (cedula) REFERENCES familias(cedula),
  CONSTRAINT fk_sol_cond FOREIGN KEY (id_condicion) REFERENCES condicion_laboral(id_condicion),
  CONSTRAINT fk_sol_niv FOREIGN KEY (id_nivel) REFERENCES niveles_educativos(id_nivel),
  CONSTRAINT fk_sol_civil FOREIGN KEY (id_estado_civil) REFERENCES estado_civil(id_estado_civil),
  
  -- NUEVA RELACIÓN ACTUALIZADA
  CONSTRAINT fk_sol_actividad FOREIGN KEY (id_condicion_actividad) REFERENCES condicion_actividad(id_condicion_actividad)
);

-- 1.6 VIVIENDA
-- ----------------------------------------------------------------
CREATE TABLE viviendas (
  cedula VARCHAR(20) PRIMARY KEY,
  cant_banos INTEGER,
  cant_habit INTEGER,
  CONSTRAINT fk_viv_sol FOREIGN KEY (cedula) REFERENCES solicitantes(cedula)
);

CREATE TABLE caracteristicas_viviendas (
  cedula VARCHAR(20),
  id_tipo_cat INTEGER,
  id_cat_vivienda INTEGER,
  PRIMARY KEY (cedula, id_tipo_cat, id_cat_vivienda),
  CONSTRAINT fk_carac_viv FOREIGN KEY (cedula) REFERENCES viviendas(cedula),
  CONSTRAINT fk_carac_cat FOREIGN KEY (id_cat_vivienda, id_tipo_cat) 
  REFERENCES categorias_de_vivienda(id_cat_vivienda, id_tipo_cat)
);

-- 1.7 CASOS
-- ----------------------------------------------------------------
CREATE TABLE casos (
  num_caso VARCHAR(50) PRIMARY KEY,
  fecha_recepcion DATE,
  sintesis TEXT,
  tramite VARCHAR(100),
  cant_beneficiarios INTEGER,
  estatus VARCHAR(50),
  cod_caso_tribunal VARCHAR(50),
  fecha_res_caso_tri DATE,
  fecha_crea_caso_tri DATE,
  
  id_tribunal INTEGER,
  termino VARCHAR(20),
  id_centro INTEGER,
  
  cedula VARCHAR(20),
  username VARCHAR(50),
  com_amb_legal INTEGER,
  
  CONSTRAINT fk_caso_amb FOREIGN KEY (com_amb_legal) REFERENCES ambito_legal(cod_amb_legal),
  CONSTRAINT fk_caso_sem FOREIGN KEY (termino) REFERENCES semestre(termino),
  CONSTRAINT fk_caso_cen FOREIGN KEY (id_centro) REFERENCES centros(id_centro),
  CONSTRAINT fk_caso_tri FOREIGN KEY (id_tribunal) REFERENCES tribunal(id_tribunal),
  CONSTRAINT fk_caso_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_caso_sol FOREIGN KEY (cedula) REFERENCES solicitantes(cedula),
  
  CONSTRAINT chk_caso_estatus CHECK (estatus IN ('ABIERTO', 'EN TRÁMITE', 'EN PAUSA', 'CERRADO')),
  CONSTRAINT chk_caso_tramite CHECK (tramite IN ('ASESORÍA', 'CONCILIACIÓN Y MEDIACIÓN', 'REDACCIÓN DE DOC.'))
);
CREATE INDEX idx_casos_mix ON casos(id_centro, termino, username, cedula);

-- 1.8 ASIGNACIÓN Y SUPERVISIÓN
-- ----------------------------------------------------------------
CREATE TABLE casos_asignados (
  num_caso VARCHAR(50),
  username VARCHAR(50),
  termino VARCHAR(20),
  PRIMARY KEY (num_caso, username, termino),
  CONSTRAINT fk_asig_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_asig_est FOREIGN KEY (username, termino) REFERENCES estudiantes(username, termino)
);

CREATE TABLE casos_supervisados (
  num_caso VARCHAR(50),
  username VARCHAR(50),
  termino VARCHAR(20),
  PRIMARY KEY (num_caso, username, termino),
  CONSTRAINT fk_sup_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_sup_prof FOREIGN KEY (username) REFERENCES profesores(username),
  CONSTRAINT fk_sup_sem FOREIGN KEY (termino) REFERENCES semestre(termino)
);

-- 1.9 DETALLES OPERATIVOS (Tablas hijas de casos)
-- ----------------------------------------------------------------
CREATE TABLE estatus_por_caso (
  id_est_caso INTEGER,
  num_caso VARCHAR(50),
  fecha_cambio DATE,
  estatus VARCHAR(50),
  observacion VARCHAR(200),
  PRIMARY KEY (num_caso, id_est_caso),
  CONSTRAINT fk_est_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE accion (
  id_accion INTEGER,
  num_caso VARCHAR(50),
  titulo VARCHAR(150),
  descripcion TEXT,
  fecha_registro DATE,
  fecha_ejecucion DATE,
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_accion),
  CONSTRAINT fk_acc_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_acc_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE acciones_ejecutadas (
  id_accion_ejecutada INTEGER,
  id_accion INTEGER, 
  num_caso VARCHAR(50),
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_accion_ejecutada),
  CONSTRAINT fk_ejec_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_ejec_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_ejec_acc FOREIGN KEY (num_caso, id_accion) REFERENCES accion(num_caso, id_accion)
);

CREATE TABLE encuentros (
  id_encuentros INTEGER,
  num_caso VARCHAR(50),
  fecha_atencion DATE,
  fecha_proxima DATE,
  orientacion TEXT,
  observacion TEXT,
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_encuentros),
  CONSTRAINT fk_enc_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_enc_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE encuentros_atendidos (
  id_encuentro_atendido INTEGER,
  id_encuentro INTEGER,
  num_caso VARCHAR(50),
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_encuentro_atendido),
  CONSTRAINT fk_aten_enc FOREIGN KEY (num_caso, id_encuentro) REFERENCES encuentros(num_caso, id_encuentros),
  CONSTRAINT fk_aten_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_aten_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE documentos (
  id_documento INTEGER,
  num_caso VARCHAR(50),
  fecha_registro DATE,
  folio_ini INTEGER,
  folio_fin INTEGER,
  titulo VARCHAR(150),
  observacion TEXT,
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_documento),
  CONSTRAINT fk_doc_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE pruebas (
  id_prueba INTEGER,
  num_caso VARCHAR(50),
  fecha DATE,
  documento VARCHAR(150),
  observacion TEXT,
  titulo VARCHAR(150),
  PRIMARY KEY (num_caso, id_prueba),
  CONSTRAINT fk_pru_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

-- ================================================================
-- SECCIÓN 2: INSERCIÓN DE DATOS (DML)
-- ================================================================

-- 2.1 MATERIAS
INSERT INTO materia_ambito_legal (cod_mat_amb_legal, mat_amb_legal) VALUES
(1, 'Civil'), (2, 'Penal'), (3, 'Laboral'), (4, 'Mercantil'), (5, 'Administrativa'), (6, 'Otros');

-- 2.2 CATEGORÍAS
INSERT INTO categoria_ambito_legal (cod_cat_amb_legal, cat_amb_legal, cod_mat_amb_legal) VALUES
(1, 'Sin Categoría', 1), (2, 'Familia', 1),
(3, 'Sin Categoría', 2), (4, 'Sin Categoría', 3), (5, 'Sin Categoría', 4),
(6, 'Sin Categoría', 5), (7, 'Sin Categoría', 6);

-- 2.3 SUBCATEGORÍAS
INSERT INTO subcategoria_ambito_legal (cod_sub_amb_legal, nombre_subcategoria, cod_cat_amb_legal) VALUES
(1, 'Personas', 1), (2, 'Bienes', 1), (3, 'Contratos', 1), (4, 'Sucesiones', 1),
(5, 'Tribunales Ordinarios', 2), (6, 'Tribunales Protección NNA', 2),
(7, 'Sin Subcategoría', 3), (8, 'Sin Subcategoría', 4), (9, 'Sin Subcategoría', 5),
(10, 'Sin Subcategoría', 6), (11, 'Sin Subcategoría', 7);

-- 2.4 ÁMBITOS LEGALES (85 ITEMS)
INSERT INTO ambito_legal (amb_legal, cod_sub_amb_legal) VALUES
-- CIVIL > PERSONAS
('Rectificación de Actas', 1), ('Inserción de Actas', 1), ('Solicitud de Naturalización', 1),
('Justificativo de Soltería', 1), ('Justificativo de Concubinato', 1), ('Invitación al país', 1),
('Justific. de Dependencia Económica / Pobreza', 1), ('Declaración Jurada de No Poseer Vivienda', 1),
('Declaración Jurada de Ingresos', 1), ('Concubinato Postmortem', 1), ('Declaración Jurada', 1), ('Justificativo de Testigos', 1),
-- CIVIL > BIENES
('Título Supletorio', 2), ('Compra venta bienhechuría', 2), ('Partición de comunidad ordinaria', 2),
('Propiedad Horizontal', 2), ('Cierre de Titularidad', 2), ('Aclaratoria', 2),
-- CIVIL > CONTRATOS
('Arrendamiento/Comodato', 3), ('Compra-venta de bienes inmuebles', 3), ('Compra-venta bienes muebles (vehículo)', 3),
('Opción de Compra Venta', 3), ('Finiquito de compra venta', 3), ('Asociaciones / Fundaciones', 3),
('Cooperativas', 3), ('Poder', 3), ('Cesión de derechos', 3), ('Cobro de Bolívares', 3),
('Constitución y liquidación de hipoteca', 3), ('Servicios/obras', 3),
-- CIVIL > SUCESIONES
('Cesión de derechos sucesorales', 4), ('Justificativo Únicos y Universales herederos', 4),
('Testamento', 4), ('Declaración Sucesoral', 4), ('Partición de comunidad hereditaria', 4),
-- FAMILIA > TRIB. ORDINARIOS
('Divorcio por separación de hecho (185-A)', 5), ('Separación de cuerpos (189)', 5),
('Conversión de separación en divorcio', 5), ('Divorcio contencioso', 5),
('Partición de comunidad conyugal', 5), ('Partición de comunidad concubinaria', 5),
('Capitulaciones matrimoniales', 5), ('Divorcio Causal No Taxativa Sentencias', 5),
-- FAMILIA > TRIB. PROTECCIÓN NNA
('Divorcio por separación de hecho (185-A) NNA', 6), ('Separación de cuerpos (189) NNA', 6),
('Conversión de separación en divorcio NNA', 6), ('Divorcio contencioso NNA', 6),
('Reconocimiento Voluntario Hijo', 6), ('Colocación familiar', 6), ('Curatela', 6),
('Medidas de protección (Identidad, salud, educación, otros)', 6), ('Autorización para Viajar', 6),
('Autorización para Vender', 6), ('Autorización para Trabajar', 6),
('Obligación de Manutención/Convivencia Familiar', 6), ('Rectificación de Actas (NNA)', 6),
('Inserción de Actas (NNA)', 6), ('Carga Familiar', 6), ('Cambio de Residencia', 6),
('Ejercicio Unilateral de Patria Potestad', 6), ('Divorcio causal No Taxativa Sentencias (NNA)', 6), ('Tutela', 6),
-- PENAL
('Delitos Contra la Propiedad (Robo, Hurto)', 7), ('Contra las Personas (homicidio, lesiones)', 7),
('Contra las Buenas Costumbres (Violación)', 7), ('Delitos contra el Honor', 7), ('Violencia Doméstica', 7),
-- LABORAL
('Calificación de Despido', 8), ('Prestaciones Sociales', 8), ('Contratos de Trabajo', 8),
('Accidentes de Trabajo', 8), ('Incapacidad laboral', 8), ('Terminación de Relación Laboral', 8),
-- MERCANTIL
('Firma Personal', 9), ('Constitución de Compañías', 9), ('Actas de Asamblea', 9),
('Compra Venta de Fondo de Comercio / Acciones', 9), ('Letras de Cambio', 9),
-- ADMINISTRATIVA
('Recursos Administrativos', 10),
-- OTROS
('Convivencia Ciudadana', 11), ('Derechos Humanos', 11), ('Tránsito', 11), ('Otros', 11), ('Diligencias Seguimiento', 11);



-- 2.6 TIPOS Y CATEGORÍAS DE VIVIENDA
INSERT INTO tipos_categorias_viviendas (id_tipo_cat, tipo_categoria) VALUES
(1, 'Tipo de Vivienda'), (2, 'Material del Piso'), (3, 'Material de las Paredes'),
(4, 'Material del Techo'), (5, 'Servicio de Agua Potable'), (6, 'Eliminación de Excretas'),
(7, 'Servicio de Aseo Urbano'), (8, 'Artefactos y Bienes');

INSERT INTO categorias_de_vivienda (id_tipo_cat, id_cat_vivienda, descripcion) VALUES
(1, 1, 'Quinta / Casa Urb.'), (1, 2, 'Apartamento'), (1, 3, 'Bloque'), (1, 4, 'Casa de Barrio'), (1, 5, 'Rancho'), (1, 6, 'Casa Rural'), (1, 7, 'Refugio'), (1, 8, 'Otros'),
(2, 1, 'Tierra'), (2, 2, 'Cemento'), (2, 3, 'Cerámica'), (2, 4, 'Granito / Parquet'),
(3, 1, 'Cartón / Desechos'), (3, 2, 'Bahareque'), (3, 3, 'Bloque sin frizar'), (3, 4, 'Bloque frizado'),
(4, 1, 'Madera / Cartón'), (4, 2, 'Zinc / Acerolit'), (4, 3, 'Platabanda / Tejas'),
(5, 1, 'Dentro de la vivienda'), (5, 2, 'Fuera de la vivienda'), (5, 3, 'No tiene servicio'),
(6, 1, 'Poceta a cloaca'), (6, 2, 'Poceta sin conexión'), (6, 3, 'Excusado / Letrina'), (6, 4, 'No tiene'),
(7, 1, 'Llega a la vivienda'), (7, 2, 'No llega / Container'), (7, 3, 'No tiene'),
(8, 1, 'Nevera'), (8, 2, 'Lavadora'), (8, 3, 'Computadora'), (8, 4, 'Cable Satelital'), (8, 5, 'Internet'), (8, 6, 'Carro'), (8, 7, 'Moto');
-- ============================================
-- INSERT DE LOS CATALODOS DE LOS ESTADOS
-- =============================================
INSERT INTO estados (id_estado, estado) VALUES (1, 'Amazonas');
INSERT INTO estados (id_estado, estado) VALUES (2, 'Anzoátegui');
INSERT INTO estados (id_estado, estado) VALUES (3, 'Apure');
INSERT INTO estados (id_estado, estado) VALUES (4, 'Aragua');
INSERT INTO estados (id_estado, estado) VALUES (5, 'Barinas');
INSERT INTO estados (id_estado, estado) VALUES (6, 'Bolívar');
INSERT INTO estados (id_estado, estado) VALUES (7, 'Carabobo');
INSERT INTO estados (id_estado, estado) VALUES (8, 'Cojedes');
INSERT INTO estados (id_estado, estado) VALUES (9, 'Delta Amacuro');
INSERT INTO estados (id_estado, estado) VALUES (10, 'Falcón');
INSERT INTO estados (id_estado, estado) VALUES (11, 'Guárico');
INSERT INTO estados (id_estado, estado) VALUES (12, 'Lara');
INSERT INTO estados (id_estado, estado) VALUES (13, 'Mérida');
INSERT INTO estados (id_estado, estado) VALUES (14, 'Miranda');
INSERT INTO estados (id_estado, estado) VALUES (15, 'Monagas');
INSERT INTO estados (id_estado, estado) VALUES (16, 'Nueva Esparta');
INSERT INTO estados (id_estado, estado) VALUES (17, 'Portuguesa');
INSERT INTO estados (id_estado, estado) VALUES (18, 'Sucre');
INSERT INTO estados (id_estado, estado) VALUES (19, 'Táchira');
INSERT INTO estados (id_estado, estado) VALUES (20, 'Trujillo');
INSERT INTO estados (id_estado, estado) VALUES (21, 'La Guaira');
INSERT INTO estados (id_estado, estado) VALUES (22, 'Yaracuy');
INSERT INTO estados (id_estado, estado) VALUES (23, 'Zulia');
INSERT INTO estados (id_estado, estado) VALUES (24, 'Distrito Capital');
INSERT INTO estados (id_estado, estado) VALUES (25, 'Dependencias Federales');

INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (1, 1, 'Alto Orinoco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (2, 1, 'Atabapo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (3, 1, 'Atures');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (4, 1, 'Autana');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (5, 1, 'Manapiare');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (6, 1, 'Maroa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (7, 1, 'Río Negro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (8, 2, 'Anaco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (9, 2, 'Aragua');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (10, 2, 'Manuel Ezequiel Bruzual');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (11, 2, 'Diego Bautista Urbaneja');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (12, 2, 'Fernando Peñalver');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (13, 2, 'Francisco Del Carmen Carvajal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (14, 2, 'General Sir Arthur McGregor');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (15, 2, 'Guanta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (16, 2, 'Independencia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (17, 2, 'José Gregorio Monagas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (18, 2, 'Juan Antonio Sotillo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (19, 2, 'Juan Manuel Cajigal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (20, 2, 'Libertad');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (21, 2, 'Francisco de Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (22, 2, 'Pedro María Freites');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (23, 2, 'Píritu');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (24, 2, 'San José de Guanipa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (25, 2, 'San Juan de Capistrano');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (26, 2, 'Santa Ana');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (27, 2, 'Simón Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (28, 2, 'Simón Rodríguez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (29, 3, 'Achaguas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (30, 3, 'Biruaca');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (31, 3, 'Muñóz');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (32, 3, 'Páez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (33, 3, 'Pedro Camejo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (34, 3, 'Rómulo Gallegos');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (35, 3, 'San Fernando');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (36, 4, 'Atanasio Girardot');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (37, 4, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (38, 4, 'Camatagua');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (39, 4, 'Francisco Linares Alcántara');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (40, 4, 'José Ángel Lamas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (41, 4, 'José Félix Ribas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (42, 4, 'José Rafael Revenga');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (43, 4, 'Libertador');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (44, 4, 'Mario Briceño Iragorry');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (45, 4, 'Ocumare de la Costa de Oro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (46, 4, 'San Casimiro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (47, 4, 'San Sebastián');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (48, 4, 'Santiago Mariño');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (49, 4, 'Santos Michelena');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (50, 4, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (51, 4, 'Tovar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (52, 4, 'Urdaneta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (53, 4, 'Zamora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (54, 5, 'Alberto Arvelo Torrealba');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (55, 5, 'Andrés Eloy Blanco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (56, 5, 'Antonio José de Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (57, 5, 'Arismendi');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (58, 5, 'Barinas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (59, 5, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (60, 5, 'Cruz Paredes');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (61, 5, 'Ezequiel Zamora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (62, 5, 'Obispos');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (63, 5, 'Pedraza');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (64, 5, 'Rojas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (65, 5, 'Sosa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (66, 6, 'Caroní');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (67, 6, 'Cedeño');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (68, 6, 'El Callao');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (69, 6, 'Gran Sabana');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (70, 6, 'Heres');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (71, 6, 'Piar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (72, 6, 'Angostura (Raúl Leoni');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (73, 6, 'Roscio');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (74, 6, 'Sifontes');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (75, 6, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (76, 6, 'Padre Pedro Chien');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (77, 7, 'Bejuma');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (78, 7, 'Carlos Arvelo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (79, 7, 'Diego Ibarra');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (80, 7, 'Guacara');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (81, 7, 'Juan José Mora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (82, 7, 'Libertador');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (83, 7, 'Los Guayos');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (84, 7, 'Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (85, 7, 'Montalbán');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (86, 7, 'Naguanagua');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (87, 7, 'Puerto Cabello');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (88, 7, 'San Diego');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (89, 7, 'San Joaquín');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (90, 7, 'Valencia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (91, 8, 'Anzoátegui');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (92, 8, 'Tinaquillo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (93, 8, 'Girardot');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (94, 8, 'Lima Blanco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (95, 8, 'Pao de San Juan Bautista');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (96, 8, 'Ricaurte');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (97, 8, 'Rómulo Gallegos');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (98, 8, 'San Carlos');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (99, 8, 'Tinaco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (100, 9, 'Antonio Díaz');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (101, 9, 'Casacoima');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (102, 9, 'Pedernales');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (103, 9, 'Tucupita');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (104, 10, 'Acosta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (105, 10, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (106, 10, 'Buchivacoa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (107, 10, 'Cacique Manaure');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (108, 10, 'Carirubana');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (109, 10, 'Colina');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (110, 10, 'Dabajuro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (111, 10, 'Democracia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (112, 10, 'Falcón');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (113, 10, 'Federación');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (114, 10, 'Jacura');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (115, 10, 'José Laurencio Silva');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (116, 10, 'Los Taques');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (117, 10, 'Mauroa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (118, 10, 'Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (119, 10, 'Monseñor Iturriza');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (120, 10, 'Palmasola');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (121, 10, 'Petit');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (122, 10, 'Píritu');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (123, 10, 'San Francisco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (124, 10, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (125, 10, 'Tocópero');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (126, 10, 'Unión');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (127, 10, 'Urumaco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (128, 10, 'Zamora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (129, 11, 'Camaguán');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (130, 11, 'Chaguaramas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (131, 11, 'El Socorro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (132, 11, 'José Félix Ribas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (133, 11, 'José Tadeo Monagas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (134, 11, 'Juan Germán Roscio');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (135, 11, 'Julián Mellado');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (136, 11, 'Las Mercedes');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (137, 11, 'Leonardo Infante');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (138, 11, 'Pedro Zaraza');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (139, 11, 'Ortíz');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (140, 11, 'San Gerónimo de Guayabal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (141, 11, 'San José de Guaribe');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (142, 11, 'Santa María de Ipire');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (143, 11, 'Sebastián Francisco de Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (144, 12, 'Andrés Eloy Blanco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (145, 12, 'Crespo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (146, 12, 'Iribarren');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (147, 12, 'Jiménez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (148, 12, 'Morán');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (149, 12, 'Palavecino');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (150, 12, 'Simón Planas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (151, 12, 'Torres');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (152, 12, 'Urdaneta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (179, 13, 'Alberto Adriani');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (180, 13, 'Andrés Bello');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (181, 13, 'Antonio Pinto Salinas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (182, 13, 'Aricagua');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (183, 13, 'Arzobispo Chacón');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (184, 13, 'Campo Elías');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (185, 13, 'Caracciolo Parra Olmedo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (186, 13, 'Cardenal Quintero');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (187, 13, 'Guaraque');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (188, 13, 'Julio César Salas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (189, 13, 'Justo Briceño');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (190, 13, 'Libertador');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (191, 13, 'Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (192, 13, 'Obispo Ramos de Lora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (193, 13, 'Padre Noguera');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (194, 13, 'Pueblo Llano');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (195, 13, 'Rangel');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (196, 13, 'Rivas Dávila');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (197, 13, 'Santos Marquina');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (198, 13, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (199, 13, 'Tovar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (200, 13, 'Tulio Febres Cordero');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (201, 13, 'Zea');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (223, 14, 'Acevedo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (224, 14, 'Andrés Bello');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (225, 14, 'Baruta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (226, 14, 'Brión');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (227, 14, 'Buroz');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (228, 14, 'Carrizal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (229, 14, 'Chacao');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (230, 14, 'Cristóbal Rojas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (231, 14, 'El Hatillo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (232, 14, 'Guaicaipuro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (233, 14, 'Independencia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (234, 14, 'Lander');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (235, 14, 'Los Salias');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (236, 14, 'Páez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (237, 14, 'Paz Castillo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (238, 14, 'Pedro Gual');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (239, 14, 'Plaza');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (240, 14, 'Simón Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (241, 14, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (242, 14, 'Urdaneta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (243, 14, 'Zamora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (258, 15, 'Acosta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (259, 15, 'Aguasay');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (260, 15, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (261, 15, 'Caripe');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (262, 15, 'Cedeño');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (263, 15, 'Ezequiel Zamora');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (264, 15, 'Libertador');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (265, 15, 'Maturín');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (266, 15, 'Piar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (267, 15, 'Punceres');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (268, 15, 'Santa Bárbara');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (269, 15, 'Sotillo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (270, 15, 'Uracoa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (271, 16, 'Antolín del Campo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (272, 16, 'Arismendi');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (273, 16, 'García');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (274, 16, 'Gómez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (275, 16, 'Maneiro');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (276, 16, 'Marcano');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (277, 16, 'Mariño');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (278, 16, 'Península de Macanao');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (279, 16, 'Tubores');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (280, 16, 'Villalba');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (281, 16, 'Díaz');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (282, 17, 'Agua Blanca');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (283, 17, 'Araure');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (284, 17, 'Esteller');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (285, 17, 'Guanare');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (286, 17, 'Guanarito');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (287, 17, 'Monseñor José Vicente de Unda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (288, 17, 'Ospino');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (289, 17, 'Páez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (290, 17, 'Papelón');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (291, 17, 'San Genaro de Boconoíto');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (292, 17, 'San Rafael de Onoto');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (293, 17, 'Santa Rosalía');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (294, 17, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (295, 17, 'Turén');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (296, 18, 'Andrés Eloy Blanco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (297, 18, 'Andrés Mata');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (298, 18, 'Arismendi');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (299, 18, 'Benítez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (300, 18, 'Bermúdez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (301, 18, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (302, 18, 'Cajigal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (303, 18, 'Cruz Salmerón Acosta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (304, 18, 'Libertador');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (305, 18, 'Mariño');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (306, 18, 'Mejía');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (307, 18, 'Montes');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (308, 18, 'Ribero');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (309, 18, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (310, 18, 'Valdéz');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (341, 19, 'Andrés Bello');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (342, 19, 'Antonio Rómulo Costa');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (343, 19, 'Ayacucho');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (344, 19, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (345, 19, 'Cárdenas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (346, 19, 'Córdoba');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (347, 19, 'Fernández Feo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (348, 19, 'Francisco de Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (349, 19, 'García de Hevia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (350, 19, 'Guásimos');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (351, 19, 'Independencia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (352, 19, 'Jáuregui');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (353, 19, 'José María Vargas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (354, 19, 'Junín');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (355, 19, 'Libertad');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (356, 19, 'Libertador');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (357, 19, 'Lobatera');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (358, 19, 'Michelena');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (359, 19, 'Panamericano');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (360, 19, 'Pedro María Ureña');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (361, 19, 'Rafael Urdaneta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (362, 19, 'Samuel Darío Maldonado');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (363, 19, 'San Cristóbal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (364, 19, 'Seboruco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (365, 19, 'Simón Rodríguez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (366, 19, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (367, 19, 'Torbes');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (368, 19, 'Uribante');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (369, 19, 'San Judas Tadeo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (370, 20, 'Andrés Bello');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (371, 20, 'Boconó');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (372, 20, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (373, 20, 'Candelaria');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (374, 20, 'Carache');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (375, 20, 'Escuque');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (376, 20, 'José Felipe Márquez Cañizalez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (377, 20, 'Juan Vicente Campos Elías');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (378, 20, 'La Ceiba');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (379, 20, 'Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (380, 20, 'Monte Carmelo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (381, 20, 'Motatán');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (382, 20, 'Pampán');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (383, 20, 'Pampanito');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (384, 20, 'Rafael Rangel');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (385, 20, 'San Rafael de Carvajal');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (386, 20, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (387, 20, 'Trujillo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (388, 20, 'Urdaneta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (389, 20, 'Valera');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (390, 21, 'Vargas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (391, 22, 'Arístides Bastidas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (392, 22, 'Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (407, 22, 'Bruzual');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (408, 22, 'Cocorote');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (409, 22, 'Independencia');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (410, 22, 'José Antonio Páez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (411, 22, 'La Trinidad');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (412, 22, 'Manuel Monge');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (413, 22, 'Nirgua');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (414, 22, 'Peña');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (415, 22, 'San Felipe');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (416, 22, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (417, 22, 'Urachiche');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (418, 22, 'José Joaquín Veroes');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (441, 23, 'Almirante Padilla');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (442, 23, 'Baralt');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (443, 23, 'Cabimas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (444, 23, 'Catatumbo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (445, 23, 'Colón');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (446, 23, 'Francisco Javier Pulgar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (447, 23, 'Páez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (448, 23, 'Jesús Enrique Losada');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (449, 23, 'Jesús María Semprún');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (450, 23, 'La Cañada de Urdaneta');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (451, 23, 'Lagunillas');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (452, 23, 'Machiques de Perijá');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (453, 23, 'Mara');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (454, 23, 'Maracaibo');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (455, 23, 'Miranda');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (456, 23, 'Rosario de Perijá');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (457, 23, 'San Francisco');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (458, 23, 'Santa Rita');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (459, 23, 'Simón Bolívar');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (460, 23, 'Sucre');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (461, 23, 'Valmore Rodríguez');
INSERT INTO municipios (id_municipio, id_estado, municipio) VALUES (462, 24, 'Libertador');

INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1, 1, 'Alto Orinoco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (2, 1, 'Huachamacare Acanaña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (3, 1, 'Marawaka Toky Shamanaña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (4, 1, 'Mavaka Mavaka');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (5, 1, 'Sierra Parima Parimabé');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (6, 2, 'Ucata Laja Lisa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (7, 2, 'Yapacana Macuruco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (8, 2, 'Caname Guarinuma');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (9, 3, 'Fernando Girón Tovar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (10, 3, 'Luis Alberto Gómez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (11, 3, 'Pahueña Limón de Parhueña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (12, 3, 'Platanillal Platanillal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (13, 4, 'Samariapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (14, 4, 'Sipapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (15, 4, 'Munduapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (16, 4, 'Guayapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (17, 5, 'Alto Ventuari');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (18, 5, 'Medio Ventuari');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (19, 5, 'Bajo Ventuari');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (20, 6, 'Victorino');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (21, 6, 'Comunidad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (22, 7, 'Casiquiare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (23, 7, 'Cocuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (24, 7, 'San Carlos de Río Negro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (25, 7, 'Solano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (26, 8, 'Anaco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (27, 8, 'San Joaquín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (28, 9, 'Cachipo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (29, 9, 'Aragua de Barcelona');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (30, 11, 'Lechería');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (31, 11, 'El Morro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (32, 12, 'Puerto Píritu');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (33, 12, 'San Miguel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (34, 12, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (35, 13, 'Valle de Guanape');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (36, 13, 'Santa Bárbara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (37, 14, 'El Chaparro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (38, 14, 'Tomás Alfaro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (39, 14, 'Calatrava');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (40, 15, 'Guanta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (41, 15, 'Chorrerón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (42, 16, 'Mamo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (43, 16, 'Soledad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (44, 17, 'Mapire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (45, 17, 'Piar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (46, 17, 'Santa Clara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (47, 17, 'San Diego de Cabrutica');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (48, 17, 'Uverito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (49, 17, 'Zuata');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (50, 18, 'Puerto La Cruz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (51, 18, 'Pozuelos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (52, 19, 'Onoto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (53, 19, 'San Pablo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (54, 20, 'San Mateo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (55, 20, 'El Carito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (56, 20, 'Santa Inés');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (57, 20, 'La Romereña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (58, 21, 'Atapirire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (59, 21, 'Boca del Pao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (60, 21, 'El Pao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (61, 21, 'Pariaguán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (62, 22, 'Cantaura');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (63, 22, 'Libertador');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (64, 22, 'Santa Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (65, 22, 'Urica');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (66, 23, 'Píritu');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (67, 23, 'San Francisco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (68, 24, 'San José de Guanipa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (69, 25, 'Boca de Uchire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (70, 25, 'Boca de Chávez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (71, 26, 'Pueblo Nuevo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (72, 26, 'Santa Ana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (73, 27, 'Bergantín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (74, 27, 'Caigua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (75, 27, 'El Carmen');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (76, 27, 'El Pilar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (77, 27, 'Naricual');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (78, 27, 'San Crsitóbal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (79, 28, 'Edmundo Barrios');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (80, 28, 'Miguel Otero Silva');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (81, 29, 'Achaguas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (82, 29, 'Apurito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (83, 29, 'El Yagual');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (84, 29, 'Guachara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (85, 29, 'Mucuritas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (86, 29, 'Queseras del medio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (87, 30, 'Biruaca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (88, 31, 'Bruzual');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (89, 31, 'Mantecal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (90, 31, 'Quintero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (91, 31, 'Rincón Hondo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (92, 31, 'San Vicente');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (93, 32, 'Guasdualito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (94, 32, 'Aramendi');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (95, 32, 'El Amparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (96, 32, 'San Camilo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (97, 32, 'Urdaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (98, 33, 'San Juan de Payara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (99, 33, 'Codazzi');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (100, 33, 'Cunaviche');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (101, 34, 'Elorza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (102, 34, 'La Trinidad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (103, 35, 'San Fernando');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (104, 35, 'El Recreo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (105, 35, 'Peñalver');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (106, 35, 'San Rafael de Atamaica');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (107, 36, 'Pedro José Ovalles');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (108, 36, 'Joaquín Crespo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (109, 36, 'José Casanova Godoy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (110, 36, 'Madre María de San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (111, 36, 'Andrés Eloy Blanco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (112, 36, 'Los Tacarigua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (113, 36, 'Las Delicias');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (114, 36, 'Choroní');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (115, 37, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (116, 38, 'Camatagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (117, 38, 'Carmen de Cura');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (118, 39, 'Santa Rita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (119, 39, 'Francisco de Miranda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (120, 39, 'Moseñor Feliciano González');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (121, 40, 'Santa Cruz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (122, 41, 'José Félix Ribas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (123, 41, 'Castor Nieves Ríos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (124, 41, 'Las Guacamayas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (125, 41, 'Pao de Zárate');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (126, 41, 'Zuata');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (127, 42, 'José Rafael Revenga');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (128, 43, 'Palo Negro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (129, 43, 'San Martín de Porres');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (130, 44, 'El Limón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (131, 44, 'Caña de Azúcar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (132, 45, 'Ocumare de la Costa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (133, 46, 'San Casimiro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (134, 46, 'Güiripa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (135, 46, 'Ollas de Caramacate');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (136, 46, 'Valle Morín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (137, 47, 'San Sebastían');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (138, 48, 'Turmero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (139, 48, 'Arevalo Aponte');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (140, 48, 'Chuao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (141, 48, 'Samán de Güere');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (142, 48, 'Alfredo Pacheco Miranda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (143, 49, 'Santos Michelena');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (144, 49, 'Tiara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (145, 50, 'Cagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (146, 50, 'Bella Vista');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (147, 51, 'Tovar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (148, 52, 'Urdaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (149, 52, 'Las Peñitas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (150, 52, 'San Francisco de Cara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (151, 52, 'Taguay');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (152, 53, 'Zamora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (153, 53, 'Magdaleno');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (154, 53, 'San Francisco de Asís');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (155, 53, 'Valles de Tucutunemo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (156, 53, 'Augusto Mijares');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (157, 54, 'Sabaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (158, 54, 'Juan Antonio Rodríguez Domínguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (159, 55, 'El Cantón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (160, 55, 'Santa Cruz de Guacas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (161, 55, 'Puerto Vivas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (162, 56, 'Ticoporo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (163, 56, 'Nicolás Pulido');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (164, 56, 'Andrés Bello');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (165, 57, 'Arismendi');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (166, 57, 'Guadarrama');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (167, 57, 'La Unión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (168, 57, 'San Antonio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (169, 58, 'Barinas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (170, 58, 'Alberto Arvelo Larriva');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (171, 58, 'San Silvestre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (172, 58, 'Santa Inés');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (173, 58, 'Santa Lucía');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (174, 58, 'Torumos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (175, 58, 'El Carmen');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (176, 58, 'Rómulo Betancourt');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (177, 58, 'Corazón de Jesús');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (178, 58, 'Ramón Ignacio Méndez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (179, 58, 'Alto Barinas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (180, 58, 'Manuel Palacio Fajardo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (181, 58, 'Juan Antonio Rodríguez Domínguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (182, 58, 'Dominga Ortiz de Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (183, 59, 'Barinitas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (184, 59, 'Altamira de Cáceres');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (185, 59, 'Calderas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (186, 60, 'Barrancas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (187, 60, 'El Socorro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (188, 60, 'Mazparrito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (189, 61, 'Santa Bárbara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (190, 61, 'Pedro Briceño Méndez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (191, 61, 'Ramón Ignacio Méndez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (192, 61, 'José Ignacio del Pumar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (193, 62, 'Obispos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (194, 62, 'Guasimitos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (195, 62, 'El Real');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (196, 62, 'La Luz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (197, 63, 'Ciudad Bolívia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (198, 63, 'José Ignacio Briceño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (199, 63, 'José Félix Ribas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (200, 63, 'Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (201, 64, 'Libertad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (202, 64, 'Dolores');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (203, 64, 'Santa Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (204, 64, 'Palacio Fajardo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (205, 65, 'Ciudad de Nutrias');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (206, 65, 'El Regalo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (207, 65, 'Puerto Nutrias');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (208, 65, 'Santa Catalina');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (209, 66, 'Cachamay');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (210, 66, 'Chirica');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (211, 66, 'Dalla Costa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (212, 66, 'Once de Abril');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (213, 66, 'Simón Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (214, 66, 'Unare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (215, 66, 'Universidad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (216, 66, 'Vista al Sol');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (217, 66, 'Pozo Verde');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (218, 66, 'Yocoima');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (219, 66, '5 de Julio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (220, 67, 'Cedeño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (221, 67, 'Altagracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (222, 67, 'Ascensión Farreras');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (223, 67, 'Guaniamo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (224, 67, 'La Urbana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (225, 67, 'Pijiguaos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (226, 68, 'El Callao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (227, 69, 'Gran Sabana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (228, 69, 'Ikabarú');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (229, 70, 'Catedral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (230, 70, 'Zea');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (231, 70, 'Orinoco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (232, 70, 'José Antonio Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (233, 70, 'Marhuanta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (234, 70, 'Agua Salada');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (235, 70, 'Vista Hermosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (236, 70, 'La Sabanita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (237, 70, 'Panapana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (238, 71, 'Andrés Eloy Blanco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (239, 71, 'Pedro Cova');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (240, 72, 'Raúl Leoni');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (241, 72, 'Barceloneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (242, 72, 'Santa Bárbara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (243, 72, 'San Francisco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (244, 73, 'Roscio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (245, 73, 'Salóm');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (246, 74, 'Sifontes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (247, 74, 'Dalla Costa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (248, 74, 'San Isidro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (249, 75, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (250, 75, 'Aripao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (251, 75, 'Guarataro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (252, 75, 'Las Majadas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (253, 75, 'Moitaco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (254, 76, 'Padre Pedro Chien');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (255, 76, 'Río Grande');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (256, 77, 'Bejuma');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (257, 77, 'Canoabo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (258, 77, 'Simón Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (259, 78, 'Güigüe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (260, 78, 'Carabobo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (261, 78, 'Tacarigua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (262, 79, 'Mariara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (263, 79, 'Aguas Calientes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (264, 80, 'Ciudad Alianza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (265, 80, 'Guacara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (266, 80, 'Yagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (267, 81, 'Morón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (268, 81, 'Yagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (269, 82, 'Tocuyito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (270, 82, 'Independencia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (271, 83, 'Los Guayos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (272, 84, 'Miranda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (273, 85, 'Montalbán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (274, 86, 'Naguanagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (275, 87, 'Bartolomé Salóm');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (276, 87, 'Democracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (277, 87, 'Fraternidad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (278, 87, 'Goaigoaza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (279, 87, 'Juan José Flores');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (280, 87, 'Unión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (281, 87, 'Borburata');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (282, 87, 'Patanemo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (283, 88, 'San Diego');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (284, 89, 'San Joaquín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (285, 90, 'Candelaria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (286, 90, 'Catedral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (287, 90, 'El Socorro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (288, 90, 'Miguel Peña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (289, 90, 'Rafael Urdaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (290, 90, 'San Blas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (291, 90, 'San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (292, 90, 'Santa Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (293, 90, 'Negro Primero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (294, 91, 'Cojedes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (295, 91, 'Juan de Mata Suárez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (296, 92, 'Tinaquillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (297, 93, 'El Baúl');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (298, 93, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (299, 94, 'La Aguadita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (300, 94, 'Macapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (301, 95, 'El Pao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (302, 96, 'El Amparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (303, 96, 'Libertad de Cojedes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (304, 97, 'Rómulo Gallegos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (305, 98, 'San Carlos de Austria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (306, 98, 'Juan Ángel Bravo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (307, 98, 'Manuel Manrique');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (308, 99, 'General en Jefe José Laurencio Silva');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (309, 100, 'Curiapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (310, 100, 'Almirante Luis Brión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (311, 100, 'Francisco Aniceto Lugo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (312, 100, 'Manuel Renaud');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (313, 100, 'Padre Barral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (314, 100, 'Santos de Abelgas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (315, 101, 'Imataca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (316, 101, 'Cinco de Julio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (317, 101, 'Juan Bautista Arismendi');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (318, 101, 'Manuel Piar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (319, 101, 'Rómulo Gallegos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (320, 102, 'Pedernales');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (321, 102, 'Luis Beltrán Prieto Figueroa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (322, 103, 'San José (Delta Amacuro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (323, 103, 'José Vidal Marcano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (324, 103, 'Juan Millán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (325, 103, 'Leonardo Ruíz Pineda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (326, 103, 'Mariscal Antonio José de Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (327, 103, 'Monseñor Argimiro García');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (328, 103, 'San Rafael (Delta Amacuro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (329, 103, 'Virgen del Valle');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (330, 10, 'Clarines');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (331, 10, 'Guanape');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (332, 10, 'Sabana de Uchire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (333, 104, 'Capadare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (334, 104, 'La Pastora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (335, 104, 'Libertador');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (336, 104, 'San Juan de los Cayos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (337, 105, 'Aracua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (338, 105, 'La Peña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (339, 105, 'San Luis');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (340, 106, 'Bariro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (341, 106, 'Borojó');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (342, 106, 'Capatárida');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (343, 106, 'Guajiro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (344, 106, 'Seque');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (345, 106, 'Zazárida');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (346, 106, 'Valle de Eroa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (347, 107, 'Cacique Manaure');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (348, 108, 'Norte');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (349, 108, 'Carirubana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (350, 108, 'Santa Ana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (351, 108, 'Urbana Punta Cardón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (352, 109, 'La Vela de Coro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (353, 109, 'Acurigua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (354, 109, 'Guaibacoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (355, 109, 'Las Calderas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (356, 109, 'Macoruca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (357, 110, 'Dabajuro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (358, 111, 'Agua Clara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (359, 111, 'Avaria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (360, 111, 'Pedregal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (361, 111, 'Piedra Grande');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (362, 111, 'Purureche');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (363, 112, 'Adaure');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (364, 112, 'Adícora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (365, 112, 'Baraived');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (366, 112, 'Buena Vista');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (367, 112, 'Jadacaquiva');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (368, 112, 'El Vínculo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (369, 112, 'El Hato');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (370, 112, 'Moruy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (371, 112, 'Pueblo Nuevo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (372, 113, 'Agua Larga');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (373, 113, 'El Paují');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (374, 113, 'Independencia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (375, 113, 'Mapararí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (376, 114, 'Agua Linda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (377, 114, 'Araurima');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (378, 114, 'Jacura');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (379, 115, 'Tucacas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (380, 115, 'Boca de Aroa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (381, 116, 'Los Taques');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (382, 116, 'Judibana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (383, 117, 'Mene de Mauroa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (384, 117, 'San Félix');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (385, 117, 'Casigua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (386, 118, 'Guzmán Guillermo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (387, 118, 'Mitare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (388, 118, 'Río Seco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (389, 118, 'Sabaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (390, 118, 'San Antonio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (391, 118, 'San Gabriel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (392, 118, 'Santa Ana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (393, 119, 'Boca del Tocuyo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (394, 119, 'Chichiriviche');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (395, 119, 'Tocuyo de la Costa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (396, 120, 'Palmasola');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (397, 121, 'Cabure');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (398, 121, 'Colina');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (399, 121, 'Curimagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (400, 122, 'San José de la Costa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (401, 122, 'Píritu');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (402, 123, 'San Francisco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (403, 124, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (404, 124, 'Pecaya');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (405, 125, 'Tocópero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (406, 126, 'El Charal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (407, 126, 'Las Vegas del Tuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (408, 126, 'Santa Cruz de Bucaral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (409, 127, 'Bruzual');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (410, 127, 'Urumaco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (411, 128, 'Puerto Cumarebo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (412, 128, 'La Ciénaga');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (413, 128, 'La Soledad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (414, 128, 'Pueblo Cumarebo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (415, 128, 'Zazárida');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (416, 113, 'Churuguara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (417, 129, 'Camaguán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (418, 129, 'Puerto Miranda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (419, 129, 'Uverito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (420, 130, 'Chaguaramas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (421, 131, 'El Socorro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (422, 132, 'Tucupido');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (423, 132, 'San Rafael de Laya');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (424, 133, 'Altagracia de Orituco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (425, 133, 'San Rafael de Orituco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (426, 133, 'San Francisco Javier de Lezama');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (427, 133, 'Paso Real de Macaira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (428, 133, 'Carlos Soublette');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (429, 133, 'San Francisco de Macaira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (430, 133, 'Libertad de Orituco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (431, 134, 'Cantaclaro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (432, 134, 'San Juan de los Morros');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (433, 134, 'Parapara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (434, 135, 'El Sombrero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (435, 135, 'Sosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (436, 136, 'Las Mercedes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (437, 136, 'Cabruta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (438, 136, 'Santa Rita de Manapire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (439, 137, 'Valle de la Pascua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (440, 137, 'Espino');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (441, 138, 'San José de Unare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (442, 138, 'Zaraza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (443, 139, 'San José de Tiznados');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (444, 139, 'San Francisco de Tiznados');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (445, 139, 'San Lorenzo de Tiznados');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (446, 139, 'Ortiz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (447, 140, 'Guayabal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (448, 140, 'Cazorla');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (449, 141, 'San José de Guaribe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (450, 141, 'Uveral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (451, 142, 'Santa María de Ipire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (452, 142, 'Altamira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (453, 143, 'El Calvario');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (454, 143, 'El Rastro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (455, 143, 'Guardatinajas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (456, 143, 'Capital Urbana Calabozo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (457, 144, 'Quebrada Honda de Guache');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (458, 144, 'Pío Tamayo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (459, 144, 'Yacambú');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (460, 145, 'Fréitez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (461, 145, 'José María Blanco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (462, 146, 'Catedral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (463, 146, 'Concepción');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (464, 146, 'El Cují');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (465, 146, 'Juan de Villegas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (466, 146, 'Santa Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (467, 146, 'Tamaca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (468, 146, 'Unión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (469, 146, 'Aguedo Felipe Alvarado');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (470, 146, 'Buena Vista');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (471, 146, 'Juárez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (472, 147, 'Juan Bautista Rodríguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (473, 147, 'Cuara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (474, 147, 'Diego de Lozada');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (475, 147, 'Paraíso de San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (476, 147, 'San Miguel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (477, 147, 'Tintorero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (478, 147, 'José Bernardo Dorante');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (479, 147, 'Coronel Mariano Peraza ');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (480, 148, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (481, 148, 'Anzoátegui');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (482, 148, 'Guarico');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (483, 148, 'Hilario Luna y Luna');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (484, 148, 'Humocaro Alto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (485, 148, 'Humocaro Bajo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (486, 148, 'La Candelaria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (487, 148, 'Morán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (488, 149, 'Cabudare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (489, 149, 'José Gregorio Bastidas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (490, 149, 'Agua Viva');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (491, 150, 'Sarare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (492, 150, 'Buría');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (493, 150, 'Gustavo Vegas León');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (494, 151, 'Trinidad Samuel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (495, 151, 'Antonio Díaz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (496, 151, 'Camacaro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (497, 151, 'Castañeda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (498, 151, 'Cecilio Zubillaga');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (499, 151, 'Chiquinquirá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (500, 151, 'El Blanco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (501, 151, 'Espinoza de los Monteros');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (502, 151, 'Lara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (503, 151, 'Las Mercedes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (504, 151, 'Manuel Morillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (505, 151, 'Montaña Verde');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (506, 151, 'Montes de Oca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (507, 151, 'Torres');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (508, 151, 'Heriberto Arroyo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (509, 151, 'Reyes Vargas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (510, 151, 'Altagracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (511, 152, 'Siquisique');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (512, 152, 'Moroturo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (513, 152, 'San Miguel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (514, 152, 'Xaguas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (515, 179, 'Presidente Betancourt');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (516, 179, 'Presidente Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (517, 179, 'Presidente Rómulo Gallegos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (518, 179, 'Gabriel Picón González');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (519, 179, 'Héctor Amable Mora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (520, 179, 'José Nucete Sardi');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (521, 179, 'Pulido Méndez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (522, 180, 'La Azulita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (523, 181, 'Santa Cruz de Mora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (524, 181, 'Mesa Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (525, 181, 'Mesa de Las Palmas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (526, 182, 'Aricagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (527, 182, 'San Antonio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (528, 183, 'Canagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (529, 183, 'Capurí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (530, 183, 'Chacantá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (531, 183, 'El Molino');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (532, 183, 'Guaimaral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (533, 183, 'Mucutuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (534, 183, 'Mucuchachí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (535, 184, 'Fernández Peña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (536, 184, 'Matriz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (537, 184, 'Montalbán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (538, 184, 'Acequias');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (539, 184, 'Jají');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (540, 184, 'La Mesa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (541, 184, 'San José del Sur');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (542, 185, 'Tucaní');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (543, 185, 'Florencio Ramírez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (544, 186, 'Santo Domingo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (545, 186, 'Las Piedras');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (546, 187, 'Guaraque');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (547, 187, 'Mesa de Quintero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (548, 187, 'Río Negro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (549, 188, 'Arapuey');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (550, 188, 'Palmira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (551, 189, 'San Cristóbal de Torondoy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (552, 189, 'Torondoy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (553, 190, 'Antonio Spinetti Dini');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (554, 190, 'Arias');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (555, 190, 'Caracciolo Parra Pérez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (556, 190, 'Domingo Peña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (557, 190, 'El Llano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (558, 190, 'Gonzalo Picón Febres');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (559, 190, 'Jacinto Plaza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (560, 190, 'Juan Rodríguez Suárez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (561, 190, 'Lasso de la Vega');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (562, 190, 'Mariano Picón Salas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (563, 190, 'Milla');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (564, 190, 'Osuna Rodríguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (565, 190, 'Sagrario');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (566, 190, 'El Morro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (567, 190, 'Los Nevados');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (568, 191, 'Andrés Eloy Blanco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (569, 191, 'La Venta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (570, 191, 'Piñango');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (571, 191, 'Timotes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (572, 192, 'Eloy Paredes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (573, 192, 'San Rafael de Alcázar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (574, 192, 'Santa Elena de Arenales');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (575, 193, 'Santa María de Caparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (576, 194, 'Pueblo Llano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (577, 195, 'Cacute');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (578, 195, 'La Toma');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (579, 195, 'Mucuchíes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (580, 195, 'Mucurubá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (581, 195, 'San Rafael');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (582, 196, 'Gerónimo Maldonado');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (583, 196, 'Bailadores');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (584, 197, 'Tabay');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (585, 198, 'Chiguará');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (586, 198, 'Estánquez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (587, 198, 'Lagunillas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (588, 198, 'La Trampa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (589, 198, 'Pueblo Nuevo del Sur');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (590, 198, 'San Juan');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (591, 199, 'El Amparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (592, 199, 'El Llano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (593, 199, 'San Francisco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (594, 199, 'Tovar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (595, 200, 'Independencia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (596, 200, 'María de la Concepción Palacios Blanco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (597, 200, 'Nueva Bolivia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (598, 200, 'Santa Apolonia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (599, 201, 'Caño El Tigre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (600, 201, 'Zea');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (601, 223, 'Aragüita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (602, 223, 'Arévalo González');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (603, 223, 'Capaya');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (604, 223, 'Caucagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (605, 223, 'Panaquire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (606, 223, 'Ribas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (607, 223, 'El Café');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (608, 223, 'Marizapa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (609, 224, 'Cumbo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (610, 224, 'San José de Barlovento');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (611, 225, 'El Cafetal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (612, 225, 'Las Minas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (613, 225, 'Nuestra Señora del Rosario');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (614, 226, 'Higuerote');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (615, 226, 'Curiepe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (616, 226, 'Tacarigua de Brión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (617, 227, 'Mamporal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (618, 228, 'Carrizal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (619, 229, 'Chacao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (620, 230, 'Charallave');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (621, 230, 'Las Brisas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (622, 231, 'El Hatillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (623, 232, 'Altagracia de la Montaña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (624, 232, 'Cecilio Acosta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (625, 232, 'Los Teques');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (626, 232, 'El Jarillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (627, 232, 'San Pedro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (628, 232, 'Tácata');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (629, 232, 'Paracotos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (630, 233, 'Cartanal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (631, 233, 'Santa Teresa del Tuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (632, 234, 'La Democracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (633, 234, 'Ocumare del Tuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (634, 234, 'Santa Bárbara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (635, 235, 'San Antonio de los Altos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (636, 236, 'Río Chico');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (637, 236, 'El Guapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (638, 236, 'Tacarigua de la Laguna');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (639, 236, 'Paparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (640, 236, 'San Fernando del Guapo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (641, 237, 'Santa Lucía del Tuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (642, 238, 'Cúpira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (643, 238, 'Machurucuto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (644, 239, 'Guarenas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (645, 240, 'San Antonio de Yare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (646, 240, 'San Francisco de Yare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (647, 241, 'Leoncio Martínez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (648, 241, 'Petare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (649, 241, 'Caucagüita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (650, 241, 'Filas de Mariche');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (651, 241, 'La Dolorita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (652, 242, 'Cúa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (653, 242, 'Nueva Cúa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (654, 243, 'Guatire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (655, 243, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (656, 258, 'San Antonio de Maturín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (657, 258, 'San Francisco de Maturín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (658, 259, 'Aguasay');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (659, 260, 'Caripito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (660, 261, 'El Guácharo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (661, 261, 'La Guanota');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (662, 261, 'Sabana de Piedra');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (663, 261, 'San Agustín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (664, 261, 'Teresen');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (665, 261, 'Caripe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (666, 262, 'Areo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (667, 262, 'Capital Cedeño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (668, 262, 'San Félix de Cantalicio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (669, 262, 'Viento Fresco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (670, 263, 'El Tejero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (671, 263, 'Punta de Mata');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (672, 264, 'Chaguaramas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (673, 264, 'Las Alhuacas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (674, 264, 'Tabasca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (675, 264, 'Temblador');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (676, 265, 'Alto de los Godos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (677, 265, 'Boquerón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (678, 265, 'Las Cocuizas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (679, 265, 'La Cruz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (680, 265, 'San Simón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (681, 265, 'El Corozo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (682, 265, 'El Furrial');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (683, 265, 'Jusepín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (684, 265, 'La Pica');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (685, 265, 'San Vicente');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (686, 266, 'Aparicio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (687, 266, 'Aragua de Maturín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (688, 266, 'Chaguamal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (689, 266, 'El Pinto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (690, 266, 'Guanaguana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (691, 266, 'La Toscana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (692, 266, 'Taguaya');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (693, 267, 'Cachipo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (694, 267, 'Quiriquire');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (695, 268, 'Santa Bárbara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (696, 269, 'Barrancas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (697, 269, 'Los Barrancos de Fajardo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (698, 270, 'Uracoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (699, 271, 'Antolín del Campo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (700, 272, 'Arismendi');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (701, 273, 'García');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (702, 273, 'Francisco Fajardo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (703, 274, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (704, 274, 'Guevara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (705, 274, 'Matasiete');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (706, 274, 'Santa Ana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (707, 274, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (708, 275, 'Aguirre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (709, 275, 'Maneiro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (710, 276, 'Adrián');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (711, 276, 'Juan Griego');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (712, 276, 'Yaguaraparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (713, 277, 'Porlamar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (714, 278, 'San Francisco de Macanao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (715, 278, 'Boca de Río');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (716, 279, 'Tubores');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (717, 279, 'Los Baleales');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (718, 280, 'Vicente Fuentes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (719, 280, 'Villalba');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (720, 281, 'San Juan Bautista');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (721, 281, 'Zabala');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (722, 283, 'Capital Araure');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (723, 283, 'Río Acarigua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (724, 284, 'Capital Esteller');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (725, 284, 'Uveral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (726, 285, 'Guanare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (727, 285, 'Córdoba');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (728, 285, 'San José de la Montaña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (729, 285, 'San Juan de Guanaguanare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (730, 285, 'Virgen de la Coromoto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (731, 286, 'Guanarito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (732, 286, 'Trinidad de la Capilla');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (733, 286, 'Divina Pastora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (734, 287, 'Monseñor José Vicente de Unda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (735, 287, 'Peña Blanca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (736, 288, 'Capital Ospino');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (737, 288, 'Aparición');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (738, 288, 'La Estación');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (739, 289, 'Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (740, 289, 'Payara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (741, 289, 'Pimpinela');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (742, 289, 'Ramón Peraza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (743, 290, 'Papelón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (744, 290, 'Caño Delgadito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (745, 291, 'San Genaro de Boconoito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (746, 291, 'Antolín Tovar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (747, 292, 'San Rafael de Onoto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (748, 292, 'Santa Fe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (749, 292, 'Thermo Morles');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (750, 293, 'Santa Rosalía');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (751, 293, 'Florida');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (752, 294, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (753, 294, 'Concepción');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (754, 294, 'San Rafael de Palo Alzado');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (755, 294, 'Uvencio Antonio Velásquez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (756, 294, 'San José de Saguaz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (757, 294, 'Villa Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (758, 295, 'Turén');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (759, 295, 'Canelones');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (760, 295, 'Santa Cruz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (761, 295, 'San Isidro Labrador');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (762, 296, 'Mariño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (763, 296, 'Rómulo Gallegos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (764, 297, 'San José de Aerocuar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (765, 297, 'Tavera Acosta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (766, 298, 'Río Caribe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (767, 298, 'Antonio José de Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (768, 298, 'El Morro de Puerto Santo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (769, 298, 'Puerto Santo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (770, 298, 'San Juan de las Galdonas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (771, 299, 'El Pilar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (772, 299, 'El Rincón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (773, 299, 'General Francisco Antonio Váquez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (774, 299, 'Guaraúnos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (775, 299, 'Tunapuicito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (776, 299, 'Unión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (777, 300, 'Santa Catalina');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (778, 300, 'Santa Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (779, 300, 'Santa Teresa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (780, 300, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (781, 300, 'Maracapana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (782, 302, 'Libertad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (783, 302, 'El Paujil');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (784, 302, 'Yaguaraparo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (785, 303, 'Cruz Salmerón Acosta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (786, 303, 'Chacopata');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (787, 303, 'Manicuare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (788, 304, 'Tunapuy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (789, 304, 'Campo Elías');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (790, 305, 'Irapa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (791, 305, 'Campo Claro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (792, 305, 'Maraval');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (793, 305, 'San Antonio de Irapa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (794, 305, 'Soro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (795, 306, 'Mejía');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (796, 307, 'Cumanacoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (797, 307, 'Arenas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (798, 307, 'Aricagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (799, 307, 'Cogollar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (800, 307, 'San Fernando');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (801, 307, 'San Lorenzo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (802, 308, 'Villa Frontado (Muelle de Cariaco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (803, 308, 'Catuaro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (804, 308, 'Rendón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (805, 308, 'San Cruz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (806, 308, 'Santa María');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (807, 309, 'Altagracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (808, 309, 'Santa Inés');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (809, 309, 'Valentín Valiente');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (810, 309, 'Ayacucho');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (811, 309, 'San Juan');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (812, 309, 'Raúl Leoni');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (813, 309, 'Gran Mariscal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (814, 310, 'Cristóbal Colón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (815, 310, 'Bideau');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (816, 310, 'Punta de Piedras');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (817, 310, 'Güiria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (818, 341, 'Andrés Bello');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (819, 342, 'Antonio Rómulo Costa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (820, 343, 'Ayacucho');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (821, 343, 'Rivas Berti');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (822, 343, 'San Pedro del Río');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (823, 344, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (824, 344, 'Palotal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (825, 344, 'General Juan Vicente Gómez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (826, 344, 'Isaías Medina Angarita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (827, 345, 'Cárdenas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (828, 345, 'Amenodoro Ángel Lamus');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (829, 345, 'La Florida');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (830, 346, 'Córdoba');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (831, 347, 'Fernández Feo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (832, 347, 'Alberto Adriani');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (833, 347, 'Santo Domingo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (834, 348, 'Francisco de Miranda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (835, 349, 'García de Hevia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (836, 349, 'Boca de Grita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (837, 349, 'José Antonio Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (838, 350, 'Guásimos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (839, 351, 'Independencia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (840, 351, 'Juan Germán Roscio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (841, 351, 'Román Cárdenas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (842, 352, 'Jáuregui');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (843, 352, 'Emilio Constantino Guerrero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (844, 352, 'Monseñor Miguel Antonio Salas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (845, 353, 'José María Vargas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (846, 354, 'Junín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (847, 354, 'La Petrólea');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (848, 354, 'Quinimarí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (849, 354, 'Bramón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (850, 355, 'Libertad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (851, 355, 'Cipriano Castro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (852, 355, 'Manuel Felipe Rugeles');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (853, 356, 'Libertador');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (854, 356, 'Doradas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (855, 356, 'Emeterio Ochoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (856, 356, 'San Joaquín de Navay');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (857, 357, 'Lobatera');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (858, 357, 'Constitución');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (859, 358, 'Michelena');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (860, 359, 'Panamericano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (861, 359, 'La Palmita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (862, 360, 'Pedro María Ureña');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (863, 360, 'Nueva Arcadia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (864, 361, 'Delicias');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (865, 361, 'Pecaya');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (866, 362, 'Samuel Darío Maldonado');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (867, 362, 'Boconó');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (868, 362, 'Hernández');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (869, 363, 'La Concordia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (870, 363, 'San Juan Bautista');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (871, 363, 'Pedro María Morantes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (872, 363, 'San Sebastián');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (873, 363, 'Dr. Francisco Romero Lobo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (874, 364, 'Seboruco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (875, 365, 'Simón Rodríguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (876, 366, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (877, 366, 'Eleazar López Contreras');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (878, 366, 'San Pablo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (879, 367, 'Torbes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (880, 368, 'Uribante');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (881, 368, 'Cárdenas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (882, 368, 'Juan Pablo Peñalosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (883, 368, 'Potosí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (884, 369, 'San Judas Tadeo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (885, 370, 'Araguaney');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (886, 370, 'El Jaguito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (887, 370, 'La Esperanza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (888, 370, 'Santa Isabel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (889, 371, 'Boconó');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (890, 371, 'El Carmen');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (891, 371, 'Mosquey');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (892, 371, 'Ayacucho');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (893, 371, 'Burbusay');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (894, 371, 'General Ribas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (895, 371, 'Guaramacal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (896, 371, 'Vega de Guaramacal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (897, 371, 'Monseñor Jáuregui');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (898, 371, 'Rafael Rangel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (899, 371, 'San Miguel');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (900, 371, 'San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (901, 372, 'Sabana Grande');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (902, 372, 'Cheregüé');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (903, 372, 'Granados');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (904, 373, 'Arnoldo Gabaldón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (905, 373, 'Bolivia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (906, 373, 'Carrillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (907, 373, 'Cegarra');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (908, 373, 'Chejendé');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (909, 373, 'Manuel Salvador Ulloa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (910, 373, 'San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (911, 374, 'Carache');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (912, 374, 'La Concepción');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (913, 374, 'Cuicas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (914, 374, 'Panamericana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (915, 374, 'Santa Cruz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (916, 375, 'Escuque');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (917, 375, 'La Unión');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (918, 375, 'Santa Rita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (919, 375, 'Sabana Libre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (920, 376, 'El Socorro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (921, 376, 'Los Caprichos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (922, 376, 'Antonio José de Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (923, 377, 'Campo Elías');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (924, 377, 'Arnoldo Gabaldón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (925, 378, 'Santa Apolonia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (926, 378, 'El Progreso');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (927, 378, 'La Ceiba');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (928, 378, 'Tres de Febrero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (929, 379, 'El Dividive');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (930, 379, 'Agua Santa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (931, 379, 'Agua Caliente');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (932, 379, 'El Cenizo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (933, 379, 'Valerita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (934, 380, 'Monte Carmelo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (935, 380, 'Buena Vista');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (936, 380, 'Santa María del Horcón');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (937, 381, 'Motatán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (938, 381, 'El Baño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (939, 381, 'Jalisco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (940, 382, 'Pampán');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (941, 382, 'Flor de Patria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (942, 382, 'La Paz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (943, 382, 'Santa Ana');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (944, 383, 'Pampanito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (945, 383, 'La Concepción');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (946, 383, 'Pampanito II');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (947, 384, 'Betijoque');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (948, 384, 'José Gregorio Hernández');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (949, 384, 'La Pueblita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (950, 384, 'Los Cedros');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (951, 385, 'Carvajal');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (952, 385, 'Campo Alegre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (953, 385, 'Antonio Nicolás Briceño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (954, 385, 'José Leonardo Suárez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (955, 386, 'Sabana de Mendoza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (956, 386, 'Junín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (957, 386, 'Valmore Rodríguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (958, 386, 'El Paraíso');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (959, 387, 'Andrés Linares');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (960, 387, 'Chiquinquirá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (961, 387, 'Cristóbal Mendoza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (962, 387, 'Cruz Carrillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (963, 387, 'Matriz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (964, 387, 'Monseñor Carrillo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (965, 387, 'Tres Esquinas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (966, 388, 'Cabimbú');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (967, 388, 'Jajó');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (968, 388, 'La Mesa de Esnujaque');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (969, 388, 'Santiago');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (970, 388, 'Tuñame');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (971, 388, 'La Quebrada');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (972, 389, 'Juan Ignacio Montilla');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (973, 389, 'La Beatriz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (974, 389, 'La Puerta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (975, 389, 'Mendoza del Valle de Momboy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (976, 389, 'Mercedes Díaz');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (977, 389, 'San Luis');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (978, 390, 'Caraballeda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (979, 390, 'Carayaca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (980, 390, 'Carlos Soublette');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (981, 390, 'Caruao Chuspa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (982, 390, 'Catia La Mar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (983, 390, 'El Junko');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (984, 390, 'La Guaira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (985, 390, 'Macuto');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (986, 390, 'Maiquetía');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (987, 390, 'Naiguatá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (988, 390, 'Urimare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (989, 391, 'Arístides Bastidas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (990, 392, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (991, 407, 'Chivacoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (992, 407, 'Campo Elías');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (993, 408, 'Cocorote');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (994, 409, 'Independencia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (995, 410, 'José Antonio Páez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (996, 411, 'La Trinidad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (997, 412, 'Manuel Monge');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (998, 413, 'Salóm');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (999, 413, 'Temerla');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1000, 413, 'Nirgua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1001, 414, 'San Andrés');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1002, 414, 'Yaritagua');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1003, 415, 'San Javier');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1004, 415, 'Albarico');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1005, 415, 'San Felipe');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1006, 416, 'Sucre');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1007, 417, 'Urachiche');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1008, 418, 'El Guayabo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1009, 418, 'Farriar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1010, 441, 'Isla de Toas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1011, 441, 'Monagas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1012, 442, 'San Timoteo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1013, 442, 'General Urdaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1014, 442, 'Libertador');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1015, 442, 'Marcelino Briceño');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1016, 442, 'Pueblo Nuevo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1017, 442, 'Manuel Guanipa Matos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1018, 443, 'Ambrosio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1019, 443, 'Carmen Herrera');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1020, 443, 'La Rosa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1021, 443, 'Germán Ríos Linares');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1022, 443, 'San Benito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1023, 443, 'Rómulo Betancourt');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1024, 443, 'Jorge Hernández');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1025, 443, 'Punta Gorda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1026, 443, 'Arístides Calvani');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1027, 444, 'Encontrados');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1028, 444, 'Udón Pérez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1029, 445, 'Moralito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1030, 445, 'San Carlos del Zulia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1031, 445, 'Santa Cruz del Zulia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1032, 445, 'Santa Bárbara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1033, 445, 'Urribarrí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1034, 446, 'Carlos Quevedo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1035, 446, 'Francisco Javier Pulgar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1036, 446, 'Simón Rodríguez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1037, 446, 'Guamo-Gavilanes');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1038, 448, 'La Concepción');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1039, 448, 'San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1040, 448, 'Mariano Parra León');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1041, 448, 'José Ramón Yépez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1042, 449, 'Jesús María Semprún');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1043, 449, 'Barí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1044, 450, 'Concepción');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1045, 450, 'Andrés Bello');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1046, 450, 'Chiquinquirá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1047, 450, 'El Carmelo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1048, 450, 'Potreritos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1049, 451, 'Libertad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1050, 451, 'Alonso de Ojeda');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1051, 451, 'Venezuela');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1052, 451, 'Eleazar López Contreras');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1053, 451, 'Campo Lara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1054, 452, 'Bartolomé de las Casas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1055, 452, 'Libertad');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1056, 452, 'Río Negro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1057, 452, 'San José de Perijá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1058, 453, 'San Rafael');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1059, 453, 'La Sierrita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1060, 453, 'Las Parcelas');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1061, 453, 'Luis de Vicente');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1062, 453, 'Monseñor Marcos Sergio Godoy');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1063, 453, 'Ricaurte');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1064, 453, 'Tamare');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1065, 454, 'Antonio Borjas Romero');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1066, 454, 'Bolívar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1067, 454, 'Cacique Mara');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1068, 454, 'Carracciolo Parra Pérez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1069, 454, 'Cecilio Acosta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1070, 454, 'Cristo de Aranza');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1071, 454, 'Coquivacoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1072, 454, 'Chiquinquirá');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1073, 454, 'Francisco Eugenio Bustamante');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1074, 454, 'Idelfonzo Vásquez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1075, 454, 'Juana de Ávila');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1076, 454, 'Luis Hurtado Higuera');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1077, 454, 'Manuel Dagnino');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1078, 454, 'Olegario Villalobos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1079, 454, 'Raúl Leoni');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1080, 454, 'Santa Lucía');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1081, 454, 'Venancio Pulgar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1082, 454, 'San Isidro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1083, 455, 'Altagracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1084, 455, 'Faría');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1085, 455, 'Ana María Campos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1086, 455, 'San Antonio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1087, 455, 'San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1088, 456, 'Donaldo García');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1089, 456, 'El Rosario');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1090, 456, 'Sixto Zambrano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1091, 457, 'San Francisco');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1092, 457, 'El Bajo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1093, 457, 'Domitila Flores');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1094, 457, 'Francisco Ochoa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1095, 457, 'Los Cortijos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1096, 457, 'Marcial Hernández');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1097, 458, 'Santa Rita');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1098, 458, 'El Mene');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1099, 458, 'Pedro Lucas Urribarrí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1100, 458, 'José Cenobio Urribarrí');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1101, 459, 'Rafael Maria Baralt');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1102, 459, 'Manuel Manrique');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1103, 459, 'Rafael Urdaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1104, 460, 'Bobures');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1105, 460, 'Gibraltar');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1106, 460, 'Heras');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1107, 460, 'Monseñor Arturo Álvarez');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1108, 460, 'Rómulo Gallegos');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1109, 460, 'El Batey');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1110, 461, 'Rafael Urdaneta');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1111, 461, 'La Victoria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1112, 461, 'Raúl Cuenca');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1113, 447, 'Sinamaica');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1114, 447, 'Alta Guajira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1115, 447, 'Elías Sánchez Rubio');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1116, 447, 'Guajira');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1117, 462, 'Altagracia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1118, 462, 'Antímano');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1119, 462, 'Caricuao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1120, 462, 'Catedral');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1121, 462, 'Coche');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1122, 462, 'El Junquito');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1123, 462, 'El Paraíso');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1124, 462, 'El Recreo');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1125, 462, 'El Valle');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1126, 462, 'La Candelaria');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1127, 462, 'La Pastora');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1128, 462, 'La Vega');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1129, 462, 'Macarao');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1130, 462, 'San Agustín');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1131, 462, 'San Bernardino');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1132, 462, 'San José');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1133, 462, 'San Juan');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1134, 462, 'San Pedro');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1135, 462, 'Santa Rosalía');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1136, 462, 'Santa Teresa');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1137, 462, 'Sucre (Catia');
INSERT INTO parroquias (id_parroquia, id_municipio, parroquia) VALUES (1138, 462, '23 de enero');

-- ============================================================
-- INSERTAR TRIBUNALES (Tabla: tribunal) - DATOS REALES
-- ============================================================
-- Columnas:  nombre_tribunal, materia, instancia, ubicacion

INSERT INTO tribunal (nombre_tribunal, materia, instancia, ubicacion) VALUES
-- JUZGADOS DE MUNICIPIO
('Juzgado 1° de Municipio Ordinario y Ejecutor de Medidas', 'Civil, Mercantil y Tránsito', 'Municipio', 'Palacio de Justicia - P. Ordaz'),
('Juzgado 2° de Municipio Ordinario y Ejecutor de Medidas', 'Civil, Mercantil y Tránsito', 'Municipio', 'Palacio de Justicia - P. Ordaz'),
('Juzgado 3° de Municipio Ordinario y Ejecutor de Medidas', 'Civil, Mercantil y Tránsito', 'Municipio', 'Palacio de Justicia - P. Ordaz'),
('Juzgado 4° de Municipio Ordinario y Ejecutor de Medidas', 'Civil, Mercantil y Tránsito', 'Municipio', 'Palacio de Justicia - P. Ordaz'),

-- JUZGADOS SUPERIORES
('Juzgado Superior 1° en lo Civil, Mercantil y Tránsito', 'Civil, Mercantil y Tránsito', 'Superior', 'Puerto Ordaz'),
('Juzgado Superior 2° en lo Civil, Mercantil y Tránsito', 'Civil, Mercantil y Tránsito', 'Superior', 'Puerto Ordaz'),
('Juzgado Superior 3° en lo Civil, Mercantil, Tránsito y Marítimo', 'Civil y Marítimo', 'Superior', 'Puerto Ordaz'),

-- LOPNNA (PROTECCIÓN)
('Tribunal 1° de Mediación y Sustanciación de LOPNNA', 'Protección Niños/Adolescentes', 'Primera Instancia', 'Palacio de Justicia - P. Ordaz'),
('Tribunal 2° de Mediación y Sustanciación de LOPNNA', 'Protección Niños/Adolescentes', 'Primera Instancia', 'Palacio de Justicia - P. Ordaz'),
('Tribunal 3° de Mediación y Sustanciación de LOPNNA', 'Protección Niños/Adolescentes', 'Primera Instancia', 'Palacio de Justicia - P. Ordaz'),

-- LABORAL (JUICIO)
('Juzgado 1° de Primera Instancia de Juicio del Trabajo', 'Laboral', 'Juicio', 'Sede Laboral / Palacio'),
('Juzgado 2° de Primera Instancia de Juicio del Trabajo', 'Laboral', 'Juicio', 'Sede Laboral / Palacio'),
('Juzgado 3° de Primera Instancia de Juicio del Trabajo', 'Laboral', 'Juicio', 'Sede Laboral / Palacio'),
('Juzgado 4° de Primera Instancia de Juicio del Trabajo', 'Laboral', 'Juicio', 'Sede Laboral / Palacio'),
('Juzgado 5° de Primera Instancia de Juicio del Trabajo', 'Laboral', 'Juicio', 'Sede Laboral / Palacio'),

-- LABORAL (SUSTANCIACIÓN, MEDIACIÓN Y EJECUCIÓN)
('Juzgado 1° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 2° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 3° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 4° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 5° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 6° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 7° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio'),
('Juzgado 8° de Sustanciación, Mediación y Ejecución del Trabajo', 'Laboral', 'Primera Instancia', 'Sede Laboral / Palacio');

-- ============================================================
-- INSERTAR NIVELES EDUCATIVOS (Tabla: niveles_educativos)
-- ============================================================

INSERT INTO niveles_educativos (nivel, estatus) VALUES
('Sin Instrucción', 'ACTIVO'),
('Primaria Incompleta', 'INACTIVO'),
('Primaria Completa', 'ACTIVO'),
('Bachillerato Incompleto', 'INACTIVO'),
('Bachillerato Completo', 'ACTIVO'),
('Técnico Medio', 'ACTIVO'),
('Técnico Superior Universitario (TSU)', 'ACTIVO'),
('Universitario (Pregrado)', 'ACTIVO'),
('Postgrado', 'INACTIVO');

-- ============================================================
-- INSERTAR CONDICIONES LABORALES (Tabla: condicion_laboral)
-- ============================================================

INSERT INTO condicion_laboral (condicion) VALUES 
('Patrono'),
('Empleado'),
('Obrero'),
('Cuenta propia'),
('Sin trabajo');

-- ============================================================
-- INSERTAR CONDICIONES DE ACTIVIDAD (Tabla: condicion_activividad)
-- ============================================================

INSERT INTO condicion_actividad (nombre_actividad) VALUES 
('Buscando Trabajo'),
('Ama de Casa'),
('Estudiante'),
('Jubilado'),
('Otro');

-- ============================================================
-- INSERTAR ESTADOS CIVILES (Tabla: estado_civil)
-- ============================================================

INSERT INTO estado_civil (descripcion) VALUES
('Soltero/a'),
('Casado/a'),
('Divorciado/a'),
('Viudo/a'),
('Concubino/a');


-- ============================================================
-- INSERTAR CENTROS (Tabla: centros)
-- ============================================================

INSERT INTO centros (nombre, abreviatura, id_parroquia) VALUES 
('UCAB Guayana', 'GY', 215),                                
('Casa Padre José Manuel Barandiarán s.j.', 'CB', 212);    

-- ============================================================
-- INSERTAR SEMESTRES (Tabla: semestres)
-- ============================================================

INSERT INTO semestre (termino, nombre, fecha_inicio, fecha_fin) VALUES
-- CICLO ACADÉMICO 2023
('2023-15', 'Semestre Sep 2022 - Ene 2023', '2022-09-26', '2023-01-27'),
('2023-25', 'Semestre Mar 2023 - Jul 2023', '2023-03-20', '2023-07-21'),

-- CICLO ACADÉMICO 2024
('2024-15', 'Semestre Sep 2023 - Ene 2024', '2023-09-25', '2024-01-26'),
('2024-25', 'Semestre Mar 2024 - Jul 2024', '2024-03-18', '2024-07-19'),

-- CICLO ACADÉMICO 2025
('2025-15', 'Semestre Sep 2024 - Ene 2025', '2024-09-23', '2025-01-24'),
('2025-25', 'Semestre Mar 2025 - Jul 2025', '2025-03-17', '2025-07-18'),

-- CICLO ACADÉMICO 2026 (El de tu Proyecto)
('2026-15', 'Semestre Sep 2025 - Ene 2026', '2025-09-22', '2026-01-23'),
('2026-25', 'Semestre Mar 2026 - Jul 2026', '2026-03-16', '2026-07-17');


-- ============================================================
-- FUNCIÓN PARA INSERTAR CASO 
-- ============================================================


CREATE OR REPLACE FUNCTION registrar_nuevo_caso(
    p_sintesis TEXT,
    p_tramite VARCHAR,
    p_cant_beneficiarios INTEGER,
    p_id_tribunal INTEGER,    -- Puede ser NULL
    p_termino VARCHAR,        -- Ej: '2025-15'
    p_id_centro INTEGER,      -- Ej: 1 (GY) o 2 (CB)
    p_cedula_solicitante VARCHAR,
    p_username_asignado VARCHAR,
    p_ambito_legal INTEGER
) RETURNS VARCHAR AS $$
DECLARE
    v_abreviatura VARCHAR(10);
    v_ultimo_correlativo INTEGER;
    v_nuevo_correlativo INTEGER;
    v_num_caso_generado VARCHAR(50);
BEGIN
    -- 1. Obtener abreviatura (GY o CB) y validar centro
    SELECT abreviatura INTO v_abreviatura 
    FROM centros WHERE id_centro = p_id_centro;

    IF v_abreviatura IS NULL THEN
        RAISE EXCEPTION 'El Centro con ID % no existe', p_id_centro;
    END IF;

    -- 2. Calcular Correlativo
    -- Busca el número más alto para ESTE centro y ESTE término (ej: 2025-15)
    -- Extrae los últimos 4 dígitos del código para hacer la matemática
    SELECT COALESCE(MAX(RIGHT(num_caso, 4)::INTEGER), 0) 
    INTO v_ultimo_correlativo
    FROM casos 
    WHERE id_centro = p_id_centro 
      AND termino = p_termino;

    v_nuevo_correlativo := v_ultimo_correlativo + 1;

    -- 3. Generar Código (Ej: GY-2025-15-0001)
    v_num_caso_generado := v_abreviatura || '-' || p_termino || '-' || LPAD(v_nuevo_correlativo::TEXT, 4, '0');

    -- 4. Insertar (Asignando fecha_recepcion = HOY automáticamente)
    INSERT INTO casos (
        num_caso, 
        fecha_recepcion,  -- <--- AQUÍ SE CUMPLE TU REGLA DE FECHA DEL DÍA
        sintesis, 
        tramite, 
        cant_beneficiarios, 
        estatus, 
        id_tribunal, 
        termino, 
        id_centro, 
        cedula, 
        username, 
        com_amb_legal,
        cod_caso_tribunal, fecha_res_caso_tri, fecha_crea_caso_tri -- Nulos al inicio
    ) VALUES (
        v_num_caso_generado,
        CURRENT_DATE,     -- <--- FECHA AUTOMÁTICA
        p_sintesis,
        p_tramite,
        p_cant_beneficiarios,
        'ABIERTO',
        p_id_tribunal,
        p_termino,
        p_id_centro,
        p_cedula_solicitante,
        p_username_asignado,
        p_ambito_legal,
        NULL, NULL, NULL
    );

    -- 4.1 Insertar estatus inicial en historial (ID = 1)
    INSERT INTO estatus_por_caso (id_est_caso, num_caso, fecha_cambio, estatus, observacion)
    VALUES (1, v_num_caso_generado, CURRENT_DATE, 'ABIERTO', 'Creación del caso');

    -- 5. Retornar el código generado para que el backend lo sepa
    RETURN v_num_caso_generado;
END;
$$ LANGUAGE plpgsql;


-- ============================================================
-- VISTA REPORTE VIVIENDA
-- ============================================================


CREATE VIEW vista_reporte_vivienda AS
SELECT 
    v.cedula,
    s.nombre,
    -- Tipo 1: Tipo de Vivienda
    cat1.descripcion AS tipo_vivienda,
    -- Tipo 2: Material del Piso
    cat2.descripcion AS material_piso,
    -- Tipo 3: Material Paredes
    cat3.descripcion AS material_paredes,
    -- Tipo 4: Material Techo
    cat4.descripcion AS material_techo,
    -- Tipo 5: Agua Potable
    cat5.descripcion AS servicio_agua,
    -- Tipo 6: Excretas
    cat6.descripcion AS eliminacion_excretas,
    -- Tipo 7: Aseo Urbano
    cat7.descripcion AS aseo_urbano,
    -- Vivenda (Datos base)
    v.cant_habit,
    v.cant_banos
FROM viviendas v
JOIN solicitantes s ON v.cedula = s.cedula
-- Join para Tipo 1 (Tipo Vivienda)
LEFT JOIN caracteristicas_viviendas car1 
    ON v.cedula = car1.cedula AND car1.id_tipo_cat = 1
LEFT JOIN categorias_de_vivienda cat1 
    ON car1.id_cat_vivienda = cat1.id_cat_vivienda AND car1.id_tipo_cat = cat1.id_tipo_cat
-- Join para Tipo 2 (Piso)
LEFT JOIN caracteristicas_viviendas car2 
    ON v.cedula = car2.cedula AND car2.id_tipo_cat = 2
LEFT JOIN categorias_de_vivienda cat2 
    ON car2.id_cat_vivienda = cat2.id_cat_vivienda AND car2.id_tipo_cat = cat2.id_tipo_cat
-- Join para Tipo 3 (Paredes)
LEFT JOIN caracteristicas_viviendas car3 
    ON v.cedula = car3.cedula AND car3.id_tipo_cat = 3
LEFT JOIN categorias_de_vivienda cat3 
    ON car3.id_cat_vivienda = cat3.id_cat_vivienda AND car3.id_tipo_cat = cat3.id_tipo_cat
-- Join para Tipo 4 (Techo)
LEFT JOIN caracteristicas_viviendas car4 
    ON v.cedula = car4.cedula AND car4.id_tipo_cat = 4
LEFT JOIN categorias_de_vivienda cat4 
    ON car4.id_cat_vivienda = cat4.id_cat_vivienda AND car4.id_tipo_cat = cat4.id_tipo_cat
-- Join para Tipo 5 (Agua)
LEFT JOIN caracteristicas_viviendas car5 
    ON v.cedula = car5.cedula AND car5.id_tipo_cat = 5
LEFT JOIN categorias_de_vivienda cat5 
    ON car5.id_cat_vivienda = cat5.id_cat_vivienda AND car5.id_tipo_cat = cat5.id_tipo_cat
-- Join para Tipo 6 (Excretas)
LEFT JOIN caracteristicas_viviendas car6 
    ON v.cedula = car6.cedula AND car6.id_tipo_cat = 6
LEFT JOIN categorias_de_vivienda cat6 
    ON car6.id_cat_vivienda = cat6.id_cat_vivienda AND car6.id_tipo_cat = cat6.id_tipo_cat
-- Join para Tipo 7 (Aseo)
LEFT JOIN caracteristicas_viviendas car7 
    ON v.cedula = car7.cedula AND car7.id_tipo_cat = 7
LEFT JOIN categorias_de_vivienda cat7 
    ON car7.id_cat_vivienda = cat7.id_cat_vivienda AND car7.id_tipo_cat = cat7.id_tipo_cat;