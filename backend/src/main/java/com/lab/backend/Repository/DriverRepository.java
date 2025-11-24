package com.lab.backend.Repository;

import com.lab.backend.Entities.DriverEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

@Repository
public class DriverRepository {

    private Sql2o sql2o;

    public DriverRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    //CRUD Operations

    public DriverEntity CreateDriver(DriverEntity driverEntity) {
        String sql = "INSERT INTO drivers (name, license_number, phone_number) VALUES (:name, :license_number, :phone_number)";
        Connection connection = null;
        try {
            connection = sql2o.open();
            Long id = connection.createQuery(sql, true)
                .addParameter("name", driverEntity.getName())
                .addParameter("last_name", driverEntity.getLast_name())
                .addParameter("email", driverEntity.getEmail())
                .addParameter("password", driverEntity.getPassword())
                .executeUpdate()
                .getKey(Long.class);
            return driverEntity;
        } catch (Sql2oException e) {
            System.err.println("Error al insertar el driver");
            throw new RuntimeException("No se pudo crear el driver");
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<DriverEntity> getAllDrivers() {
        String sql = "SELECT * FROM drivers";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                .executeAndFetch(DriverEntity.class);
        } catch (Sql2oException e) {
            System.err.println("Error al obtener los drivers");
            throw new RuntimeException("No se pudieron obtener los drivers");
        }
    }

    public DriverEntity getDriverById(int id) {
        String sql = "SELECT * FROM drivers WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(DriverEntity.class);
        } catch (Sql2oException e) {
            System.err.println("Error al obtener el driver por ID");
            throw new RuntimeException("No se pudo obtener el driver");
        }
    }

    public void updateDriver(int id, DriverEntity driverEntity) {
        String sql = "UPDATE drivers SET name = :name, license_number = :license_number, phone_number = :phone_number WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                .addParameter("name", driverEntity.getName())
                .addParameter("last_name", driverEntity.getLast_name())
                .addParameter("email", driverEntity.getEmail())
                .addParameter("password", driverEntity.getPassword())
                .executeUpdate();
        } catch (Sql2oException e) {
            System.err.println("Error al actualizar el driver");
            throw new RuntimeException("No se pudo actualizar el driver");
        }
    }

    public void deleteDriver(int id) {
        String sql = "DELETE FROM drivers WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
        } catch (Sql2oException e) {
            System.err.println("Error al eliminar el driver");
            throw new RuntimeException("No se pudo eliminar el driver");
        }
    }
}
