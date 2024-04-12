package com.time3.api.domains.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.time3.api.domains.Auth.dtos.LoginDto;
import com.time3.api.domains.Auth.dtos.LoginResponseDto;
import com.time3.api.domains.User.dtos.RegisterDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationUseCases authenticationUseCases;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto data) {

        return ResponseEntity.ok(authenticationUseCases.login(data));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto data) {

        authenticationUseCases.register(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}