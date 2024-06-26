package DroneMed.repositories;

import DroneMed.models.DroneDispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DroneDispatchRepository extends JpaRepository<DroneDispatch, Long> {
}
