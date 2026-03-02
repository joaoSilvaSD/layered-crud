package com.layered.crud.presentation.controller;

import com.layered.crud.business.dto.UserCreateRequest;
import com.layered.crud.business.dto.UserUpdateRequest;
import com.layered.crud.business.dto.UserResponse;
import com.layered.crud.business.service.UserService;
import com.layered.crud.presentation.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long userId) {
        Optional<UserResponse> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            throw new NotFoundException("User not found with id: " + userId);
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> changeById(@PathVariable Long userId,
                                                   @RequestBody @Valid UserUpdateRequest request) {
        Optional<UserResponse> userOptional = userService.changeById(userId, request);

        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            throw new NotFoundException("User not found with id: " + userId);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
        Optional<UserResponse> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            userService.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new NotFoundException("User not found with id: " + userId);
        }
    }
}
