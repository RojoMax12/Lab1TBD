package com.lab.backend.Controller;

import com.lab.backend.Services.RouteServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Route")
public class RouteController {

    private final RouteServices routeService;

    public RouteController(RouteServices routeService) {
        this.routeService = routeService;
    }

    public static class PlanificarRutaRequest {
        public List<Long> contenedores;
        public Long idDriver;
        public Long idCentral;
        public Long idPickUpPoint;
    }

    @PostMapping("/PlanificarRuta")
    public String planificarRuta(@RequestBody PlanificarRutaRequest request) {
        try {
            routeService.planificarRuta(
                    request.contenedores,
                    request.idDriver,
                    request.idCentral,
                    request.idPickUpPoint
            );
            return "✅ Ruta planificada exitosamente";
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    @GetMapping("/inefficient")
    public ResponseEntity<List<Map<String, Object>>> getInefficientRoutes() {
        List<Map<String, Object>> inefficientRoutes = routeService.findInefficientRoutes();
        return ResponseEntity.ok(inefficientRoutes);
    }

}
