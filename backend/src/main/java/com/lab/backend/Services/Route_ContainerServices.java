package com.lab.backend.Services;
import com.lab.backend.Entities.Route_ContainerEntity;
import com.lab.backend.Repository.Route_ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Route_ContainerServices {

    @Autowired
    private Route_ContainerRepository routeContainerRepository;

    // Crear un nuevo Route_Container
    public Route_ContainerEntity createRouteContainer(Route_ContainerEntity routeContainerEntity) {
        return routeContainerRepository.createRouteContainer(routeContainerEntity);
    }

    // Obtener todos los Route_Container
    public List<Route_ContainerEntity> getAllRouteContainers() {
        return routeContainerRepository.getAllRouteContainers();
    }

    // Obtener un Route_Container por su ID
    public Route_ContainerEntity getRouteContainerById(Long id) {
        return routeContainerRepository.getRouteContainerById(id);
    }

    // Eliminar un Route_Container
    public void deleteRouteContainer(Long id) {
        routeContainerRepository.deleteRouteContainer(id);
    }
}
