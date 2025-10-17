package com.lab.backend.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.backend.Repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServices {

    private final RouteRepository routeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RouteServices(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void planificarRuta(List<Long> contenedorIds, Long idDriver, Long idCentral, Long idPickUpPoint) {
        try {
            String contenedoresJson = objectMapper.writeValueAsString(contenedorIds);
            routeRepository.planificarRuta(contenedoresJson, idDriver, idCentral, idPickUpPoint);
        } catch (Exception e) {
            System.err.println("Error en servicio planificarRuta: " + e.getMessage());
            throw new RuntimeException("Error al planificar ruta", e);
        }
    }
}
