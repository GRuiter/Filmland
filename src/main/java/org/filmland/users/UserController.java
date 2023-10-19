package org.filmland.users;

import org.filmland.entities.Users;
import org.filmland.response.DefaultResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> checkUser(@RequestBody UserCredentials userCredentials) {

        Users user = userRepository.findAllByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword());

        if (user != null) {
            DefaultResponseMessage response = new DefaultResponseMessage("Login successful", "Welcome to Filmland");
            return ResponseEntity.ok(response);
        } else {
            DefaultResponseMessage response = new DefaultResponseMessage("Login failed", "Wrong credentials");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
