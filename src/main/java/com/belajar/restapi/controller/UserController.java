package com.belajar.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.belajar.restapi.model.User;
import com.belajar.restapi.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<User> findUserByName(@RequestParam String name) {
        Optional<User> user = userRepository.findAllByNameContainingIgnoreCase(name);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") long id, @RequestBody User data) {
        Optional<User> userData = userRepository.findById(id);

        if(userData.isPresent()) {
            User user = userData.get();
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            return ResponseEntity.ok().body(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(value = "id") long id) {
        Optional<User> userData = userRepository.findById(id);

        if(userData.isPresent()) {
            try {
                userRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } catch (Exception ex) {
                return ResponseEntity.internalServerError().build();
            }
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
