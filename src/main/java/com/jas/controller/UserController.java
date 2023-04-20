package com.jas.controller;

import com.jas.dto.UserDTO;
import com.jas.entity.User;
import com.jas.service.impl.UserServiceImpl;
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
    UserServiceImpl userService;

    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserDetails(@Valid @RequestBody UserDTO user, @PathVariable Integer id) {
        userService.updateUser(user,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
