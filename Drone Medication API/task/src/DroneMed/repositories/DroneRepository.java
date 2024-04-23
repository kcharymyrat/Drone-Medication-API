package DroneMed.repositories;

import DroneMed.models.Drone;
import DroneMed.models.DroneModel;
import DroneMed.models.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, String> {
    Drone findBySerialNumber(String serialNumber);
    List<Optional<Drone>> findDronesByModel(DroneModel model);
    List<Optional<Drone>> findDronesByState(DroneState state);
    List<Optional<Drone>> findDronesByBatteryCapacityAfter(int percentage);
}
