package com.lab.backend.Services;

import com.lab.backend.Repository.ContainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContainerServices {
    private ContainerRepository containerRepository;

    public ContainerServices(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    public List<Map<String, Object>> getProblematicContainersReport() {
        return containerRepository.getProblematicContainers();
    }

    public List<Map<String, Object>> getContainerDensityAnalysis() {
        return containerRepository.getMonthlyContainerDensityAnalysis();
    }

}