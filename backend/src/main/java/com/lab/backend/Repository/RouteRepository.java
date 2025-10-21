package com.lab.backend.Repository;
import org.springframework.stereotype.Repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;
import java.util.Map;

@Repository
public class RouteRepository {
    private final Sql2o sql2o;

    public RouteRepository(Sql2o sql2o) {

        this.sql2o = sql2o;
    }

    public void planificarRuta(String contenedoresJson, Long idDriver, Long idCentral, Long idPickUpPoint) {
        String sql = "CALL planificar_ruta(:contenedores::json, :idDriver, :idCentral, :idPickUpPoint)";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("contenedores", contenedoresJson)
                    .addParameter("idDriver", idDriver)
                    .addParameter("idPickUpPoint", idPickUpPoint)
                    .addParameter("idCentral", idCentral)
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al ejecutar planificar_ruta: " + e.getMessage());
            throw new RuntimeException("Error al planificar ruta", e);
        }
    }

    // CONSULTA A DESARROLLAR N°1

    public List<Map<String, Object>> findDriverEfficiency() {
        String sql = """
            SELECT 
                d.name AS driver_name,
                d.last_name AS driver_last_name,
                ROUND(AVG(EXTRACT(EPOCH FROM (r.end_time - r.start_time)) / 3600), 2) AS average_time_hours
            FROM 
                route r
            JOIN 
                driver d ON r.id_driver = d.id
            WHERE 
                r.route_status ILIKE 'Finalizada'
                AND r.date_ >= NOW() - INTERVAL '6 months'
            GROUP BY 
                d.id, d.name, d.last_name
            ORDER BY 
                average_time_hours ASC;
        """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetchTable().asList();
        } catch (Exception e) {
            System.err.println("Error executing findDriverEfficiency: " + e.getMessage());
            throw new RuntimeException("Error calculating driver efficiency", e);
        }
    }

    // CONSULTA A DESARROLLAR N°3

    public List<Map<String, Object>> compareWastePerformance() {
        String sql = """
            WITH top_drivers AS (
                SELECT\s
                    r.id_driver,
                    COUNT(*) AS completed_routes
                FROM route r
                WHERE r.route_status ILIKE 'FINALIZADA'
                GROUP BY r.id_driver
                ORDER BY completed_routes DESC
                LIMIT 2
            ),
            driver_names AS (
                SELECT\s
                    d.id,
                    CONCAT(d.name, ' ', d.last_name) AS full_name
                FROM driver d
                WHERE d.id IN (SELECT id_driver FROM top_drivers)
            ),
            waste_per_driver AS (
                SELECT\s
                    r.id_driver,
                    w.waste_type,
                    SUM(c.weight) AS total_weight
                FROM route r
                JOIN pickup p ON r.id = p.id_route
                JOIN container c ON p.id_container = c.id
                JOIN waste w ON c.id_waste = w.id
                WHERE r.route_status ILIKE 'FINALIZADA'
                GROUP BY r.id_driver, w.waste_type
            )
            SELECT
                wp.waste_type,
                (SELECT full_name FROM driver_names ORDER BY id LIMIT 1 OFFSET 0) AS driver_a_name,
                MAX(CASE WHEN wp.id_driver = (SELECT id_driver FROM top_drivers LIMIT 1 OFFSET 0) THEN wp.total_weight END) AS driver_a_weight,
                (SELECT full_name FROM driver_names ORDER BY id LIMIT 1 OFFSET 1) AS driver_b_name,
                MAX(CASE WHEN wp.id_driver = (SELECT id_driver FROM top_drivers LIMIT 1 OFFSET 1) THEN wp.total_weight END) AS driver_b_weight
            FROM waste_per_driver wp
            GROUP BY wp.waste_type
            ORDER BY wp.waste_type;
        """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetchTable().asList();
        } catch (Exception e) {
            System.err.println("Error executing compareWastePerformance: " + e.getMessage());
            throw new RuntimeException("Error comparing performance by waste type", e);
        }
    }

    // Metodo para encontrar rutas ineficientes (Consulta a desarrollar N°4)
    public List<Map<String, Object>> findInefficientRoutes() {
        String sql = """
            WITH durations AS (
                SELECT
                    r.id AS route_id,
                    COUNT(p.id_container) AS container_count,
                    EXTRACT(EPOCH FROM (r.end_time - r.start_time)) / 3600 AS duration_hours
                FROM route r
                JOIN pickup p ON r.id = p.id_route
                WHERE r.route_status ILIKE 'FINALIZADA'
                GROUP BY r.id, r.start_time, r.end_time
            ),
            percentile AS (
                SELECT
                    PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY duration_hours) AS p25
                FROM durations
            )
            SELECT
                d.route_id,
                d.container_count,
                ROUND(d.duration_hours, 2) AS duration_hours
            FROM durations d, percentile p
            WHERE d.duration_hours < p.p25
            ORDER BY d.container_count DESC
            LIMIT 5;
        """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetchTable().asList();
        }
    }

}