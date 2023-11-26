package com.example.demo.security;

import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userByUsername = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return org.springframework.security.core.userdetails.User.builder()
				.roles(userByUsername.getRole().toString())
				.password(userByUsername.getPassword())
				.username(userByUsername.getUsername())
				.disabled(!userByUsername.isEnabled())
				.build();
	}
}
