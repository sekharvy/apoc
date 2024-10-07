package com.example.demo;


import com.example.demo.User;
import com.example.demo.Group;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.User;
import com.example.demo.Group;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // In-memory storage for users and groups for mocking
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Group> groups = new HashMap<>();
    private int userIdCounter = 1;

    // Endpoint to add a new user
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        user.setId(userIdCounter++);
        users.put(user.getId(), user);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    // Endpoint to add a user to a group
    @PostMapping("/{userId}/groups")
    public ResponseEntity<String> addUserToGroup(@PathVariable int userId, @RequestBody Group group) {
        User user = users.get(userId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Check if the group is already associated with the user
        if (user.getGroups().contains(group.getId())) {
            return new ResponseEntity<>("User is already a member of this group", HttpStatus.BAD_REQUEST);
        }

        user.getGroups().add(group.getId());
        return new ResponseEntity<>("User added to group successfully", HttpStatus.CREATED);
    }
}


