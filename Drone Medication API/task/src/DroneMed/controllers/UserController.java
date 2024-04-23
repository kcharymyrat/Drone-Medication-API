package DroneMed.controllers;

import DroneMed.models.UserAccount;
import DroneMed.responses.ResponseHandler;
import DroneMed.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody UserAccount userAccount) {
        String res = userService.createUser(userAccount);
        if(res.contains("not found.")) {
            return ResponseHandler.responseBuilder(res, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @PostMapping("/create_users")
    public ResponseEntity<?> createUsers(@RequestBody List<UserAccount> userAccounts) {
        List<String> res = userService.createUsers(userAccounts);
        return ResponseHandler.responseBuilder("Users created successfully.", HttpStatus.OK, res);
    }

    @PutMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody UserAccount user) {
        String res = userService.updateUser(user);
        if(res.contains("not found.")) {
            return ResponseHandler.responseBuilder(res, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete_user/{phoneNumber}")
    public ResponseEntity<?> deleteUser(@PathVariable String phoneNumber) {
        String res = userService.deleteUser(phoneNumber);
        if(res.contains("not found.")) {
            return ResponseHandler.responseBuilder(res, HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.responseBuilder(res, HttpStatus.OK);
    }

    @GetMapping("/get_user/{phoneNumber}")
    public ResponseEntity<?> getUser(@PathVariable String phoneNumber) {
        Optional<UserAccount> userAccount = userService.getUser(phoneNumber);
        if(userAccount.isPresent()) {
            return ResponseHandler.responseBuilder("User fetched successfully.", HttpStatus.OK, userAccount);
        }
        return ResponseHandler.responseBuilder("The user with phone number " + phoneNumber + " was not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<?> getAllUsers() {
        List<UserAccount> userAccounts = userService.getAllUsers();
        if(!userAccounts.isEmpty()) {
            return ResponseHandler.responseBuilder("All users fetched successfully.", HttpStatus.OK, userAccounts);
        }
        return ResponseHandler.responseBuilder("The user list is empty.", HttpStatus.OK);
    }

}
