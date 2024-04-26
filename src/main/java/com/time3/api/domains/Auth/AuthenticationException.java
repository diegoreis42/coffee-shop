package com.time3.api.domains.Auth;

import org.springframework.http.HttpStatus;

import com.time3.api.shared.exception.GlobalException;

public class AuthenticationException extends GlobalException {

    public AuthenticationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class UserAlreadyExists extends AuthenticationException {
        public UserAlreadyExists() {
            super("Usuário já cadastrado", HttpStatus.CONFLICT);
        }
    }

    public static class JwtException extends AuthenticationException {
        public JwtException() {
            super("Token inválido", HttpStatus.FORBIDDEN);
        }

        public JwtException(String message) {
            super(message, HttpStatus.FORBIDDEN);
        }

        public JwtException(String message, HttpStatus httpStatus) {
            super(message, httpStatus);
        }
    }

}
