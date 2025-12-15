-- =============================================================================
-- FUNCIÓN: Registrar Caso (Genera PK Inteligente e Inserta)
-- =============================================================================

CREATE OR REPLACE FUNCTION sp_registrar_caso(
    p_id_solicitante VARCHAR,
    p_id_centro INTEGER,
    p_id_ambito_legal INTEGER,
    p_fecha_recepcion DATE,
    p_estatus VARCHAR,
    p_sintesis TEXT,
    p_cant_beneficiarios INTEGER
) 
RETURNS VARCHAR AS $$
DECLARE
    v_siglas VARCHAR(10);
    v_anio INTEGER;
    v_mes INTEGER;
    v_periodo VARCHAR(2);
    v_consecutivo INTEGER;
    v_num_caso_generado VARCHAR(50);
    v_prefijo_busqueda VARCHAR(50);
BEGIN
    -- 1. OBTENER SIGLAS DEL CENTRO (Ej: GY o CB)
    SELECT abreviatura INTO v_siglas 
    FROM centros 
    WHERE id_centro = p_id_centro;

    IF v_siglas IS NULL THEN
        RAISE EXCEPTION 'El centro con ID % no existe', p_id_centro;
    END IF;

    -- 2. CALCULAR PERIODO Y AÑO
    -- Regla: Feb(20), Mar-Jul(25), Ago(30), Resto(15)
    v_anio := EXTRACT(YEAR FROM p_fecha_recepcion);
    v_mes := EXTRACT(MONTH FROM p_fecha_recepcion);

    IF v_mes = 2 THEN
        v_periodo := '20'; -- Intensivo Febrero
    ELSIF v_mes >= 3 AND v_mes <= 7 THEN
        v_periodo := '25'; -- Semestre Mar-Jul
    ELSIF v_mes = 8 THEN
        v_periodo := '30'; -- Intensivo Agosto
    ELSE
        v_periodo := '15'; -- Semestre Sep-Ene
    END IF;

    -- 3. GENERAR CONSECUTIVO
    -- Formato: SIGLAS-AÑO-PERIODO-XXXX (Ej: GY-2025-15-)
    v_prefijo_busqueda := v_siglas || '-' || v_anio || '-' || v_periodo || '-';

    -- Buscamos el último número registrado con ese prefijo y le sumamos 1
    -- SPLIT_PART rompe el string por guiones y toma la 4ta parte (el número)
    SELECT COALESCE(MAX(CAST(SPLIT_PART(num_caso, '-', 4) AS INTEGER)), 0) + 1
    INTO v_consecutivo
    FROM casos
    WHERE num_caso LIKE v_prefijo_busqueda || '%';

    -- Armamos el ID final con relleno de ceros (padding 4 dígitos)
    v_num_caso_generado := v_prefijo_busqueda || LPAD(v_consecutivo::TEXT, 4, '0');

    -- 4. INSERTAR EL CASO (Sin el campo trámite)
    INSERT INTO casos (
        num_caso, 
        fecha_recepcion, 
        cant_beneficiarios, 
        estatus, 
        sintesis, 
        id_centro, 
        id_ambito_legal, 
        id_solicitante
    ) VALUES (
        v_num_caso_generado, 
        p_fecha_recepcion, 
        p_cant_beneficiarios, 
        p_estatus, 
        p_sintesis, 
        p_id_centro, 
        p_id_ambito_legal, 
        p_id_solicitante
    );

    -- 5. RETORNAR EL ID GENERADO
    RETURN v_num_caso_generado;

END;
$$ LANGUAGE plpgsql;
