package com.gullidge.retrospective.datastore;

import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;

public class RetrospectiveNotFoundException extends ExternalMessageFriendlyException {

	public RetrospectiveNotFoundException() {
	}
	
	public RetrospectiveNotFoundException(String message) {
		super(message);
	}
	
	public RetrospectiveNotFoundException(Throwable t) {
		super(t);
	}
}
