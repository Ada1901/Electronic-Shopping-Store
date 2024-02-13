package com.lcwa.electonic.store.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lcwa.electonic.store.dto.ApiResponseMessage;
import com.lcwa.electonic.store.validate.ImageNameValidator;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourseNotFoundExceptionHandler(ResourceNotFoundException exception) {

		logger.info("Exception Handler Invoked");
		ApiResponseMessage response = ApiResponseMessage.builder().message(exception.getMessage())
				.status(HttpStatus.NOT_FOUND).sucess(true).build();
		return new ResponseEntity(response,HttpStatus.NOT_FOUND);
	}
	
	//MethodArgumentNotFound
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotFound(MethodArgumentNotValidException exception){
		List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
		Map<String, Object> response= new HashMap<>();
		allErrors.stream().forEach(objectError->{
			String message = objectError.getDefaultMessage();
			String field = ((FieldError)objectError).getField();
			response.put(field, message);
		});
		return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
	}
	
	// Handle Bad API request
	@ExceptionHandler(BadRequestAPI.class)
	public ResponseEntity<ApiResponseMessage> handleBadRequestAPI(BadRequestAPI exception) {

		logger.info("Bad API Request");
		ApiResponseMessage response = ApiResponseMessage.builder().message(exception.getMessage())
				.status(HttpStatus.BAD_REQUEST).sucess(false).build();
		return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
	}
}
