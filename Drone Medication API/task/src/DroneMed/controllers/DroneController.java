package DroneMed.controllers;

import DroneMed.models.Drone;
import DroneMed.models.DroneModel;
import DroneMed.models.DroneState;
import DroneMed.responses.ResponseHandler;
import DroneMed.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drones")
public class DroneController {

    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("/create_drone")
    public ResponseEntity<?> createDrone(@RequestBody Drone drone) {
        String res = droneService.createDrone(drone);
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @PostMapping("/create_drones")
    public ResponseEntity<?> createDrones(@RequestBody List<Drone> drones) {
        List<String> res = droneService.createDrones(drones);
        return ResponseHandler.responseBuilder("Drones created successfully.",HttpStatus.OK, res);
    }

    @PutMapping("/update_drone")
    public ResponseEntity<?> updateDrone(@RequestBody Drone drone) {
        String res = droneService.updateDrone(drone);
        if (res.contains("not found") ){
            return ResponseHandler.responseBuilder(res, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete_drone/{serialNumber}")
    public ResponseEntity<?> deleteDrone(@PathVariable String serialNumber) {
        String response = droneService.deleteDrone(serialNumber);
        if(response.contains("not found.")) {
            return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    @GetMapping("/get_drone/{serialNumber}")
    public ResponseEntity<?> getDrone(@PathVariable String serialNumber) {
        Drone drone = droneService.getDrone(serialNumber);
        if(drone != null) {
            return ResponseHandler.responseBuilder("Drone fetched successfully.", HttpStatus.OK, drone);
        }
        return ResponseHandler.responseBuilder(
                "The drone with serial number " + serialNumber + " was not found.", HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/get_all_drones")
    public ResponseEntity<?> getAllDrones() {
        List<Drone> drones = droneService.getAllDrones();
        if(!drones.isEmpty()) {
            return ResponseHandler.responseBuilder("All drones fetched successfully.", HttpStatus.OK, drones);
        }
        return ResponseHandler.responseBuilder("The drone list is empty.", HttpStatus.OK);
    }

    @GetMapping("/get_drones_by_state/{state}")
    public ResponseEntity<?> getDronesByState(@PathVariable DroneState state) {
        List<Optional<Drone>> drones = droneService.findDroneByState(state);
        if(drones.isEmpty()) {
            return ResponseHandler.responseBuilder("The drones list by state " + state + " is empty.", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("Drones fetched by state.", HttpStatus.OK, drones);
    }

    @GetMapping("/get_drones_by_model/{model}")
    public ResponseEntity<?> getDronesByModel(@PathVariable DroneModel model) {
        List<Optional<Drone>> drones = droneService.findDronesByModel(model);
        if(drones.isEmpty()) {
            return ResponseHandler.responseBuilder("The drones list by model " + model + " is empty.", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("Drones fetched by model.", HttpStatus.OK, drones);
    }

    @GetMapping("/get_drones_by_charge/{percentage}")
    public ResponseEntity<?> getDronesByCharge(@PathVariable int percentage) {
        List<Optional<Drone>> drones = droneService.findDronesByBatteryCapacityAfter(percentage);
        if(drones.isEmpty()) {
            return ResponseHandler.responseBuilder("The drones list by percentage " + percentage + " is empty.", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("Drones fetched by charge.", HttpStatus.OK, drones);
    }

}
