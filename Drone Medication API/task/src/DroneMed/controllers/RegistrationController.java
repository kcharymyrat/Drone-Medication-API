package DroneMed.controllers;

import DroneMed.models.AppUser;
import DroneMed.repositories.AppUserRepository;
import DroneMed.responses.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/register_user")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);
        String response = "The user registration was successful.";

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }
}
