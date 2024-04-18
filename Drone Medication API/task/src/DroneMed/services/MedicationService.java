package DroneMed.services;

import DroneMed.models.Medication;
import DroneMed.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
            return "The medication with code " + medication.getCode() + " was created successfully.";
        } catch (Exception e) {
            return "The parameter you entered contains a null or invalid parameter, Please enter a valid medication parameter.";
        }
    }

   public String updateMedication(Medication medication) {
       try {
           if (repository.findByCode(medication.getCode()) == null) {
               return "The medication with code " + medication.getCode() + " was not found.";
           }
           repository.save(medication);
           return "The medication with code " + medication.getCode() + " was updated successfully.";
       } catch (Exception e) {
           return "The medication with code " + medication.getCode() + " was not found.";
       }
   }

   public String deleteMedication(String code) {
       try {
           repository.delete(repository.findByCode(code));
           return "The medication with code " + code + " was deleted Successfully.";
       } catch (Exception e) {
           return "The medication with code " + code + " was not found.";
       }
   }

    public Optional<Medication> getMedication(String value) {
        return Optional.ofNullable(repository.findByCode(value));
    }


    @Transactional
    public List<String> createMedications(List<Medication> medications) {
        List<String> res = new ArrayList<>();
        for (Medication m: medications) {
            res.add(createMedication(m));
        }
        return res;
    }
}
