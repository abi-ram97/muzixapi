package com.stackroute.accountmanager.exception;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception {
	String message;

	public UserAlreadyExistsException(String message) {
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
		return "UserAlreadyExistsException [message=" + message + "]";
	}
	
}
