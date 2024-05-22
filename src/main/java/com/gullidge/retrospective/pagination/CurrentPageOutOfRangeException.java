package com.gullidge.retrospective.pagination;

import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;

public class CurrentPageOutOfRangeException extends ExternalMessageFriendlyException {

	public CurrentPageOutOfRangeException() {
	}
	
	public CurrentPageOutOfRangeException(String message) {
		super(message);
	}
	
	public CurrentPageOutOfRangeException(Throwable t) {
		super(t);
	}
}
