package DroneMed.services;

import DroneMed.repositories.DroneRepository;
import DroneMed.models.Drone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return "Drone with serial number " + drone.getSerialNumber() + " created successfully.";
        } catch (Exception e) {
            return "The parameters you entered are not valid drone parameters, please enter a valid drone parameter.";
        }
    }

    public String updateDrone(Drone drone) {
        try {
            if (repository.findBySerialNumber(drone.getSerialNumber()) == null) {
                return "Drone with serial number " + drone.getSerialNumber() + " not found.";
            }
            repository.save(drone);
            return "Drone with serial number " + drone.getSerialNumber() + " updated successfully.";
        } catch (Exception e) {
            return "Drone with serial number " + drone.getSerialNumber() + " not found.";
        }
    }

    public String deleteDrone(String serialNumber) {
        try {
            repository.delete(repository.findBySerialNumber(serialNumber));
            return "Drone with serial number "+ serialNumber + " deleted Successfully.";
        } catch (Exception e) {
            return "Drone with serial number " + serialNumber.toLowerCase() + " not found.";
        }
    }

    public Drone getDrone(String serialNumber) {
        return repository.findBySerialNumber(serialNumber);
    }

}
