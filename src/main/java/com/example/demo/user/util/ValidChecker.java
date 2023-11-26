package com.example.demo.user.util;



public class ValidChecker {


	public static boolean isPasswordValid( String password) {
		String correctPasswordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,12}$";
		return password.matches(correctPasswordPattern);
	}

	public static boolean isEmailValid( String email) {
		String correctEmailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return email.matches(correctEmailPattern);
	}
}
