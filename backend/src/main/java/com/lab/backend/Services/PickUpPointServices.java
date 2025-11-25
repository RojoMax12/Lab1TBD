package com.lab.backend.Services;

import com.lab.backend.Entities.PickUpPointEntity;
import com.lab.backend.Repository.PickUpPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickUpPointServices {

    private PickUpPointRepository pickUpPointRepository;

    public PickUpPointServices(PickUpPointRepository pickUpPointRepository) {
        this.pickUpPointRepository = pickUpPointRepository;
    }

    public List<PickUpPointEntity> getAllPickUpPoints() {
        return pickUpPointRepository.getAllPickUpPoints();
    }

    public PickUpPointEntity getPickUpPointById(Long id) {
        return pickUpPointRepository.getPickUpPointById(id);
    }

    public void deletePickUpPointById(Long id) {
        pickUpPointRepository.deletePickUpPoint(id);
    }


}
