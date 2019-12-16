package ru.priamosudov.hotelme.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.priamosudov.core.common.dto.ErrorDto;
import ru.priamosudov.hotelme.user.UserNotFoundException;
import ru.priamosudov.hotelme.user.domain.User;
import ru.priamosudov.hotelme.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@Valid @RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        userService.addUser(user);
        UriComponents uriComponents = uriComponentsBuilder.path("/user/{username}").buildAndExpand(user.getUsername());

        return ResponseEntity
                .created(uriComponents.toUri())
                .build();
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        final String errorMessage = ex.getMessage() + String.format(". Username = [%s]", ex.getUsername());
        return ResponseEntity.badRequest().body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }
}
