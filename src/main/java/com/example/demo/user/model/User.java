package com.example.demo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Getter
@Table(name = "users")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Setter
	@Column(name = "username")
	private String username;

	@Setter
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Setter
	@Column(name = "enabled")
	private boolean enabled;

	@Setter
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	public void setSafetyPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id && Objects.equals(username, user.username) && Objects.equals(email, user.email) && role == user.role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, role);
	}
}
