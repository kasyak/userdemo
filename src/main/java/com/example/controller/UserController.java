package com.example.controller;

import com.example.domain.User;
import com.example.domain.UserReport;
import com.example.service.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<UserReport> users() {
        return userRepo.findAll()
                .stream()
                .map(user -> new UserReport(user.toString(), user.getLogin()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public User userById(@PathVariable(value="id") Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
        ));
    }
}