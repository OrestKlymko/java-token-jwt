package com.example.demo.user.dto;

import lombok.Data;



public class ExceptionMessage extends Exception {
	public ExceptionMessage(String message) {
		super(message);
	}

}
