package com.learning.exception;

import lombok.ToString;

@ToString(callSuper = true)
//Internally it'll give a call to the toString method from the base class that is exception class
public class InvalidIdLengthException extends Exception {

	public InvalidIdLengthException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	//Referred as an user defined exception

	
	//Can be done us toString(callSuper = true)
	
//	@Override
//	public String toString() {
//		return "IdNotFoundException [toString()=" + super.toString() + "]";
//	}
	
}
