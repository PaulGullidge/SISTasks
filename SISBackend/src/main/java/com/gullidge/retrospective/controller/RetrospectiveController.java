package com.gullidge.retrospective.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;
import com.gullidge.retrospective.model.FeedbackDto;
import com.gullidge.retrospective.model.RetrospectiveDto;
import com.gullidge.retrospective.service.RetrospectiveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="v1/retrospective")
public class RetrospectiveController {

	private static final Logger logger = LogManager.getLogger(RetrospectiveController.class.getName());
	
	
	@Autowired
	private RetrospectiveService retrospectiveService;
	
	/**
	 * Not specified as required but gets a single retrospective's details
	 * @param retrospectiveName The retrospective to retain
	 * @return The retrospective
	 * @throws ExternalMessageFriendlyException when an error occurs that has a user friendly message
	 * that can be shown to an external caller
	 */
	@GetMapping(value="/{retrospectiveName}")
	public ResponseEntity<RetrospectiveDto> getRetrospective(
			@PathVariable("retrospectiveName") String retrospectiveName) throws ExternalMessageFriendlyException {
		logger.debug("getRetrospective method called");
		RetrospectiveDto retrospective = retrospectiveService.getRetrospective(retrospectiveName);
		return ResponseEntity.ok(retrospective);
	}
	
	/**
	 * Create a new retrospective
	 * @param retrospective The retrospective details
	 * @return A simple confirmation message to say that the retrospective has been created 
	 * @throws ExternalMessageFriendlyException When an error occurs that has an externally 
	 * presentable error message (such as the DuplicateRetrospectiveException)
	 */
	@PostMapping()
	public ResponseEntity<String> createRetrospective(
		@Valid @RequestBody RetrospectiveDto retrospective) throws ExternalMessageFriendlyException {
		logger.debug("createRetrospective method called");
		retrospectiveService.createRetrospective(retrospective);
		return ResponseEntity.ok("created retrospective: " + retrospective.getName());
	}
	
	/**
	 * Create new feedback for a retrospective
	 * @param retrospectiveName The name of the retrospective to which to add feedback
	 * @param feedback The request body containing the feedback
	 * @return A user friendly message to say that the feedback has been added
	 * @throws ExternalMessageFriendlyException When an error occurs that has an externally 
	 * presentable error message (such as the RerospectiveNotFoundException)
	 */
	@PostMapping(value="/feedback/{retrospectiveName}")
	public ResponseEntity<String> addFeedbackToRetrospective(
			@PathVariable("retrospectiveName") String retrospectiveName,
			@Valid @RequestBody FeedbackDto feedback) throws ExternalMessageFriendlyException {
		logger.debug("addFeedbackToRetrospective method called");
		retrospectiveService.addFeedback(retrospectiveName, feedback);
		return ResponseEntity.ok("Added feedback for retrospective: " + retrospectiveName);
	}

	/**
	 * Update a feedback item 
	 * @param retrospectiveName The name of the retrospective to which to update feedback
	 * @param feedbackItemNumber The feedback item in the list (starting from 1 for the first
	 * item in the list) 
	 * @param feedback The request body containing the feedback
	 * @return A user friendly message to say that the feedback has been updated
	 * @throws ExternalMessageFriendlyException When an error occurs that has an externally 
	 * presentable error message (such as the RerospectiveNotFoundException or FeedbackItemNotFoundException)
	 */
	@PutMapping(value="/feedback/{retrospectiveName}/{feedbackItemNumber}")
	public ResponseEntity<String> updateFeedbackItem(
			@PathVariable("retrospectiveName") String retrospectiveName,
			@PathVariable("feedbackItemNumber") int feedbackItemNumber,
			@Valid @RequestBody FeedbackDto feedback) throws ExternalMessageFriendlyException {
		logger.debug("updateFeedbackItem method called");
		retrospectiveService.updateFeedback(retrospectiveName, feedbackItemNumber, feedback);
		return ResponseEntity.ok("Updated feedback for retrospective: " + retrospectiveName);
	}
	
	/**
	 * Get a list of all retrospectives using pagination.  Specify the Accept header to say whether
	 * the response is JSON or XML (application/xml or application/json)
	 * @param currentPage The required page
	 * @param pageSize The number of entries per page
	 * @param accept The Accept request header value
	 * @return The list of retrospectives of the current page
	 * @throws ExternalMessageFriendlyException when an error occurs that has a user friendly message
	 * that can be shown to an external caller (such as CurrentPageOutOfRangeException)
	 */
	@GetMapping(value="/{currentPage}/{pageSize}")
	public ResponseEntity<List<RetrospectiveDto>> getRetrospectives(
			@PathVariable("currentPage") int currentPage,
			@PathVariable("pageSize") int pageSize,
			@RequestHeader(value = "Accept", required = false, defaultValue = "*/*") String accept) throws ExternalMessageFriendlyException {
		logger.debug("getRetrospectives method called (int, int, MediaType)");
		List<RetrospectiveDto> retrospectives = retrospectiveService.getRetrospectives(currentPage, pageSize);

		HttpHeaders responseHeaders = new HttpHeaders();
    	responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			MediaType mediaType = MediaType.parseMediaType(accept);
		    if(!accept.toString().equals("*/*")) {
		    	if (mediaType.includes(MediaType.APPLICATION_XML)) {
		    		responseHeaders.setContentType(MediaType.APPLICATION_XML);
		    	}
		    }
		}
		catch (Exception error) {
			// Looks like browsers don't necessarily pass in a parsable media type so
			// now accepting as a string, if it isn't recognisable then send JSON back
			logger.debug("Invalid accept string passed: " + accept);
		}
	    return new ResponseEntity<>(retrospectives, responseHeaders, HttpStatus.OK);
	}	

	/**
	 * Get a list of retrospectives using pagination.  Specify the Accept header to say whether
	 * the response is JSON or XML (application/xml or application/json)
	 * @param retrospectiveDate The date of the retrospectives to return
	 * @param currentPage The required page
	 * @param pageSize The number of entries per page
	 * @param accept The Accept request header value
	 * @return The list of retrospectives with the specified date for the current page
	 * @throws ExternalMessageFriendlyException when an error occurs that has a user friendly message
	 * that can be shown to an external caller (such as CurrentPageOutOfRangeException)
	 */
	@GetMapping(value="/datesearch/{currentPage}/{pageSize}")
	public ResponseEntity<List<RetrospectiveDto>> getRetrospective(
			@RequestParam("retrospectiveDate") String retrospectiveDate,
			@PathVariable("currentPage") int currentPage,
			@PathVariable("pageSize") int pageSize,
			@RequestHeader(value = "Accept", required = false, defaultValue = "*/*") String accept) throws ExternalMessageFriendlyException {
		logger.debug("getRetrospectives method called (String, int, int, MediaType)");
		List<RetrospectiveDto> retrospectives = retrospectiveService.getRetrospectives(retrospectiveDate, currentPage, pageSize);

		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			MediaType mediaType = MediaType.parseMediaType(accept);
		    if(!accept.toString().equals("*/*")) {
		    	if (mediaType.includes(MediaType.APPLICATION_XML)) {
		    		responseHeaders.setContentType(MediaType.APPLICATION_XML);
		    	}
		    }
		}
		catch (Exception error) {
			// Looks like browsers don't necessarily pass in a parsable media type so
			// now accepting as a string, if it isn't recognisable then send JSON back
			logger.debug("Invalid accept string passed: " + accept);
		}
	    return new ResponseEntity<>(retrospectives, responseHeaders, HttpStatus.OK);
	}	
	
}
