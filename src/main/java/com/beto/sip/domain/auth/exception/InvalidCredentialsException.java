package com.beto.sip.domain.auth.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciales incorrectas, verifica tu usuario y contraseña");
    }
}