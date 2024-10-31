package com.krushit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krushit.entity.User;
import com.krushit.model.UserModel;
import com.krushit.service.IUserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserModel userModel) {
        try {
            User createdUser = userService.createUser(userModel);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error creating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel userModel, HttpSession session) {
        try {
            Optional<User> userOpt = userService.loginUser(userModel.getEmail(), userModel.getPassword());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Set user details in session
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getFirstName());
                System.out.println("User Name :: " +user.getFirstName());
                return ResponseEntity.ok(user);  // Return user details
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");

        if (userId != null) {
            return ResponseEntity.ok("Current user: " + userName);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
        }
    }


    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
