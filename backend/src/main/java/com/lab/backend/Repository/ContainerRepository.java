package com.lab.backend.Repository;

import com.lab.backend.Entities.ContainerEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

@Repository
public class ContainerRepository {

    private Sql2o sql2o;

    public ContainerRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public ContainerEntity CreateContainer(ContainerEntity containerEntity) {
        String sql = "INSERT INTO containers (id_waste, coord_x, coord_y, weight, status) VALUES (:id_waste, :coord_x, :coord_y, :weight, :status)";
        Connection connection = null;
        try {
            connection = sql2o.open();
            Long id = connection.createQuery(sql, true)
                    .addParameter("id_waste", containerEntity.getId_waste())
                    .addParameter("coord_x", containerEntity.getCoord_x())
                    .addParameter("coord_y", containerEntity.getCoord_y())
                    .addParameter("weight", containerEntity.getWeight())
                    .addParameter("status", containerEntity.getStatus())
                    .executeUpdate()
                    .getKey(Long.class);
            containerEntity.setId(id);
            return containerEntity;
        } catch (Exception e) {
            System.err.println("Error al insertar el container");
            throw new RuntimeException("No se pudo crear el container");
        } finally {
            if (connection != null) {
                connection.close();
            }



        }
    }

    //Sentencia 2: Obtiene los contenedores con problemas; es decir, aquellos que han sido recogidos por más de 3 conductores diferentes en el último año.
    //Entrada: -
    //Salida: Lista con id_container y el conteo de conductores únicos para cada contenedor problemático.

    public List<Map<String, Object>> getProblematicContainers() {
        String sql = "WITH PickUpsLastYear AS (" +  //CTE PickUpsLastYear
                "    SELECT id_container, id_route FROM pick_up_entity " +  //Selecciona id_container e id_route de pick_up_entity
                "    WHERE date_hour >= NOW() - INTERVAL '1 year'" +        //Filtra por los últimos 12 meses
                ") " +
                "SELECT " +     //Selecciona id_container y el conteo de conductores únicos
                "    py.id_container," +
                "    COUNT(DISTINCT re.id_driver) AS driverCount " +
                "FROM " +       //Desde la CTE PickUpsLastYear
                "    PickUpsLastYear AS py " +
                "JOIN " +       //Une con route_entity para obtener id_driver
                "    route_entity AS re ON py.id_route = re.id " +
                "GROUP BY " +
                "    py.id_container " +
                "HAVING " +
                "    COUNT(DISTINCT re.id_driver) > 3;";

        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql).executeAndFetchTable().asList();
        } catch (Exception e) {
            System.err.println("Error al obtener datos de contenedores: " + e.getMessage());
            throw new RuntimeException("No se pudo obtener la información", e);
        }
    }


    //Explicación en palabras de la sentencia SQL:
    /*
    1. Se crea la CTE PickUpsLastYear que es una tabla con id_container e id_route de la tabla pick_up_entity, filtrando solo las recolecciones de los últimos 12 meses.
    2. Entre las tablas PickUpsLastYear y route_entity, se hace una unión (JOIN) usando id_route para obtener id_driver.
    3. Se agrupan los resultados por id_container.
    4. Se cuenta el número de conductores únicos (DISTINCT id_driver) para cada contenedor.
    5. Finalmente, se filtran los resultados para incluir solo aquellos contenedores que han sido recogidos por más de 3 conductores diferentes en el último año.

    Esto se muestra con el SELECT de la línea 57 que muestra el id_container y el conteo final de conductores únicos como driverCount.
     */
}
