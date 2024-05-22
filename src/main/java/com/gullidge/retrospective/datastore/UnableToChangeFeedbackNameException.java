package com.gullidge.retrospective.datastore;

import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;

public class UnableToChangeFeedbackNameException extends ExternalMessageFriendlyException {

	public UnableToChangeFeedbackNameException() {
	}
	
	public UnableToChangeFeedbackNameException(String message) {
		super(message);
	}
	
	public UnableToChangeFeedbackNameException(Throwable t) {
		super(t);
	}	
	
}
