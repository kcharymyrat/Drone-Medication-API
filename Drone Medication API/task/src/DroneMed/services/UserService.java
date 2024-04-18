package DroneMed.services;

import DroneMed.models.Drone;
import DroneMed.models.UserAccount;
import DroneMed.repositories.DroneRepository;
import DroneMed.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
            return "The user with phone number " + userAccount.getPhoneNumber() + " was created successfully.";
        } catch (Exception e) {
            return "The parameter you entered contains a null or invalid parameter, Please enter a valid user account parameter.";
        }
    }

    public String updateUser(UserAccount userAccount) {
        try {
            if (repository.findByPhoneNumber(userAccount.getPhoneNumber()) == null) {
                return "The user with phone number " + userAccount.getPhoneNumber() + " was not found.";
            }
            repository.save(userAccount);
            return "The user with phone number " + userAccount.getPhoneNumber() + " was updated successfully.";
        } catch (Exception e) {
            return "The user with phone number " + userAccount.getPhoneNumber() + " was not found.";
        }
    }


    public String deleteUser(String phoneNumber) {
        try {
            repository.delete(repository.findByPhoneNumber(phoneNumber));
            return "The user with phone number " + phoneNumber + " was deleted Successfully.";
        } catch (Exception e) {
            return "The user with phone number " + phoneNumber + " was not found.";
        }
    }

    public Optional<UserAccount> getUser(String phoneNumber) {
        return Optional.ofNullable(repository.findByPhoneNumber(phoneNumber));
    }


    @Transactional
    public List<String> createUsers(List<UserAccount> users) {
        List<String> res = new ArrayList<>();
        for (UserAccount u: users) {
            res.add(createUser(u));
        }
        return res;
    }
}
