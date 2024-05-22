package com.gullidge.retrospective.datastore;

import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;

public class FeedbackItemNotFoundException extends ExternalMessageFriendlyException {

	public FeedbackItemNotFoundException() {
	}
	
	public FeedbackItemNotFoundException(String message) {
		super(message);
	}
	
	public FeedbackItemNotFoundException(Throwable t) {
		super(t);
	}
}
