package ru.priamosudov.hotelme.user;

import lombok.Getter;

import javax.validation.constraints.NotNull;

public class UserNotFoundException extends RuntimeException {

    @Getter
    private String username;

    public UserNotFoundException(String message, Throwable cause, @NotNull String username) {
        super(message, cause);
        this.username = username;
    }

    public UserNotFoundException(String message, @NotNull String username) {
        super(message);
        this.username = username;
    }
}
