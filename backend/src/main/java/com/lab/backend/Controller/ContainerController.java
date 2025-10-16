package com.lab.backend.Controller;

import com.lab.backend.Services.ContainerServices;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/containers")
public class ContainerController {
    private final ContainerServices containerService;

    public ContainerController(ContainerServices containerService) {
        this.containerService = containerService;
    }

    @GetMapping("/problematic")
    public List<Map<String, Object>> getProblematicContainers() {
        return containerService.getProblematicContainersReport();
    }

    @GetMapping("/density-analysis") // Nuevo endpoint
    public List<Map<String, Object>> getContainerDensityAnalysisReport() {
        return containerService.getContainerDensityAnalysis();
    }
}
