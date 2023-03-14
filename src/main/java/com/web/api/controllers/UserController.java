package com.web.api.controllers;

import com.web.api.dto.AuthResponse;
import com.web.api.dto.LoginRequest;
import com.web.api.dto.ResponData;
import com.web.api.dto.UserRegister;
import com.web.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    @Lazy
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ResponData<AuthResponse>> register(@Valid @RequestBody UserRegister userRegister, Errors errors) {
        ResponData<AuthResponse> responData = new ResponData<>();

        try {
            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    responData.getMessages().add(error.getDefaultMessage());
                }
                responData.setStatus(false);
                responData.setPayload(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
            } else {
                responData.setStatus(true);
                responData.setPayload(userService.register(userRegister));
                responData.getMessages().add("Anda berhasil register");
                return ResponseEntity.ok(responData);
            }
        } catch (RuntimeException exception) {
            responData.setStatus(false);
            responData.setPayload(null);
            responData.getMessages().add(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ResponData<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest, Errors errors) {
        ResponData<AuthResponse> responData = new ResponData<>();
        try {
            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    responData.getMessages().add(error.getDefaultMessage());
                }
                responData.setStatus(false);
                responData.setPayload(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
            } else {
                responData.setStatus(true);
                responData.setPayload(userService.authenticate(loginRequest));
                return ResponseEntity.ok(responData);
            }

        } catch (IllegalArgumentException exception) {
            responData.setStatus(false);
            responData.setPayload(null);
            responData.getMessages().add(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        } catch (NoSuchElementException exception) {
            responData.setStatus(false);
            responData.setPayload(null);
            responData.getMessages().add(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responData);
        }
    }

}
