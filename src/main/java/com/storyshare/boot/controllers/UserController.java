package com.storyshare.boot.controllers;

import com.storyshare.boot.pojos.User;
import com.storyshare.boot.services.UserService;
import com.storyshare.boot.wrappers.Role;
import com.storyshare.boot.wrappers.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
        }

        user.setRole("user");
        user.setStatus("active");
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PatchMapping(value = "users/{userID}/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_BOSS")
    public ResponseEntity<String> changeUserRole(
            @PathVariable("userID") long userID,
            @RequestBody Role role) {
        if ("user".equals(role.getRole())) {
            userService.assignAdmin(userID);

            return new ResponseEntity<>(HttpStatus.OK);
        } else if ("admin".equals(role.getRole())) {
            userService.assignUser(userID);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_BOSS', 'ROLE_ADMIN')")
    @PatchMapping(value = "users/{userID}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeUserStatus(
            @PathVariable("userID") long userID,
            @RequestBody Status status) {
        if ("active".equals(status.getStatus())) {
            userService.banUser(userID);

            return new ResponseEntity<>(HttpStatus.OK);
        } else if ("banned".equals(status.getStatus())) {
            userService.unbanUser(userID);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "users/{userID}/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserAvatar(@PathVariable("userID") long userID) {
        return new ResponseEntity<>(userService.getUserAvatar(userID), HttpStatus.OK);
    }

    @GetMapping(value = "users/{userID}/name", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getUserName(@PathVariable("userID") long userID) {
        return new ResponseEntity<>(userService.getUserName(userID), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_BOSS', 'ROLE_ADMIN')")
    @GetMapping(value = "users/{userID}/role,status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserRoleAndStatus(@PathVariable("userID") long userID) {
        return new ResponseEntity<>(userService.getUserRoleAndStatus(userID).toString(), HttpStatus.OK);
    }
}

/*
create_user
change_status
change_role
user_avatar
user_name
user_role_and_status
*/

