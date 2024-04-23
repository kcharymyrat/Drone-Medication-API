package DroneMed.mappers;

import DroneMed.models.Medication;
import DroneMed.models.DroneDispatch;
import DroneMed.models.DroneDispatchDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DroneDispatchMapper {

    public DroneDispatchDTO droneDispatchToDroneDispatchDTO(DroneDispatch droneDispatch) {
        return new DroneDispatchDTO(
                droneDispatch.getDispatchId(),
                droneDispatch.isCancelled(),
                droneDispatch.getEstimatedTime(),
                droneDispatch.getDrone().getSerialNumber(),
                droneDispatch.getDrone().getState().toString(),
                droneDispatch.getUser().getName(),
                droneDispatch.getUser().getPhoneNumber(),
                droneDispatch.getMedications().stream()
                        .map(Medication::getCode).collect(Collectors.toList())
        );
    }

    public DroneDispatchDTO droneDispatchToDroneDispatchDTO(Optional<DroneDispatch> droneDispatch) {
        return new DroneDispatchDTO(
                droneDispatch.get().getDispatchId(),
                droneDispatch.get().isCancelled(),
                droneDispatch.get().getEstimatedTime(),
                droneDispatch.get().getDrone().getSerialNumber(),
                droneDispatch.get().getDrone().getState().toString(),
                droneDispatch.get().getUser().getName(),
                droneDispatch.get().getUser().getPhoneNumber(),
                droneDispatch.get().getMedications().stream()
                        .map(Medication::getCode).collect(Collectors.toList())
        );
    }
}
