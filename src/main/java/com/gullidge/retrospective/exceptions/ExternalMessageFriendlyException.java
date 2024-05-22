package com.gullidge.retrospective.exceptions;

public class ExternalMessageFriendlyException extends Exception {
	
	public ExternalMessageFriendlyException() {
	}
	
	public ExternalMessageFriendlyException(String message) {
		super(message);
	}
	
	public ExternalMessageFriendlyException(Throwable t) {
		super(t);
	}
}
