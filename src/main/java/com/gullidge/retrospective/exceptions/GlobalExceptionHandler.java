package com.gullidge.retrospective.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());

	/**
	 * Put the method arguments into a nicely formatted list (handles the @NotEmpty, @NotBlank, etc validation)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    
	    List<String> errors = ex.getBindingResult()
	        .getFieldErrors()
	        .stream()
	        .map(DefaultMessageSourceResolvable::getDefaultMessage)
	        .collect(Collectors.toList());

	    Map<String, List<String>> body = getErrorsMap(errors);
	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle Exception classes.  If the exception extends ExternalMessageFriendlyException then
	 * we can send the message back to the user, otherwise log it with a timestamp reference and
	 * hide the actual exception from the user but send them the timestamp to retrieve from the logs  
	 * @param ex Any exception
	 * @return The appropriate response to the exception
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
		List<String> errors = null;
		if (ex instanceof ExternalMessageFriendlyException) {
			errors = Collections.singletonList(ex.getMessage());
		}
		else {
			errors = new ArrayList<>();
			long timeOfError = new Date().getTime();
			errors.add("Problem(E) logged at: " + timeOfError);
			logger.error("Unhandled Exception at: " + timeOfError, ex);
		}
	    Map<String, List<String>> body = getErrorsMap(errors);
	    return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle RuntimeException classes.  If the exception extends ExternalMessageFriendlyRuntimeException then
	 * we can send the message back to the user, otherwise log it with a timestamp reference and
	 * hide the actual exception from the user but send them the timestamp to retrieve from the logs  
	 * @param ex Any exception
	 * @return The appropriate response to the exception
	 */
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
		List<String> errors = null;
		if (ex instanceof ExternalMessageFriendlyRuntimeException) {
			errors = Collections.singletonList(ex.getMessage());
		}
		else {
			errors = new ArrayList<>();
			long timeOfError = new Date().getTime();
			errors.add("Problem(R) logged at: " + timeOfError);
			logger.error("Unhandled RuntimeException at: " + timeOfError, ex);
		}
	    Map<String, List<String>> body = getErrorsMap(errors);
	    return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Create a map containing a single entry called "error" that points to the list of errors
	 * passed into the method.
	 * @param errors The list of error messages
	 * @return A map containing a single entry
	 */
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }	
	
}
