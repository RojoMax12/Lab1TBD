package com.lab.backend.Services;

import com.lab.backend.Entities.DriverEntity;
import com.lab.backend.Repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServices {

    private DriverRepository driverRepository;

    public DriverServices(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public DriverEntity CreateDriver(DriverEntity driverEntity) {
        return driverRepository.CreateDriver(driverEntity);
    }

    public List<DriverEntity> getAllDrivers() {
        return driverRepository.getAllDrivers();
    }

    public DriverEntity getDriverById(int id) {
        return driverRepository.getDriverById(id);
    }

    public void updateDriver(int id, DriverEntity driverEntity) {
        driverRepository.updateDriver(id, driverEntity);
    }

    public void deleteDriver(int id) {
        driverRepository.deleteDriver(id);
    }

    
}
