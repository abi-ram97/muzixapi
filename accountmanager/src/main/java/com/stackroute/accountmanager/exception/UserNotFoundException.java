package com.stackroute.accountmanager.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {

	String message;
	
	public UserNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserNotFoundException [message=" + message + "]";
	}
	

}
