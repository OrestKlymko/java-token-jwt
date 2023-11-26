package com.example.demo.user.service;

import com.example.demo.security.JwtToken;
import com.example.demo.user.dto.ExceptionMessage;
import com.example.demo.user.dto.UserLoginDTO;
import com.example.demo.user.dto.UserRegistrationDTO;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.util.ValidChecker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtToken jwtToken;

	public User createUser(UserRegistrationDTO userRegistrationDTO) throws ExceptionMessage {
		System.out.println("CREATED");
		if (!ValidChecker.isPasswordValid(userRegistrationDTO.getPassword())) {
			throw new ExceptionMessage("Password should contain min 4 and max 12 symbols");
		}
		if (!ValidChecker.isEmailValid(userRegistrationDTO.getEmail())) {
			throw new ExceptionMessage("Email should have correct pattern");
		}
		User newUser = convertFromDTOtoDatabaseModel(userRegistrationDTO);
		return userRepository.save(newUser);
	}

	@NotNull
	private static User convertFromDTOtoDatabaseModel(UserRegistrationDTO userRegistrationDTO) {
		User newUser = User.builder()
				.email(userRegistrationDTO.getEmail())
				.role(Role.USER)
				.username(userRegistrationDTO.getUsername())
				.enabled(true)
				.build();
		newUser.setSafetyPassword(userRegistrationDTO.getPassword());
		return newUser;
	}

	public User loginUser(UserLoginDTO userLoginDTO, HttpServletResponse httpServletResponse) throws Exception {
		User existUser = userRepository.findUserByUsername(userLoginDTO.getUsername())
				.orElseThrow(() -> new ExceptionMessage(String.format("Username %s not found", userLoginDTO.getUsername())));

		String token = jwtToken.generateToken( userLoginDTO);
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		httpServletResponse.addCookie(cookie);
		return existUser;
	}


	public User getAuthenticateUserInfo(String jwt) throws NoSuchAlgorithmException, ExceptionMessage {
		UserLoginDTO userFromToken = jwtToken.getUserFromToken(jwt);
		return getUserByUsername(userFromToken.getUsername());
	}

	private User getUserByUsername(String username) throws ExceptionMessage {
		return userRepository.findUserByUsername(username)
				.orElseThrow(() -> new ExceptionMessage(String.format("Username %s not found", username)));
	}

}
