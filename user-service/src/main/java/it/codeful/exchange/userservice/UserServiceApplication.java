package it.codeful.exchange.userservice;

import it.codeful.exchange.userservice.data.UserRepository;
import it.codeful.exchange.userservice.exception.DuplicateUserException;
import it.codeful.exchange.userservice.exception.UserNotFoundException;
import it.codeful.exchange.userservice.data.UserView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@SpringBootApplication
@Controller
@Slf4j
public class UserServiceApplication {
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/user")
	public ResponseEntity<Void> createUser(@RequestBody UserView user) {
		userRepository.create(user.toModel());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/user/{pesel}")
	public ResponseEntity<UserView> getUser(@PathVariable("pesel") String pesel) {
		return new ResponseEntity<>(userRepository.get(pesel).toView(), HttpStatus.OK);
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<Void> handleDuplicateUser() {
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Void> handleUserNotFound() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleInvalidData(ConstraintViolationException exception) {
		return new ResponseEntity<>(exception.getConstraintViolations(), HttpStatus.BAD_REQUEST);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
