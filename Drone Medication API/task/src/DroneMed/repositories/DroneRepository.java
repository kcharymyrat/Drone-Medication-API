package DroneMed.repositories;

import DroneMed.models.Drone;
import org.springframework.data.repository.CrudRepository;

public interface DroneRepository extends CrudRepository<Drone, String> {
    Drone findBySerialNumber(String serialNumber);
}
