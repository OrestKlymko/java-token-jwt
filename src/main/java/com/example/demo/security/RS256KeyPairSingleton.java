package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Component
public class RS256KeyPairSingleton {

	private static volatile RS256KeyPairSingleton instance;
	private KeyPair keyPair;

	private RS256KeyPairSingleton() {
		if (instance != null) {
			throw new RuntimeException("Use getInstance() method to create a singleton");
		}
		generateKeyPair();
	}

	public static RS256KeyPairSingleton getInstance() {
		if (instance == null) {
			synchronized (RS256KeyPairSingleton.class) {
				if (instance == null) {
					instance = new RS256KeyPairSingleton();
				}
			}
		}
		return instance;
	}

	private void generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048); // Встановлюємо бажаний розмір ключа
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}
}
