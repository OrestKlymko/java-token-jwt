package com.example.demo.user.controller;

import com.example.demo.user.dto.ExceptionMessage;
import com.example.demo.user.dto.UserLoginDTO;
import com.example.demo.user.dto.UserRegistrationDTO;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public ResponseEntity<?> getInfo(@CookieValue("token") String jwt) {
		try {
			return ResponseEntity.ok(userService.getAuthenticateUserInfo(jwt));
		} catch (NoSuchAlgorithmException | ExceptionMessage e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/registration")
	public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
		try {
			return ResponseEntity.ok(userService.createUser(userRegistrationDTO));
		} catch (ExceptionMessage e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse httpServletResponse) {
		try {
			return ResponseEntity.ok(userService.loginUser(userLoginDTO, httpServletResponse));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
