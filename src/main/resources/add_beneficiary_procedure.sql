CREATE OR REPLACE FUNCTION sp_agregar_beneficiario(
    p_id_beneficiario VARCHAR,
    p_num_caso VARCHAR,
    p_tipo_beneficiario VARCHAR,
    p_parentesco VARCHAR
)
RETURNS VARCHAR AS $$
BEGIN
    INSERT INTO beneficiarios_casos (id_beneficiario, num_caso, tipo_beneficiario, parentesco)
    VALUES (p_id_beneficiario, p_num_caso, p_tipo_beneficiario, p_parentesco);
    
    RETURN 'Beneficiario agregado exitosamente';
EXCEPTION
    WHEN unique_violation THEN
        RETURN 'El beneficiario ya está asociado a este caso';
    WHEN foreign_key_violation THEN
        RETURN 'El usuario o el caso no existen';
END;
$$ LANGUAGE plpgsql;
