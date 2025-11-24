package com.lab.backend.Controller;

import com.lab.backend.Entities.DriverEntity;
import com.lab.backend.Services.DriverServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private DriverServices driverServices;

    public DriverController(DriverServices driverServices) {
        this.driverServices = driverServices;
    }

    @PostMapping("/")
    public DriverEntity createDriver(@RequestBody DriverEntity driverEntity) {
        return driverServices.CreateDriver(driverEntity);
    }

    @GetMapping("/all")
    public List<DriverEntity> getAllDrivers() {
        return driverServices.getAllDrivers();
    }

    @GetMapping("/{id}")
    public DriverEntity getDriverById(@PathVariable int id) {
        return driverServices.getDriverById(id);
    }

    @PutMapping("/{id}")
    public void updateDriver(@PathVariable int id, @RequestBody DriverEntity driverEntity) {
        driverServices.updateDriver(id, driverEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable int id) {
        driverServices.deleteDriver(id);
    }
}