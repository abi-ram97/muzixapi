package com.stackroute.accountmanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.accountmanager.exception.UserAlreadyExistsException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.service.SecurityTokenGenerator;
import com.stackroute.accountmanager.service.UserService;



@RestController
@CrossOrigin
@RequestMapping("/api/v1/userservice")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityTokenGenerator tokenGenerator;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			return new ResponseEntity<String>("User registered successfully", HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
		try {
			final String userId = loginUser.getUserId();
			final String password = loginUser.getPassword();

			if (userId == null || password == null)
				throw new UserNotFoundException("UserId or password cannot be empty");

			User user = userService.findByUserIdAndPassword(userId, password);

			Map<String, String> map = tokenGenerator.generateToken(user);

			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
		}
	}

}
