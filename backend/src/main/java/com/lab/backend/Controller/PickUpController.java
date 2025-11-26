package com.lab.backend.Controller;

import com.lab.backend.Entities.PickUpEntity;
import com.lab.backend.Services.PickUpServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pickups")
public class PickUpController {

    private final PickUpServices pickUpServices;

    public PickUpController(PickUpServices pickUpServices) {
        this.pickUpServices = pickUpServices;
    }

    @PostMapping("/")
    public PickUpEntity createPickUp(@RequestBody PickUpEntity pickUp) {
        return pickUpServices.CreatePickUp(pickUp);
    }

    @GetMapping("/")
    public List<PickUpEntity> getAllPickUps() {
        return pickUpServices.getAllPickUps();
    }

    @GetMapping("/{id}")
    public PickUpEntity getPickUpById(@PathVariable Long id) {
        return pickUpServices.getPickUpById(id);
    }

    @PutMapping("/{id}")
    public void updatePickUp(@PathVariable Long id, @RequestBody PickUpEntity pickUp) {
        pickUpServices.updatePickUp(id, pickUp);
    }

    @DeleteMapping("/{id}")
    public void deletePickUp(@PathVariable Long id) {
        pickUpServices.deletePickUp(id);
    }
}
