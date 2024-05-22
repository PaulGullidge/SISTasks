package com.gullidge.retrospective.exceptions;

public class ExternalMessageFriendlyRuntimeException extends RuntimeException {

	public ExternalMessageFriendlyRuntimeException() {
	}
	
	public ExternalMessageFriendlyRuntimeException(String message) {
		super(message);
	}
	
	public ExternalMessageFriendlyRuntimeException(Throwable t) {
		super(t);
	}
	
}
