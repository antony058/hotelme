package ru.priamosudov.hotelme.user.exception;

import lombok.Getter;

public class UserAlreadyExistException extends RuntimeException {

    private static final String COMMON_ERROR_MESSAGE = "User already exist.";

    @Getter
    private String username;

    public UserAlreadyExistException(Throwable cause, String username) {
        super(COMMON_ERROR_MESSAGE, cause);
        this.username = username;
    }
    public UserAlreadyExistException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }


    public UserAlreadyExistException(String username) {
        super(COMMON_ERROR_MESSAGE);
        this.username = username;
    }

    public UserAlreadyExistException(String message, String username) {
        super(message);
        this.username = username;
    }
}
