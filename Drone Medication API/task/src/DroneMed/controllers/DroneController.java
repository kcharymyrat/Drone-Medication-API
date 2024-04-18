package DroneMed.controllers;

import DroneMed.models.Drone;
import DroneMed.responses.ResponseHandler;
import DroneMed.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
