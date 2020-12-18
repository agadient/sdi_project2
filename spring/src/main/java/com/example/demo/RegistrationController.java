package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @CrossOrigin(origins="http://localhost:3000", allowCredentials="true")
    @PostMapping("/new")
    public String newRegistration(HttpServletResponse response, @RequestBody Registration registration) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Method", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        User newUser = new User();
        PasswordHasher.PasswordHashAndSalt phs = phasher.hashPassword(registration.getPassword());
        newUser.setUsername(registration.getUsername());
        newUser.setPassword_hash(phs.getHashedPassword());
        newUser.setSalt(phs.getSalt());
        System.out.println(registration.getLocation());
        newUser.setLocation_id(registration.getLocation());
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
        Long location;

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

        public Long getLocation() {
            return location;
        }

        public void setLocation(Long location) {
            this.location = location;
        }

    }

}
