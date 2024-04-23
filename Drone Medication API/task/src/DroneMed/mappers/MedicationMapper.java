package DroneMed.mappers;

import DroneMed.models.Medication;
import DroneMed.models.MedicationDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicationMapper {
    public MedicationDTO medicationToMedicationDTO(Medication medication) {
        long dispatchID = medication.getDroneDispatch() == null ? -1L : medication.getDroneDispatch().getDispatchId();
        return new MedicationDTO(
                medication.getCode(),
                medication.getName(),
                medication.getWeight(),
                medication.getImageURL(),
                dispatchID
        );
    }

    public MedicationDTO medicationToMedicationDTO(Optional<Medication> medication) {
        long dispatchID = medication.get().getDroneDispatch() == null ? -1L : medication.get().getDroneDispatch().getDispatchId();
        return new MedicationDTO(
                medication.get().getCode(),
                medication.get().getName(),
                medication.get().getWeight(),
                medication.get().getImageURL(),
                dispatchID
        );
    }
}
