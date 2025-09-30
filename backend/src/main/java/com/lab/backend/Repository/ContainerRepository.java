package com.lab.backend.Repository;

import com.lab.backend.Entities.ContainerEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

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
}
