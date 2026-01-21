-- ================================================================
-- SECCIÓN 1: ESTRUCTURA DE TABLAS Y REGLAS DE INTEGRIDAD
-- ================================================================

-- 1.1 CATÁLOGOS GLOBALES Y GEOGRAFÍA
-- ----------------------------------------------------------------
CREATE TABLE estados (
  id_estado INTEGER PRIMARY KEY,
  nombre_estado VARCHAR(100) NOT NULL
);

CREATE TABLE municipios (
  id_municipio INTEGER PRIMARY KEY,
  nombre_municipio VARCHAR(100) NOT NULL,
  id_estado INTEGER,
  CONSTRAINT fk_mun_est FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

CREATE TABLE parroquias (
  id_parroquia INTEGER PRIMARY KEY,
  nombre_parroquia VARCHAR(100) NOT NULL,
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
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE NOT NULL,
  CONSTRAINT chk_formato_termino CHECK (termino ~ '^[0-9]{4}-[0-9]{2}$'),
  CONSTRAINT chk_logica_temporal_semestre CHECK (fecha_fin > fecha_inicio)
);

CREATE TABLE tribunal (
  id_tribunal SERIAL PRIMARY KEY,
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

-- 1.3 CATÁLOGOS SOCIOECONÓMICOS
-- ----------------------------------------------------------------
CREATE TABLE niveles_educativos (
  id_nivel SERIAL PRIMARY KEY,
  nivel VARCHAR(100),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_nivel CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE condicion_laboral (
  id_condicion SERIAL PRIMARY KEY,
  condicion VARCHAR(100),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_cond CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE condicion_actividad (
  id_condicion_actividad SERIAL PRIMARY KEY,
  nombre_actividad VARCHAR(100),
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_actividad CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE estado_civil (
  id_estado_civil SERIAL PRIMARY KEY,
  descripcion VARCHAR(50), 
  estatus VARCHAR(20) DEFAULT 'ACTIVO',
  CONSTRAINT chk_estatus_civil CHECK (estatus IN ('ACTIVO', 'INACTIVO'))
);

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
  f_nacimiento DATE NOT NULL,
  edad INTEGER, 
  f_registro DATE DEFAULT CURRENT_DATE,
  id_condicion INTEGER,
  id_condicion_actividad INTEGER,
  id_nivel INTEGER,
  tiempo_estudio VARCHAR(50),
  id_parroquia INTEGER,
  
  CONSTRAINT chk_sol_sexo CHECK (sexo IN ('Masculino', 'Femenino')),
  CONSTRAINT chk_sol_nacionalidad CHECK (nacionalidad IN ('Venezolano', 'Extranjero')),
  CONSTRAINT chk_sol_concubinato CHECK (concubinato IN ('SI', 'NO')),
  CONSTRAINT chk_logica_nacimiento CHECK (f_nacimiento <= f_registro),
  
  CONSTRAINT fk_sol_cond FOREIGN KEY (id_condicion) REFERENCES condicion_laboral(id_condicion),
  CONSTRAINT fk_sol_niv FOREIGN KEY (id_nivel) REFERENCES niveles_educativos(id_nivel),
  CONSTRAINT fk_sol_civil FOREIGN KEY (id_estado_civil) REFERENCES estado_civil(id_estado_civil),
  CONSTRAINT fk_sol_actividad FOREIGN KEY (id_condicion_actividad) REFERENCES condicion_actividad(id_condicion_actividad),
  CONSTRAINT fk_sol_parroquia FOREIGN KEY (id_parroquia) REFERENCES parroquias(id_parroquia)
);

CREATE TABLE familias (
  cedula VARCHAR(20) PRIMARY KEY,
  cant_personas INTEGER NOT NULL,
  cant_estudiando INTEGER DEFAULT 0,
  ingreso_mes DECIMAL(12,2) DEFAULT 0,
  jefe_familia BOOLEAN DEFAULT TRUE,
  cant_sin_trabajo INTEGER DEFAULT 0,
  cant_ninos INTEGER DEFAULT 0,
  cant_trabaja INTEGER DEFAULT 0,
  id_nivel_edu_jefe INTEGER,
  tiempo_estudio VARCHAR(50),

  CONSTRAINT chk_no_negativos_fam CHECK (
      cant_personas >= 0 AND cant_estudiando >= 0 AND 
      cant_sin_trabajo >= 0 AND cant_ninos >= 0 AND 
      cant_trabaja >= 0 AND ingreso_mes >= 0
  ),
  CONSTRAINT chk_existencia_minima CHECK (cant_personas > 0),
  CONSTRAINT chk_consistencia_estudiantes CHECK (cant_estudiando <= cant_personas),
  CONSTRAINT chk_ecuacion_demografica CHECK (
      cant_personas = (cant_ninos + cant_trabaja + cant_sin_trabajo)
  ),

  CONSTRAINT fk_fam_niv FOREIGN KEY (id_nivel_edu_jefe) REFERENCES niveles_educativos(id_nivel),
  CONSTRAINT fk_fam_sol FOREIGN KEY (cedula) REFERENCES solicitantes(cedula)
);

-- 1.6 VIVIENDA
-- ----------------------------------------------------------------
CREATE TABLE viviendas (
  cedula VARCHAR(20) PRIMARY KEY,
  cant_banos INTEGER DEFAULT 0,
  cant_habit INTEGER DEFAULT 1,

  CONSTRAINT chk_viv_habitabilidad CHECK (cant_habit > 0),
  CONSTRAINT chk_viv_banos_pos CHECK (cant_banos >= 0),
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

-- 1.7 CASOS Y AUDITORÍA
-- ----------------------------------------------------------------
CREATE TABLE casos (
  num_caso VARCHAR(50) PRIMARY KEY,
  fecha_recepcion DATE DEFAULT CURRENT_DATE,
  sintesis TEXT,
  tramite VARCHAR(100),
  cant_beneficiarios INTEGER CHECK (cant_beneficiarios > 0),
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
  CONSTRAINT chk_caso_tramite CHECK (tramite IN ('ASESORÍA', 'CONCILIACIÓN Y MEDIACIÓN', 'REDACCIÓN DE DOCUMENTOS'))
);

CREATE INDEX idx_casos_mix ON casos(id_centro, termino, username, cedula);

CREATE TABLE beneficiarios_casos (
    cedula VARCHAR(20) NOT NULL,
    num_caso VARCHAR(20) NOT NULL,
    tipo_beneficiario VARCHAR(50),
    parentesco VARCHAR(50),
    CONSTRAINT pk_beneficiarios_casos PRIMARY KEY (cedula, num_caso),
    CONSTRAINT fk_ben_casos_cedula FOREIGN KEY (cedula) REFERENCES solicitantes(cedula),
    CONSTRAINT fk_ben_casos_numcaso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

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

-- 1.9 DETALLES OPERATIVOS
-- ----------------------------------------------------------------
CREATE TABLE estatus_por_caso (
  id_est_caso INTEGER,
  num_caso VARCHAR(50),
  fecha_cambio DATE DEFAULT CURRENT_DATE,
  estatus VARCHAR(50),
  observacion VARCHAR(200),
  username VARCHAR(50), 
  
  PRIMARY KEY (num_caso, id_est_caso),
  CONSTRAINT fk_est_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_est_usr FOREIGN KEY (username) REFERENCES usuarios(username)
);

CREATE TABLE accion (
  id_accion INTEGER,
  num_caso VARCHAR(50),
  titulo VARCHAR(150),
  descripcion TEXT,
  fecha_registro DATE DEFAULT CURRENT_DATE,
  fecha_ejecucion DATE,
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_accion),
  CONSTRAINT fk_acc_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_acc_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE acciones_ejecutadas (
  id_accion INTEGER, 
  num_caso VARCHAR(50),
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_accion, username),
  CONSTRAINT fk_ejec_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_ejec_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT fk_ejec_acc FOREIGN KEY (num_caso, id_accion) REFERENCES accion(num_caso, id_accion)
);

CREATE TABLE encuentros (
  id_encuentros INTEGER,
  num_caso VARCHAR(50),
  fecha_atencion DATE DEFAULT CURRENT_DATE,
  fecha_proxima DATE,
  orientacion TEXT,
  observacion TEXT,
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_encuentros),
  CONSTRAINT fk_enc_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_enc_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT chk_coherencia_citas CHECK (fecha_proxima >= fecha_atencion)
);

CREATE TABLE encuentros_atendidos (
  id_encuentro INTEGER,
  num_caso VARCHAR(50),
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_encuentro, username),
  CONSTRAINT fk_aten_enc FOREIGN KEY (num_caso, id_encuentro) REFERENCES encuentros(num_caso, id_encuentros),
  CONSTRAINT fk_aten_usr FOREIGN KEY (username) REFERENCES usuarios(username),
  CONSTRAINT fk_aten_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso)
);

CREATE TABLE documentos (
  id_documento INTEGER,
  num_caso VARCHAR(50),
  fecha_registro DATE DEFAULT CURRENT_DATE,
  folio_ini INTEGER CHECK (folio_ini > 0),
  folio_fin INTEGER CHECK (folio_fin > 0),
  titulo VARCHAR(150),
  observacion TEXT,
  username VARCHAR(50),
  PRIMARY KEY (num_caso, id_documento),
  CONSTRAINT fk_doc_caso FOREIGN KEY (num_caso) REFERENCES casos(num_caso),
  CONSTRAINT chk_logica_folios CHECK (folio_fin >= folio_ini)
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
-- SECCIÓN 2: AUDITORÍA Y AUTOMATIZACIÓN (TRIGGERS)
-- ================================================================

-- 2.1 TABLA MAESTRA DE AUDITORÍA
CREATE TABLE auditoria_sistema (
    id_auditoria SERIAL PRIMARY KEY,
    nombre_tabla VARCHAR(50),
    operacion VARCHAR(10), 
    username_aplicacion VARCHAR(50), 
    fecha_evento TIMESTAMP DEFAULT NOW(),
    datos_anteriores JSONB, 
    datos_nuevos JSONB      
);

-- 2.2 FUNCIÓN DE AUDITORÍA GENERAL
CREATE OR REPLACE FUNCTION func_auditoria_global() RETURNS TRIGGER AS $$
DECLARE
    v_usuario VARCHAR(50);
    v_old_data JSONB;
    v_new_data JSONB;
BEGIN
    v_usuario := current_setting('app.current_user', true);
    IF v_usuario IS NULL OR v_usuario = '' THEN v_usuario := 'DESCONOCIDO'; END IF;

    IF (TG_OP = 'INSERT') THEN
        v_old_data := NULL; v_new_data := to_jsonb(NEW);
    ELSIF (TG_OP = 'UPDATE') THEN
        v_old_data := to_jsonb(OLD); v_new_data := to_jsonb(NEW);
    ELSIF (TG_OP = 'DELETE') THEN
        v_old_data := to_jsonb(OLD); v_new_data := NULL;
    END IF;

    INSERT INTO auditoria_sistema (nombre_tabla, operacion, username_aplicacion, datos_anteriores, datos_nuevos) 
    VALUES (TG_TABLE_NAME, TG_OP, v_usuario, v_old_data, v_new_data);

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- 2.3 TRIGGERS DE AUDITORÍA
CREATE TRIGGER trg_audit_casos AFTER INSERT OR UPDATE OR DELETE ON casos FOR EACH ROW EXECUTE FUNCTION func_auditoria_global();
CREATE TRIGGER trg_audit_solicitantes AFTER INSERT OR UPDATE OR DELETE ON solicitantes FOR EACH ROW EXECUTE FUNCTION func_auditoria_global();
CREATE TRIGGER trg_audit_familias AFTER INSERT OR UPDATE OR DELETE ON familias FOR EACH ROW EXECUTE FUNCTION func_auditoria_global();

-- 2.4 AUTOMATIZACIÓN DE ESTATUS
CREATE OR REPLACE FUNCTION func_historial_estatus_automatico() RETURNS TRIGGER AS $$
DECLARE
    v_usuario VARCHAR(50);
    v_nuevo_id INTEGER;
BEGIN
    -- Se dispara si el estatus cambia o es un caso nuevo
    IF (TG_OP = 'INSERT') OR (OLD.estatus IS DISTINCT FROM NEW.estatus) THEN
        v_usuario := current_setting('app.current_user', true);
        
        -- Calculamos ID correlativo
        SELECT COALESCE(MAX(id_est_caso), 0) + 1 INTO v_nuevo_id 
        FROM estatus_por_caso WHERE num_caso = NEW.num_caso;

        -- Insertamos en el historial (La BD lo hace por ti)
        INSERT INTO estatus_por_caso (
            id_est_caso, num_caso, fecha_cambio, estatus, observacion, username
        ) VALUES (
            v_nuevo_id, NEW.num_caso, CURRENT_DATE, NEW.estatus, 
            CASE WHEN TG_OP = 'INSERT' THEN 'Creación Inicial del Caso' ELSE 'Cambio de estatus automático' END, 
            v_usuario
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auto_historial_estatus
AFTER INSERT OR UPDATE ON casos
FOR EACH ROW EXECUTE FUNCTION func_historial_estatus_automatico();

-- ================================================================
-- SECCIÓN 3: PROCEDIMIENTOS Y VISTAS (User Logic)
-- ================================================================

-- 3.1 PROCEDIMIENTO PARA REGISTRAR CASO (ADAPTADO)
-- NOTA: Se eliminó el INSERT a 'estatus_por_caso' porque el Trigger 'trg_auto_historial_estatus'
-- ya lo hace automáticamente. Si se deja, causaría error de llave duplicada.
CREATE OR REPLACE FUNCTION registrar_nuevo_caso(
    p_sintesis TEXT,
    p_tramite VARCHAR,
    p_cant_beneficiarios INTEGER,
    p_id_tribunal INTEGER,
    p_id_centro INTEGER,
    p_cedula_solicitante VARCHAR,
    p_username_asignado VARCHAR,
    p_ambito_legal INTEGER
) RETURNS VARCHAR AS $$
DECLARE
    v_abreviatura VARCHAR(10);
    v_ultimo_correlativo INTEGER;
    v_nuevo_correlativo INTEGER;
    v_num_caso_generado VARCHAR(50);
    v_termino VARCHAR(20);
BEGIN
    -- 0. Validar Término Activo
    SELECT termino INTO v_termino FROM semestre 
    WHERE CURRENT_DATE BETWEEN fecha_inicio AND fecha_fin LIMIT 1;

    IF v_termino IS NULL THEN
        RAISE EXCEPTION 'No se encontró un término académico activo para la fecha actual (%)', CURRENT_DATE;
    END IF;

    -- 1. Validar Centro
    SELECT abreviatura INTO v_abreviatura FROM centros WHERE id_centro = p_id_centro;
    IF v_abreviatura IS NULL THEN
        RAISE EXCEPTION 'El Centro con ID % no existe', p_id_centro;
    END IF;

    -- 2. Calcular Correlativo (Lógica de negocio: Por Centro y Término)
    SELECT COALESCE(MAX(RIGHT(num_caso, 4)::INTEGER), 0) INTO v_ultimo_correlativo
    FROM casos WHERE id_centro = p_id_centro AND termino = v_termino;

    v_nuevo_correlativo := v_ultimo_correlativo + 1;
    v_num_caso_generado := v_abreviatura || '-' || v_termino || '-' || LPAD(v_nuevo_correlativo::TEXT, 4, '0');

    -- 3. Insertar Caso (El trigger se encargará del historial de estatus)
    INSERT INTO casos (
        num_caso, fecha_recepcion, sintesis, tramite, cant_beneficiarios, 
        estatus, id_tribunal, termino, id_centro, cedula, username, com_amb_legal
    ) VALUES (
        v_num_caso_generado, CURRENT_DATE, p_sintesis, p_tramite, p_cant_beneficiarios,
        'ABIERTO', p_id_tribunal, v_termino, p_id_centro, p_cedula_solicitante, p_username_asignado, p_ambito_legal
    );

    RETURN v_num_caso_generado;
END;
$$ LANGUAGE plpgsql;

-- 3.2 VISTA DE REPORTE DE VIVIENDA
CREATE OR REPLACE VIEW vista_reporte_vivienda AS
SELECT 
    v.cedula,
    s.nombre,
    cat1.descripcion AS tipo_vivienda,
    cat2.descripcion AS material_piso,
    cat3.descripcion AS material_paredes,
    cat4.descripcion AS material_techo,
    cat5.descripcion AS servicio_agua,
    cat6.descripcion AS eliminacion_excretas,
    cat7.descripcion AS aseo_urbano,
    v.cant_habit,
    v.cant_banos
FROM viviendas v
JOIN solicitantes s ON v.cedula = s.cedula
LEFT JOIN caracteristicas_viviendas car1 ON v.cedula = car1.cedula AND car1.id_tipo_cat = 1
LEFT JOIN categorias_de_vivienda cat1 ON car1.id_cat_vivienda = cat1.id_cat_vivienda AND car1.id_tipo_cat = cat1.id_tipo_cat
LEFT JOIN caracteristicas_viviendas car2 ON v.cedula = car2.cedula AND car2.id_tipo_cat = 2
LEFT JOIN categorias_de_vivienda cat2 ON car2.id_cat_vivienda = cat2.id_cat_vivienda AND car2.id_tipo_cat = cat2.id_tipo_cat
LEFT JOIN caracteristicas_viviendas car3 ON v.cedula = car3.cedula AND car3.id_tipo_cat = 3
LEFT JOIN categorias_de_vivienda cat3 ON car3.id_cat_vivienda = cat3.id_cat_vivienda AND car3.id_tipo_cat = cat3.id_tipo_cat
LEFT JOIN caracteristicas_viviendas car4 ON v.cedula = car4.cedula AND car4.id_tipo_cat = 4
LEFT JOIN categorias_de_vivienda cat4 ON car4.id_cat_vivienda = cat4.id_cat_vivienda AND car4.id_tipo_cat = cat4.id_tipo_cat
LEFT JOIN caracteristicas_viviendas car5 ON v.cedula = car5.cedula AND car5.id_tipo_cat = 5
LEFT JOIN categorias_de_vivienda cat5 ON car5.id_cat_vivienda = cat5.id_cat_vivienda AND car5.id_tipo_cat = cat5.id_tipo_cat
LEFT JOIN caracteristicas_viviendas car6 ON v.cedula = car6.cedula AND car6.id_tipo_cat = 6
LEFT JOIN categorias_de_vivienda cat6 ON car6.id_cat_vivienda = cat6.id_cat_vivienda AND car6.id_tipo_cat = cat6.id_tipo_cat
LEFT JOIN caracteristicas_viviendas car7 ON v.cedula = car7.cedula AND car7.id_tipo_cat = 7
LEFT JOIN categorias_de_vivienda cat7 ON car7.id_cat_vivienda = cat7.id_cat_vivienda AND car7.id_tipo_cat = cat7.id_tipo_cat;