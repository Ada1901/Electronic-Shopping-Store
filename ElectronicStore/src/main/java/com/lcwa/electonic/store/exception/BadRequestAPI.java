package com.lcwa.electonic.store.exception;

public class BadRequestAPI extends RuntimeException{

	public BadRequestAPI(String message) {
		super(message);
	}
	
	public BadRequestAPI() {
		super("Bad API Request!!");
	}
}
