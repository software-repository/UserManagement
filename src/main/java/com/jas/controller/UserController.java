package com.jas.controller;

import com.jas.dto.UserDTO;
import com.jas.entity.User;
import com.jas.exceptions.DepartmentNotFoundException;
import com.jas.exceptions.UserAlreadyExistsException;
import com.jas.exceptions.UserNotFoundException;
import com.jas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws UserAlreadyExistsException, DepartmentNotFoundException {
         userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUserDetails(@Valid @RequestBody User user) throws UserNotFoundException {
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
