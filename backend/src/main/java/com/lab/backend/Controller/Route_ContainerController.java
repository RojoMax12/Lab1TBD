package com.lab.backend.Controller;

import com.lab.backend.Entities.Route_ContainerEntity;
import com.lab.backend.Services.Route_ContainerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routecontainer")
public class Route_ContainerController {


    @Autowired
    private Route_ContainerServices routeContainerService;

    // Endpoint para crear un nuevo Route_Container
    @PostMapping("/create")
    public Route_ContainerEntity createRouteContainer(@RequestBody Route_ContainerEntity routeContainerEntity) {
        return routeContainerService.createRouteContainer(routeContainerEntity);
    }

    // Endpoint para obtener todos los Route_Containers
    @GetMapping("/")
    public List<Route_ContainerEntity> getAllRouteContainers() {
        return routeContainerService.getAllRouteContainers();
    }

    // Endpoint para obtener un Route_Container por ID
    @GetMapping("/{id}")
    public Route_ContainerEntity getRouteContainerById(@PathVariable Long id) {
        return routeContainerService.getRouteContainerById(id);
    }

    @GetMapping("/container/{id}")
    public List<Route_ContainerEntity> getRouteContainerByContainer(@PathVariable Long id) {
        return routeContainerService.getRouteContainersByRoute(id);
    }

    // Endpoint para eliminar un Route_Container por ID
    @DeleteMapping("/{id}")
    public void deleteRouteContainer(@PathVariable Long id) {
        routeContainerService.deleteRouteContainer(id);
    }
}
