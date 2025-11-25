package com.lab.backend.Repository;

import com.lab.backend.Entities.PickUpPointEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

@Repository
public class PickUpPointRepository {
    private Sql2o sql2o;

    public PickUpPointRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public PickUpPointEntity createPickUpPoint(PickUpPointEntity pickuppoint) {
        String sql = "INSERT INTO pickUpPoints (name, coord_x, coord_y) VALUES (:name, :coord_x, :coord_y)";
        Connection connection = null;
        try {
            connection = sql2o.open();
            Long id = connection.createQuery(sql, true)
                    .addParameter("name", pickuppoint.getName())
                    .addParameter("coord_x", pickuppoint.getCoord_x())
                    .addParameter("coord_y", pickuppoint.getCoord_y())
                    .executeUpdate()
                    .getKey(Long.class);
            pickuppoint.setId(id);
            return pickuppoint;
        } catch (Exception e) {
            System.err.println("Error al insertar pick up point: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el pick up point", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<PickUpPointEntity> getAllPickUpPoints() {
        String sql = "SELECT * FROM pickUpPoints";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(PickUpPointEntity.class);
        } catch (Sql2oException e) {
            System.err.println("Error al obtener los pick up points: ");
            throw new RuntimeException("No se pudieron obtener los pick up points");
        }
    }

    public PickUpPointEntity getPickUpPointById(Long id) {
        String sql = "SELECT * FROM pickUpPoints WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(PickUpPointEntity.class);
        } catch (Sql2oException e) {
            System.err.println("Error al obtener pick up point por id: ");
            throw new RuntimeException("No se pudo obtener el pick up point por id");
        }
    }

    public void updatePickUpPoint(PickUpPointEntity pickuppoint) {
        String sql = "UPDATE pickUpPoints SET name = :name, coord_x = :coord_x, coord_y = :coord_y WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("name", pickuppoint.getName())
                    .addParameter("coord_x", pickuppoint.getCoord_x())
                    .addParameter("coord_y", pickuppoint.getCoord_y())
                    .addParameter("id", pickuppoint.getId())
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.err.println("Error al actualizar pick up point: ");
            throw new RuntimeException("No se pudo actualizar el pick up point");
        }
    }

    public void deletePickUpPoint(Long id) {
        String sql = "DELETE FROM pickUpPoints WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.err.println("Error al eliminar pick up point: ");
            throw new RuntimeException("No se pudo eliminar el pick up point");
        }
    }
}
