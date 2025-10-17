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

    // Método para encontrar rutas ineficientes (Consulta a desarrollar N°4)
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