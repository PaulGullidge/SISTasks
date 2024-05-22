package com.gullidge.retrospective.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gullidge.retrospective.datastore.RetrospectiveDataStore;
import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;
import com.gullidge.retrospective.model.FeedbackDto;
import com.gullidge.retrospective.model.RetrospectiveDto;
import com.gullidge.retrospective.pagination.RetrospectiveResultsPager;

@Service
public class RetrospectiveService {

	private static final Logger logger = LogManager.getLogger(RetrospectiveService.class.getName());
	
	@Autowired
	RetrospectiveDataStore retrospectiveDataStore;

	public RetrospectiveDto getRetrospective(String name) throws ExternalMessageFriendlyException {
		return retrospectiveDataStore.getRetrospective(name);
	}
	
	public void createRetrospective(RetrospectiveDto retrospectiveDto) throws ExternalMessageFriendlyException {
		retrospectiveDataStore.addRetrospective(retrospectiveDto);
	}

	public void updateRetrospective(RetrospectiveDto retrospectiveDto) throws ExternalMessageFriendlyException {
		retrospectiveDataStore.updateOrSaveRetrospective(retrospectiveDto);
	}
	
	public void deleteRetrospective(RetrospectiveDto retrospectiveDto) throws ExternalMessageFriendlyException {
		retrospectiveDataStore.deleteRetrospective(retrospectiveDto);
	}
	
	public void addFeedback(String retrospectiveName, FeedbackDto feedback) throws ExternalMessageFriendlyException {
		retrospectiveDataStore.addFeedback(retrospectiveName, feedback);
	}

	public void updateFeedback(String retrospectiveName, int feedbackItemNumber, FeedbackDto feedback) throws ExternalMessageFriendlyException {
		retrospectiveDataStore.updateFeedback(retrospectiveName, feedbackItemNumber, feedback);
	}
	
	public List<RetrospectiveDto> getRetrospectives(int currentPage, int pageSize) throws ExternalMessageFriendlyException {
		return getRetrospectives(null, currentPage, pageSize);
	}

	public List<RetrospectiveDto> getRetrospectives(String retrospectiveDate, int currentPage, int pageSize) throws ExternalMessageFriendlyException {
		List<RetrospectiveDto> retrospectives = retrospectiveDataStore.getRetrospectives(retrospectiveDate);
		return RetrospectiveResultsPager.getPagedView(retrospectives, currentPage, pageSize);
	}
	
}
