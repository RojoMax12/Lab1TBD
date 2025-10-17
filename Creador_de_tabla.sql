
CREATE TABLE central (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    coord_x REAL NOT NULL,
    coord_y REAL NOT NULL
);

CREATE TABLE waste (
    id BIGSERIAL PRIMARY KEY,
    waste_type VARCHAR(100) NOT NULL
);

CREATE TABLE container (
    id BIGSERIAL PRIMARY KEY,
    id_waste BIGINT REFERENCES waste(id) ON DELETE CASCADE,
    coord_x REAL NOT NULL,
    coord_y REAL NOT NULL,
    weight REAL,
    status VARCHAR(50)
);

CREATE TABLE driver (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(150) NOT NULL
);

CREATE TABLE pickuppoint (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    coord_x REAL NOT NULL,
    coord_y REAL NOT NULL
);

CREATE TABLE route (
    id BIGSERIAL PRIMARY KEY,
    id_driver BIGINT REFERENCES driver(id) ON DELETE SET NULL,
    date_hour TIMESTAMP NOT NULL DEFAULT NOW(),
    route_status VARCHAR(50) NOT NULL,
    id_central BIGINT REFERENCES central(id) ON DELETE SET NULL,
    id_pick_up_point BIGINT REFERENCES pickuppoint(id) ON DELETE SET NULL
);

CREATE TABLE pickup (
    id BIGSERIAL PRIMARY KEY,
    id_container BIGINT REFERENCES container(id) ON DELETE CASCADE,
    id_route BIGINT REFERENCES route(id) ON DELETE CASCADE,
    date_hour TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =========================================================
-- 3️⃣ Insertar datos iniciales (para pruebas)
-- =========================================================

-- Centrales
INSERT INTO central (name, coord_x, coord_y) VALUES
('Central Norte', 10.5, 20.2),
('Central Sur', 30.1, 40.8);

-- Tipos de residuos
INSERT INTO waste (waste_type) VALUES
('Plástico'),
('Vidrio'),
('Orgánico');

-- Contenedores
INSERT INTO container (id_waste, coord_x, coord_y, weight, status) VALUES
(1, 12.3, 22.1, 50.0, 'Disponible'),
(2, 14.8, 25.7, 70.5, 'Disponible'),
(3, 15.2, 27.0, 60.0, 'Disponible'),
(1, 16.0, 28.5, 55.5, 'Disponible');

-- Conductores
INSERT INTO driver (name, last_name, email, password) VALUES
('Juan', 'Pérez', 'juan.perez@mail.com', '1234'),
('Ana', 'Gómez', 'ana.gomez@mail.com', 'abcd');

-- Puntos de recolección
INSERT INTO pickuppoint (name, coord_x, coord_y) VALUES
('Punto A', 18.1, 33.3),
('Punto B', 20.5, 35.7);

-- =========================================================
-- 5️⃣ Ejemplo de uso del procedimiento
-- =========================================================
-- (Asegúrate de haber creado previamente planificar_ruta)

--CALL planificar_ruta('[1,2,3]'::json, 1, 1, 1);

-- =========================================================
-- ✅ FIN DEL SCRIPT
-- =========================================================