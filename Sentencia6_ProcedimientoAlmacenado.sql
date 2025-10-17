CREATE OR REPLACE PROCEDURE planificar_ruta(
    p_contenedores JSON,
    p_id_driver BIGINT,
    p_id_central BIGINT,
    p_id_pick_up_point BIGINT
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_contenedor_id BIGINT;
    v_error BOOLEAN := FALSE;
    rec RECORD;
    new_route_id BIGINT;
BEGIN
    -- Validar existencia y disponibilidad
    FOR rec IN SELECT json_array_elements_text(p_contenedores)::BIGINT AS id LOOP
        SELECT COUNT(*) INTO STRICT v_contenedor_id
        FROM container
        WHERE id = rec.id
          AND id NOT IN (
              SELECT id_container
              FROM pickup p
              JOIN route r ON r.id = p.id_route
              WHERE r.route_status = 'Pendiente'
          );

        IF v_contenedor_id = 0 THEN
            v_error := TRUE;
        END IF;
    END LOOP;

    IF v_error THEN
        RAISE EXCEPTION 'Error: uno o más contenedores no existen o están asignados a una ruta pendiente';
    END IF;

    -- Crear la nueva ruta
    INSERT INTO route (id_driver, date_hour, route_status, id_central, id_pick_up_point)
    VALUES (p_id_driver, NOW(), 'Pendiente', p_id_central, p_id_pick_up_point)
    RETURNING id INTO new_route_id;

    -- Asociar los contenedores
    FOR rec IN SELECT json_array_elements_text(p_contenedores)::BIGINT AS id LOOP
        INSERT INTO pickup (id_container, id_route, date_hour)
        VALUES (rec.id, new_route_id, NOW());
    END LOOP;
END;
$$;

