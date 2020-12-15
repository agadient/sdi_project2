package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserRepository repository;
    public RegistrationController(UserRepository repository) {
        this.repository = repository;
    }
    @PostMapping("/new")
    public String newRegistration(@RequestBody User user) {
            this.repository.save(user);
            return "Ok";
    }
}
