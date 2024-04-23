package DroneMed.services;

import DroneMed.mappers.DroneDispatchMapper;
import DroneMed.mappers.MedicationMapper;
import DroneMed.models.*;
import DroneMed.repositories.DroneDispatchRepository;
import DroneMed.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneDispatchService {

    private final MedicationRepository medicationRepository;
    private final DroneDispatchRepository droneDispatchRepository;

    private final DroneService droneService;
    private final UserService userService;

    private final DroneDispatchMapper droneDispatchMapper;
    private final ScheduledTask scheduledTask;


    @Autowired
    public DroneDispatchService(
            MedicationRepository medicationRepository,
            DroneDispatchRepository droneDispatchRepository,
            DroneService droneService,
            UserService userService,
            DroneDispatchMapper droneDispatchMapper,
            ScheduledTask scheduledTask
    ) {
        this.medicationRepository = medicationRepository;
        this.droneDispatchRepository = droneDispatchRepository;
        this.droneService = droneService;
        this.userService = userService;
        this.droneDispatchMapper = droneDispatchMapper;
        this.scheduledTask = scheduledTask;
    }

    public String dispatchDrone(DroneDispatch droneDispatch) {

        Drone drone = droneDispatch.getDrone();
        String droneNullText = "----Drone dispatch information is missing a drone! It should include details about the actual drone being dispatched.";
        if(drone == null) return droneNullText;

        List<Medication> medications = droneDispatch.getMedications();
        String medicationsNullText = "----Drone dispatch information is missing medication details! It should include details about the actual medications being dispatched.";
        if(medications == null) return medicationsNullText;

        UserAccount userAccount = droneDispatch.getUser();
        String userNullText = "-----Drone dispatch information is missing user account details! It should include requesting user account information.";
        if(userAccount == null) return userNullText;


        int weight = getTotalWeight(droneDispatch.getDrone(), droneDispatch.getMedications());
        String weightLimitExceedsText = "Drone selected cannot carry medications, You may reduce the number of medications or choose another drone with a larger capacity.";
        if(weight == -1) return weightLimitExceedsText;

        int batteryCapacity = droneDispatch.getDrone().getBatteryCapacity();
        int batteryConsumption = droneDispatch.getEstimatedTime().toSecondOfDay()  * 10 * 2;
        String notEnoughBatteryText = "Drone selected does not have enough battery charge to deliver medication.";
        if(batteryCapacity < batteryConsumption) return notEnoughBatteryText;

        String res = checkMedicationsAvailability(droneDispatch);
        if(res.contains("not found.")) return res;

        Optional<UserAccount> user = userService.getUser(droneDispatch.getUser().getPhoneNumber());
        if(user.isEmpty()) return "The user with phone number " + droneDispatch.getUser().getPhoneNumber() + " was not found.";

        Drone exactDrone = droneService.getDrone(droneDispatch.getDrone().getSerialNumber());
        if(exactDrone == null) return "The drone with serial number " + droneDispatch.getDrone().getSerialNumber() +  " was not found.";

        droneDispatch.setTimestamp(Timestamp.from(Instant.now()));
        droneDispatchRepository.save(droneDispatch);

        System.out.println("First saved =" + droneDispatch.toString());

        drone = droneDispatch.getDrone();
        drone.setCurrentWeight(weight);
        var response = droneService.updateDrone(drone);
        if(response.contains("not found.")) return response;

        for(Medication m: droneDispatch.getMedications()) {
            Optional<Medication> existingMedication = medicationRepository.findById(m.getCode());
            medicationRepository.save(existingMedication.get());
        }

        scheduledTask.droneDispatchedStateTask(droneDispatch);

        return "Drone dispatched successfully.";
    }


    public Optional<DroneDispatchDTO> getDispatch(Long dispatchId) {
        System.out.println("dispatchDrone: " + droneDispatchRepository.findById(dispatchId).toString());
        return droneDispatchRepository.findById(dispatchId).map(droneDispatchMapper::droneDispatchToDroneDispatchDTO);
    }


    public List<DroneDispatchDTO> getAllDispatch() {
        List<DroneDispatch> dispatches = (List<DroneDispatch>) droneDispatchRepository.findAll();
        return dispatches.stream().map(droneDispatchMapper::droneDispatchToDroneDispatchDTO).collect(Collectors.toList());
    }

    private int getTotalWeight(Drone drone, List<Medication> medications) {
        int totalWeight = 0;
        for(Medication m: medications) {
            totalWeight += m.getWeight();
        }
        if(totalWeight >= drone.getMaxWeight()) return -1;
        return totalWeight;
    }


    private String checkMedicationsAvailability(DroneDispatch droneDispatch) {
        for (Medication m : droneDispatch.getMedications()) {
            Optional<Medication> existingMedication = medicationRepository.findById(m.getCode());
            if(existingMedication.isPresent()) {
                existingMedication.get().setDroneDispatch(droneDispatch);
            }
            else {
                return "The medication with code " + m.getCode() + " was not found.";
            }
        }
        return "Medication list available.";
    }

}
