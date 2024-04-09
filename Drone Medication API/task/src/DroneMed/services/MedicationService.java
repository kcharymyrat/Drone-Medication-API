package DroneMed.services;

import DroneMed.models.Medication;
import DroneMed.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    private final MedicationRepository repository;

    @Autowired
    public MedicationService(MedicationRepository repository) {
        this.repository = repository;
    }


    public List<Medication> getAllMedication() {
        return (List<Medication>) repository.findAll();
    }

    public String createMedication(Medication medication) {
        try {
            repository.save(medication);
            return "Medication with code " + medication.getCode() + " created successfully.";
        } catch (Exception e) {
            return "The parameters you entered are not valid medication parameters, please enter a valid medication parameter.";
        }
    }

   public String updateMedication(Medication medication) {
       try {
           if (repository.findByCode(medication.getCode()) == null) {
               return "Medication with code " + medication.getCode() + " not found.";
           }
           repository.save(medication);
           return "Medication with code " + medication.getCode() + " updated successfully.";
       } catch (Exception e) {
           return "Medication with code " + medication.getCode() + " not found.";
       }
   }

   public String deleteMedication(String code) {
       try {
           repository.delete(repository.findByCode(code));
           return "Medication with code " + code + " deleted Successfully.";
       } catch (Exception e) {
           return "Medication with code " + code + " not found.";
       }
   }

    public Optional<Medication> getMedication(String value) {
        return Optional.ofNullable(repository.findByCode(value));
    }
}
