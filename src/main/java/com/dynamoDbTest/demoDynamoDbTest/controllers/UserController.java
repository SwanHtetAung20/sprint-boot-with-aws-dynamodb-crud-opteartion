package com.dynamoDbTest.demoDynamoDbTest.controllers;

import com.dynamoDbTest.demoDynamoDbTest.entities.User;
import com.dynamoDbTest.demoDynamoDbTest.repositroies.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;


    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.getUserById(id));
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/get-All/{id}")
    public ResponseEntity<List<User>> findUsersWithId(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.getUsersWithId(id));
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<User>> findUsersWithGsi(@RequestParam(name = "date") String date) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.getUsersWithGsi(date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userRepository.deleteUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return ResponseEntity.ok(userRepository.updateUser(id, user));
    }

}
