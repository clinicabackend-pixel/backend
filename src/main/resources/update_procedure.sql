CREATE OR REPLACE FUNCTION sp_actualizar_caso(
    p_num_caso VARCHAR,
    p_id_solicitante VARCHAR,
    p_id_centro INTEGER,
    p_id_ambito_legal INTEGER,
    p_fecha_recepcion DATE,
    p_estatus VARCHAR,
    p_sintesis TEXT,
    p_cant_beneficiarios INTEGER
)
RETURNS VARCHAR AS $$
BEGIN
    UPDATE casos
    SET 
        id_solicitante = p_id_solicitante,
        id_centro = p_id_centro,
        id_ambito_legal = p_id_ambito_legal,
        fecha_recepcion = p_fecha_recepcion,
        estatus = p_estatus,
        sintesis = p_sintesis,
        cant_beneficiarios = p_cant_beneficiarios
    WHERE num_caso = p_num_caso;

    IF FOUND THEN
        RETURN 'Caso actualizado exitosamente';
    ELSE
        RETURN 'Caso no encontrado';
    END IF;
END;
$$ LANGUAGE plpgsql;


