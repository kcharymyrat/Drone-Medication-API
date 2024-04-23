package DroneMed.repositories;

import DroneMed.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, String> {
    Medication findByCode(String code);
    List<Optional<Medication>> findByName(String name);

}
