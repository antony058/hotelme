package ru.priamosudov.hotelme.user.exception;

import lombok.Getter;

public class UserNotFoundException extends RuntimeException {

    private static final String COMMON_ERROR_MESSAGE = "User was not found.";

    @Getter
    private String username;

    public UserNotFoundException(Throwable cause, String username) {
        super(COMMON_ERROR_MESSAGE, cause);
        this.username = username;
    }

    public UserNotFoundException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }

    public UserNotFoundException(String username) {
        super(COMMON_ERROR_MESSAGE);
        this.username = username;
    }

    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }
}
