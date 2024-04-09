package DroneMed.repositories;

import DroneMed.models.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication, String> {
    Medication findByCode(String code);
}
