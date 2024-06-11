package com.time3.api.domains.Auth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.time3.api.configuration.TokenService;
import com.time3.api.domains.Auth.dtos.LoginDto;
import com.time3.api.domains.Auth.dtos.LoginResponseDto;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;
import com.time3.api.domains.User.UserRolesEnum;
import com.time3.api.domains.User.dtos.RegisterDto;

@ExtendWith(MockitoExtension.class)
public class AuthenticationUseCasesTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private AuthenticationUseCases authenticationUseCases;

    private RegisterDto registerDto;
    private LoginDto loginDto;
    private User user;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto("user@example.com", "name", "password123", UserRolesEnum.USER);
        loginDto = new LoginDto("user@example.com", "password123");
        user = new User("user@example.com", "name", "password123", UserRolesEnum.USER);
    }

    @Test
    void register_Successful_NoExceptionThrown() {
        when(repository.findByEmail(anyString())).thenReturn(null);

        assertDoesNotThrow(() -> authenticationUseCases.register(registerDto));

        verify(repository).save(any(User.class));
    }

    @Test
    void register_EmailAlreadyExists_ThrowsException() {
        when(repository.findByEmail(registerDto.email())).thenReturn(new User());

        assertThrows(AuthenticationException.UserAlreadyExists.class, () -> {
            authenticationUseCases.register(registerDto);
        });

        verify(repository, never()).save(any(User.class));
    }

    @Test
    void login_Successful_ReturnsToken() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginDto.email(), loginDto.password());
        when(authenticationManager.authenticate(authRequest))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        String expectedToken = "mocked-token";
        when(tokenService.generateToken(user)).thenReturn(expectedToken);

        LoginResponseDto result = authenticationUseCases.login(loginDto);

        assertNotNull(result);
        assertEquals(expectedToken, result.token());
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginDto.email(), loginDto.password());
        when(authenticationManager.authenticate(authRequest))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> {
            authenticationUseCases.login(loginDto);
        });
    }
}
