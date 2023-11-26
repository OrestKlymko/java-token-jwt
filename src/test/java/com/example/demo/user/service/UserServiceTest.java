package com.example.demo.user.service;

import com.example.demo.user.dto.ExceptionMessage;
import com.example.demo.user.dto.UserLoginDTO;
import com.example.demo.user.dto.UserRegistrationDTO;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userServiceTest;

	private AutoCloseable closeable;

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		closeable = () -> {};
	}

	@AfterEach
	void afterEach() throws Exception {
		if (closeable != null) {
			closeable.close();
		}
	}


	@Test
	void createUser() throws ExceptionMessage {

		//give
		UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
		userRegistrationDTO.setEmail("orest@gmail.com");
		userRegistrationDTO.setPassword("orest1111");
		userRegistrationDTO.setUsername("orestsurname");

		//when
		userServiceTest.createUser(userRegistrationDTO);

		//then
		verify(userRepository).save(any(User.class));

	}


}