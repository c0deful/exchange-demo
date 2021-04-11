package it.codeful.exchange.userservice.api;

import it.codeful.exchange.userservice.data.UserModel;
import it.codeful.exchange.userservice.data.UserRepository;
import it.codeful.exchange.userservice.exception.DuplicateUserException;
import it.codeful.exchange.userservice.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@Slf4j
@Validated
public class UserApi {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody CreateUserCommand command) {
        userRepository.create(UserModel.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .pesel(command.getPesel())
                .build());
    }

    @GetMapping("/user/{pesel}")
    @ResponseStatus(HttpStatus.OK)
    public UserView getUser(@PathVariable("pesel") @Valid @PESEL String pesel) {
        return userRepository.get(pesel).toView();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateUser(DuplicateUserException e) {
        log.info(e.getMessage());
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserNotFound(UserNotFoundException e) {
        log.info(e.getMessage());
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidData(ConstraintViolationException e) {
        return new ApiError(e.getMessage());
    }

    @Data
    @AllArgsConstructor
    public static final class ApiError {
        private String message;
    }
}
