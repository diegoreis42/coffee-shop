package com.time3.api.domains.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.time3.api.configuration.TokenService;
import com.time3.api.domains.Auth.dtos.LoginDto;
import com.time3.api.domains.Auth.dtos.LoginResponseDto;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;
import com.time3.api.domains.User.dtos.RegisterDto;

@Service
public class AuthenticationUseCases {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDto login(LoginDto data) {

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());
        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) authenticate.getPrincipal());

        return new LoginResponseDto(token);
    }

    public void register(RegisterDto data) {

        if (this.repository.findByEmail(data.email()) != null)
            throw new AuthenticationException.UserAlreadyExists();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        this.repository.save(new User(data.email(), encryptedPassword, data.role()));
    }
}
