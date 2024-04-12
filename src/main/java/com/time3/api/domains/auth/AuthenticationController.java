package com.time3.api.domains.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.time3.api.configuration.TokenService;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;
import com.time3.api.domains.User.dtos.RegisterDto;
import com.time3.api.domains.auth.dtos.LoginDto;
import com.time3.api.domains.auth.dtos.LoginResponseDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());
        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) authenticate.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto data) {
        if (this.repository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User user = new User(data.email(), encryptedPassword, data.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }
}