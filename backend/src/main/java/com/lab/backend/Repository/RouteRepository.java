package com.lab.backend.Repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.stereotype.Repository;

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
                    .addParameter("idCentral", idCentral)
                    .addParameter("idPickUpPoint", idPickUpPoint)
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al ejecutar planificar_ruta: " + e.getMessage());
            throw new RuntimeException("Error al planificar ruta", e);
        }
    }
}
