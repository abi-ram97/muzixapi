package com.stackroute.muzixmanager.exception;

@SuppressWarnings("serial")
public class MuzixTrackNotFoundException extends Exception {
	String message;

	public MuzixTrackNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
