package ru.priamosudov.hotelme.user.controller;

import org.modelmapper.ModelMapper;
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
import ru.priamosudov.hotelme.user.exception.UserAlreadyExistException;
import ru.priamosudov.hotelme.user.exception.UserNotFoundException;
import ru.priamosudov.hotelme.user.domain.SecuredUser;
import ru.priamosudov.hotelme.user.domain.User;
import ru.priamosudov.hotelme.user.dto.UserDto;
import ru.priamosudov.hotelme.user.dto.request.SecuredUserDto;
import ru.priamosudov.hotelme.user.service.SecuredUserService;
import ru.priamosudov.hotelme.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    private final SecuredUserService securedUserService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, SecuredUserService securedUserService, ModelMapper modelMapper) {
        this.userService = userService;
        this.securedUserService = securedUserService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserDto userDto = convertObject(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@Valid @RequestBody SecuredUserDto securedUserDto, UriComponentsBuilder uriComponentsBuilder) {
        SecuredUser securedUser = convertObject(securedUserDto, SecuredUser.class);
        securedUserService.addUser(securedUser);
        UriComponents uriComponents = uriComponentsBuilder.path("/user/{username}").buildAndExpand(securedUserDto.getUsername());

        return ResponseEntity
                .created(uriComponents.toUri())
                .build();
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = ex.getMessage() + String.format(" Username = [%s]", ex.getUsername());
        return ResponseEntity.badRequest().body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<ErrorDto> handlerUserAlreadyExistException(UserAlreadyExistException ex) {
        String message = ex.getMessage() + String.format(" Username = [%s]", ex.getUsername());
        return ResponseEntity.badRequest().body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), message));
    }

    private <D, S> D convertObject(S sourceObject, Class<D> destinationObjectClass) {
        return modelMapper.map(sourceObject, destinationObjectClass);
    }
}
