package DroneMed.controllers;

import DroneMed.models.DroneDispatch;
import DroneMed.models.DroneDispatchDTO;
import DroneMed.responses.ResponseHandler;
import DroneMed.services.DroneDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dispatch")
public class DispatchController {

    private final DroneDispatchService droneDispatchService;

    @Autowired
    public DispatchController(DroneDispatchService droneDispatchService) {
        this.droneDispatchService = droneDispatchService;
    }

    @PostMapping("/dispatch_drone")
    public ResponseEntity<?> dispatchDrone(@RequestBody DroneDispatch droneDispatch) {
        String response = droneDispatchService.dispatchDrone(droneDispatch);
        if(response.contains("not found.")) {
            return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    @GetMapping("/get_dispatch/{dispatchId}")
    public ResponseEntity<?> getDispatch(@PathVariable Long dispatchId) {
        Optional<DroneDispatchDTO> droneDispatch = droneDispatchService.getDispatch(dispatchId);
        if(droneDispatch.isPresent()) {
            return ResponseHandler.responseBuilder("The drone Dispatched fetched successfully.", HttpStatus.OK, droneDispatch);
        }
        return ResponseHandler.responseBuilder("The drone Dispatched with id " + dispatchId + " was not found.", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/get_all_dispatch")
    public ResponseEntity<?> getAllDispatch() {
        List<DroneDispatchDTO> droneDispatch = droneDispatchService.getAllDispatch();
        if(droneDispatch.isEmpty()) {
            return ResponseHandler.responseBuilder("The drone dispatch list is empty.", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("All Drone Dispatched fetched successfully.", HttpStatus.OK, droneDispatch);
    }

}
