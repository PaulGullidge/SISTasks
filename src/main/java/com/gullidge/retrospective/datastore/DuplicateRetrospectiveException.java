package com.gullidge.retrospective.datastore;

import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;

public class DuplicateRetrospectiveException extends ExternalMessageFriendlyException {

	public DuplicateRetrospectiveException() {
	}
	
	public DuplicateRetrospectiveException(String message) {
		super(message);
	}
	
	public DuplicateRetrospectiveException(Throwable t) {
		super(t);
	}
}
