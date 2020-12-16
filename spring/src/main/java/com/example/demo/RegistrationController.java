package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    PasswordHasher phasher;
    private final UserRepository repository;
    public RegistrationController(UserRepository repository) {
        this.repository = repository;
    }
    @PostMapping("/new")
    public String newRegistration(@RequestBody Registration registration) {
        User newUser = new User();
        PasswordHasher.PasswordHashAndSalt phs = phasher.hashPassword(registration.getPassword());
        newUser.setUsername(registration.getUsername());
        newUser.setPassword_hash(phs.getHashedPassword());
        newUser.setSalt(phs.getSalt());
        newUser.setLocation_id(registration.getLocationId());
        newUser.setAge(registration.getAge());
        try {
            this.repository.save(newUser);
        } catch(Exception e) {
            return "Invalid registration!";
        }
        return "Ok";
    }

    public static class Registration {
        String username;
        String password;
        Long age;
        Long locationId;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Long getAge() {
            return age;
        }

        public void setAge(Long age) {
            this.age = age;
        }

        public Long getLocationId() {
            return locationId;
        }

        public void setLocationId(Long locationId) {
            this.locationId = locationId;
        }

    }

}
