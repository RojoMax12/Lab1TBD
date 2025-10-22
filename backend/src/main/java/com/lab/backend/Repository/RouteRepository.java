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

    // Sentencia 1: Calcula la eficiencia promedio de cada conductor,
    // medida como el tiempo medio (en horas) que tarda en completar sus rutas finalizadas
    // durante los últimos seis meses.
    // Entrada: -
    // Salida: Lista con el nombre, apellido y el tiempo promedio (average_time_hours)
    //         de cada conductor, ordenada de menor a mayor tiempo promedio.

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

    // Explicación en palabras de la sentencia SQL:
    /*
    1. La consulta obtiene los datos de las tablas `route` y `driver` mediante un JOIN
       usando el identificador del conductor (`id_driver`).
    2. Se filtran las rutas cuyo estado (`route_status`) sea 'Finalizada'
       y cuya fecha (`date_`) sea dentro de los últimos 6 meses.
    3. Para cada conductor, se calcula el tiempo promedio de duración de sus rutas:
       - `r.end_time - r.start_time` obtiene la duración del recorrido.
       - `EXTRACT(EPOCH FROM ...)` convierte esa duración a segundos.
       - Se divide por 3600 para obtener el valor en horas.
       - `AVG()` obtiene el promedio de duración entre todas sus rutas.
       - `ROUND(..., 2)` redondea a dos decimales.
    4. Se agrupa por el ID, nombre y apellido del conductor para aplicar el promedio
       correctamente a cada uno.
    5. Finalmente, se ordena por `average_time_hours ASC`.
    */



    // Sentencia 3: Obtiene la cantidad total de residuos recolectados por tipo,
    // comparando los dos conductores con mayor número de rutas finalizadas.
    // Entrada: -
    // Salida: Lista con el tipo de residuo (waste_type) y, para cada tipo,
    //          el nombre y el total recolectado por el conductor 1 (driver_a)
    //          y el conductor 2 (driver_b).

    public List<Map<String, Object>> compareWastePerformance() {
        String sql = """
            WITH ranked_drivers AS (
              SELECT
                r.id_driver,
                d.name || ' ' || d.last_name AS full_name,
                COUNT(*) AS completed_routes,
                ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC, r.id_driver) AS rn
              FROM route r
              JOIN driver d ON d.id = r.id_driver
              WHERE r.route_status ILIKE 'FINALIZADA'
                AND r.id_driver IS NOT NULL
              GROUP BY r.id_driver, d.name, d.last_name
              ORDER BY completed_routes DESC
              LIMIT 2
            ),
            waste_per_driver AS (
              SELECT
                r.id_driver,
                w.waste_type,
                SUM(COALESCE(c.weight, 0)) AS total_weight
              FROM route r
              JOIN pickup p ON p.id_route = r.id
              JOIN container c ON c.id = p.id_container
              JOIN waste w ON w.id = c.id_waste
              WHERE r.route_status ILIKE 'FINALIZADA'
              GROUP BY r.id_driver, w.waste_type
            )
            SELECT
              wt.waste_type,
              (SELECT full_name FROM ranked_drivers WHERE rn = 1) AS driver_a_name,
              (SELECT total_weight
                 FROM waste_per_driver
                 WHERE id_driver = (SELECT id_driver FROM ranked_drivers WHERE rn = 1)
                   AND waste_type = wt.waste_type
              ) AS driver_a_weight,
              (SELECT full_name FROM ranked_drivers WHERE rn = 2) AS driver_b_name,
              (SELECT total_weight
                 FROM waste_per_driver
                 WHERE id_driver = (SELECT id_driver FROM ranked_drivers WHERE rn = 2)
                   AND waste_type = wt.waste_type
              ) AS driver_b_weight
            FROM (
              SELECT DISTINCT waste_type FROM waste_per_driver
            ) wt
            ORDER BY wt.waste_type;
        """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetchTable().asList();
        } catch (Exception e) {
            System.err.println("Error executing compareWastePerformance: " + e.getMessage());
            throw new RuntimeException("Error comparing performance by waste type", e);
        }
    }

    // Explicación en palabras de la sentencia SQL:
    /*
    1. Se crea la CTE `ranked_drivers`:
       - Cuenta cuántas rutas tiene cada conductor con estado 'FINALIZADA'.
       - Usa `ROW_NUMBER()` para asignar el puesto 1 al conductor con más rutas y 2 al segundo.
       - Se limitan los resultados a los dos primeros (LIMIT 2).
    2. Se crea la CTE `waste_per_driver`:
       - Calcula la suma total del peso (weight) de los residuos recolectados
         por cada conductor y tipo de residuo (`waste_type`), considerando solo
         rutas finalizadas.
       - Usa `SUM(COALESCE(c.weight, 0))` para asegurar que los valores nulos
         no afecten el total.
    3. Se listan todos los tipos de residuo encontrados. Para cada tipo, se buscan:
            a) El nombre y peso total del conductor top 1 (rn=1).
            b) El nombre y peso total del conductor top 2 (rn=2).
       - Si un conductor no recolectó cierto tipo de residuo, su columna de peso
         aparece como NULL.
    4. El resultado muestra una tabla comparativa del total de residuos por tipo entre
       los dos conductores con más rutas completadas.
    */



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

    // Metodo para la actualización masiva de contenedores (Sentencia N°7)
    public void updateContainerWeight(Long routeId, double newWeight) {
        String sql = "CALL actualizar_peso_contenedores(:routeId, :newWeight)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("routeId", routeId)
                    .addParameter("newWeight", newWeight)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating container weights: " + e.getMessage());
        }
    }

}