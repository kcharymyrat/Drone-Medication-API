package DroneMed.controllers;

import DroneMed.models.Medication;
import DroneMed.models.MedicationDTO;
import DroneMed.responses.ResponseHandler;
import DroneMed.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/create_medication")
    public ResponseEntity<?> createMedication(@RequestBody Medication medication) {
        String res = medicationService.createMedication(medication);
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @PostMapping("/create_medications")
    public ResponseEntity<?> createMedications(@RequestBody List<Medication> medications) {
        List<String> res = medicationService.createMedications(medications);
        return ResponseHandler.responseBuilder("Medications created successfully.", HttpStatus.OK, res);
    }

    @PutMapping("/update_medication")
    public ResponseEntity<?> updateMedication(@RequestBody Medication medication) {
        String res = medicationService.updateMedication(medication);
        if(res.contains("not found.")) {
            return ResponseHandler.responseBuilder(res, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete_medication/{code}")
    public ResponseEntity<?> deleteMedication(@PathVariable String code) {
        String res = medicationService.deleteMedication(code);
        if(res.contains("not found.")) {
            return ResponseHandler.responseBuilder(res, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @GetMapping("/get_medication/{code}")
    public ResponseEntity<?> getMedication(@PathVariable String code) {
        Optional<Medication> medication = medicationService.getMedication(code);
        if (medication.isPresent()) {
            return ResponseHandler.responseBuilder("Medication fetched successfully.", HttpStatus.OK, medication);
        }
        return ResponseHandler.responseBuilder(
                "The medication with code " + code + " was not found.", HttpStatus.NOT_FOUND
        );
    }


    @GetMapping("/get_all_medications")
    public ResponseEntity<?> getAllMedication() {
        List<Medication> medications = medicationService.getAllMedication();
        if(medications.isEmpty()) {
            return ResponseHandler.responseBuilder("The medication list is empty.", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("All medications fetched successfully.", HttpStatus.OK, medications);
    }

    @GetMapping("/get_by_name/{name}")
    public ResponseEntity<?> getMedicationByName(@PathVariable String name) {
        List<MedicationDTO> medication = medicationService.getMedicationByName(name);
        if(medication.isEmpty()) {
            return ResponseHandler.responseBuilder("The medication list by name " + name + " is empty.", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("Medications fetched successfully.", HttpStatus.OK, medication);
    }

}
