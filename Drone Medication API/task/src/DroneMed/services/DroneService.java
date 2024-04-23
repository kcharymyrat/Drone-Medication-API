package DroneMed.services;

import DroneMed.models.DroneModel;
import DroneMed.models.DroneState;
import DroneMed.repositories.DroneRepository;
import DroneMed.models.Drone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {
    private final DroneRepository repository;

    @Autowired
    public DroneService(DroneRepository repository) {
        this.repository = repository;
    }

    public List<Drone> getAllDrones() {
        return (List<Drone>) repository.findAll();
    }

    public String createDrone(Drone drone) {
        try {
            repository.save(drone);
            return "The drone with serial number " + drone.getSerialNumber() + " was created successfully.";
        } catch (Exception e) {
            return "The parameter you entered contains a null or invalid parameter, Please enter a valid drone parameter.";
        }
    }

    public String updateDrone(Drone drone) {
        try {
            if (repository.findBySerialNumber(drone.getSerialNumber()) == null) {
                return "The drone with serial number " + drone.getSerialNumber() + " was not found.";
            }
            repository.save(drone);
            return "The drone with serial number " + drone.getSerialNumber() + " was updated successfully.";
        } catch (Exception e) {
            return "The drone with serial number " + drone.getSerialNumber() + " was not found.";
        }
    }

    public String deleteDrone(String serialNumber) {
        try {
            repository.delete(repository.findBySerialNumber(serialNumber));
            return "The drone with serial number "+ serialNumber + " was deleted Successfully.";
        } catch (Exception e) {
            return "The drone with serial number " + serialNumber + " was not found.";
        }
    }

    public Drone getDrone(String serialNumber) {
        return repository.findBySerialNumber(serialNumber);
    }

    @Transactional
    public List<String> createDrones(List<Drone> drones) {
        List<String> res = new ArrayList<>();
        for (Drone drone : drones) {
            res.add(createDrone(drone));
        }
        return res;
    }

    public List<Optional<Drone>> findDroneByState(DroneState state) {
        return repository.findDronesByState(state);
    }

    public List<Optional<Drone>> findDronesByModel(DroneModel model) {
        return repository.findDronesByModel(model);
    }

    public List<Optional<Drone>> findDronesByBatteryCapacityAfter(int percentage) {
        return repository.findDronesByBatteryCapacityAfter(percentage);
    }

}
