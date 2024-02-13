package com.lcwa.electonic.store.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("Resourse not Found");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
