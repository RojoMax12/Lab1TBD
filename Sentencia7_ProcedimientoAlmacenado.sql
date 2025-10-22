-- Crear el procedimiento
CREATE OR REPLACE PROCEDURE actualizar_peso_contenedores(
    p_id_ruta BIGINT,     -- ID de la ruta
    p_nuevo_peso DOUBLE PRECISION     -- Nuevo valor de peso a asignar
)
LANGUAGE plpgsql          -- Especificamos que usamos PL/pgSQL 
AS $$
BEGIN
    -- si el peso es negativo, mostrar error
    IF p_nuevo_peso < 0 THEN
        RAISE EXCEPTION 'El peso no puede ser negativo. Se revierte la transacción.';
    END IF;

    -- Actualizar todos los contenedores asociados a la ruta dada
    UPDATE container
    SET weight = p_nuevo_peso
    WHERE id IN (
        SELECT id_container
        FROM pickup
        WHERE id_route = p_id_ruta
    );

    -- Mostrar un mensaje en consola si todo salió bien
    RAISE NOTICE 'Contenedores de la ruta % actualizados correctamente a peso % kg', p_id_ruta, p_nuevo_peso;
END;
$$;

SELECT id, weight FROM container;

