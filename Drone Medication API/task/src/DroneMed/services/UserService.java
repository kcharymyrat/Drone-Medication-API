package DroneMed.services;

import DroneMed.models.Drone;
import DroneMed.models.UserAccount;
import DroneMed.repositories.DroneRepository;
import DroneMed.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserAccountRepository repository;

    @Autowired
    public UserService(UserAccountRepository repository) {
        this.repository = repository;
    }

    public List<UserAccount> getAllUsers() {
        return (List<UserAccount>) repository.findAll();
    }


    public String createUser(UserAccount userAccount) {
        try {
            repository.save(userAccount);
            return "User with phone number " + userAccount.getPhoneNumber() + " created successfully.";
        } catch (Exception e) {
            return "The parameters you entered are not valid userAccounts parameters, please enter a valid userAccounts parameter.";
        }
    }

    public String updateUser(UserAccount userAccount) {
        try {
            if (repository.findByPhoneNumber(userAccount.getPhoneNumber()) == null) {
                return "User with phone number " + userAccount.getPhoneNumber() + " not found.";
            }
            repository.save(userAccount);
            return "User with phone number " + userAccount.getPhoneNumber() + " updated successfully.";
        } catch (Exception e) {
            return "User with phone number " + userAccount.getPhoneNumber() + " not found.";
        }
    }


    public String deleteUser(String phoneNumber) {
        try {
            repository.delete(repository.findByPhoneNumber(phoneNumber));
            return "User with phone number " + phoneNumber + " deleted Successfully.";
        } catch (Exception e) {
            return "User with phone number " + phoneNumber + " not found.";
        }
    }

    public Optional<UserAccount> getUser(String phoneNumber) {
        return Optional.ofNullable(repository.findByPhoneNumber(phoneNumber));
    }
}
