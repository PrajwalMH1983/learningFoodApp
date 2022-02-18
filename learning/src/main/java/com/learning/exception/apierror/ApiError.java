package com.learning.exception.apierror;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {
	//1. Its a complete wrapper class , all details you get in this class 
	//
	//Should provide collective info regarding error / errors
	private HttpStatus httpStatus;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM--yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	
	private String message;
	private String debugMessage;
	
	private List<ApiSubError> subErrors;	//To hold validational related errors
	
	private ApiError() {
		timeStamp = LocalDateTime.now();	//At the time of calling the method whatever the date time value are there it will provide it 
		
	}
	
	public ApiError(HttpStatus status) {
		this();
		this.httpStatus = status;
	}
	
	public ApiError(HttpStatus status , String message , Throwable ex) {
		this();
		this.httpStatus = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
	//Every field validation in subError
	//create and add into subError
	private void addSubError(ApiSubError apiSubError) {
		if(subErrors == null)
			subErrors = new ArrayList<>();
		subErrors.add(apiSubError);
	}
	
	private void addValidationError(String object , String field ,Object rejectedValue , String message) {
		addSubError(new ApiValidationError(object, field , rejectedValue , message));
	}
	
	private void addValidationError(String object , String message) {
		addSubError(new ApiValidationError(object, message));
	}
	
	//Encapsulates a fieldError , that is a reason for rejecting a specific field value
	private void addValidationError(FieldError fieldError) {
		//getObjectName() : On which object the error occurred
		//getField() : On which field this error occurred
		//getRejectedValue() : Y it is rejected
		//getDefaultMessage() : WWhat was the message that is holding it
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}
	
	public void addValidationError1(List<FieldError> fieldErrors) {
		//fieldErrors.forEach(this::addValidationError);	//method reference     both are same
		fieldErrors.forEach(e->this.addValidationError(e));
	}
	
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
	
	public void addValidationErrors(List<ObjectError> globalErrors) {
		globalErrors.forEach(e->this.addValidationError(e));
	}
	
	
	//Leaf Node will provide u with the last details ---> means over that particular path right 
	//whenever that error is going to occur right for that specific property we are gonna retrieve that name 
	//InvalidValue --> which invalid value u provided
	public void addValidationError(ConstraintViolation<?> constraintViolation) {
		this.addValidationError(constraintViolation.getRootBeanClass().getName() ,
				((PathImpl)constraintViolation.getPropertyPath()).getLeafNode().asString() ,
				 constraintViolation.getInvalidValue() ,
				 constraintViolation.getMessage());
	}
	
	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(e->addValidationError(e));
	}
	
}
