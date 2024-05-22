package com.gullidge.retrospective.datastore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gullidge.retrospective.configuration.JacksonConfiguration;
import com.gullidge.retrospective.model.FeedbackDto;
import com.gullidge.retrospective.model.RetrospectiveDto;

@Repository
public class RetrospectiveDataStore {

	private static final Logger logger = LogManager.getLogger(RetrospectiveDataStore.class.getName());

	Map<String, RetrospectiveDto> retrospectiveDtos = new TreeMap<>();
	
	/**
	 * Get the retrospective with the given name
	 * @param name The name of the retrospective (case sensitive)
	 * @return The retrospective if it has been saved or null
	 */
	public RetrospectiveDto getRetrospective(String name) {
		logger.debug("getRetrospective method called");
		return retrospectiveDtos.get(name);
	}
	
	/**
	 * Add a retrospective to the datastore
	 * @param retrospectiveDto The retrospective to save
	 * @throws DuplicateRetrospectiveException when there is already a retrospective with the given name
	 */
	public void addRetrospective(RetrospectiveDto retrospectiveDto) throws DuplicateRetrospectiveException {
		logger.debug("addRetrospective method called");
		if (retrospectiveDto != null) {
			String retrospectiveName = retrospectiveDto.getName();
			if (getRetrospective(retrospectiveName) != null) {
				logger.debug("Trying to create a retrospective with a duplicate name: " + retrospectiveDto.getName());
				throw new DuplicateRetrospectiveException("Trying to create a retrospective with a duplicate name: " + retrospectiveDto.getName());
			}
		}
		retrospectiveDtos.put(retrospectiveDto.getName(), retrospectiveDto);
	}

	/**
	 * Not required for this implementation
	 * Update the retrospective if it already exists or save it if it does not already exist
	 * @param retrospectiveDto
	 */
	public void updateOrSaveRetrospective(RetrospectiveDto retrospectiveDto) {
		logger.debug("updateOrSaveRetrospective method called");
		if (retrospectiveDto != null) {
			String retrospectiveName = retrospectiveDto.getName();
			retrospectiveDtos.put(retrospectiveName, retrospectiveDto);
		}
	}
	
	/**
	 * Not required for this implementation
	 * Remove the retrospective from the data store
	 * @param retrospectiveDto The retrospective to remove
	 */
	public void deleteRetrospective(RetrospectiveDto retrospectiveDto) {
		logger.debug("deleteRetrospective method called");
		if (retrospectiveDto != null) {
			String retrospectiveName = retrospectiveDto.getName();
			retrospectiveDtos.remove(retrospectiveName);
		}
	}

	/**
	 * Add a feedback item to a retrospective
	 * @param retrospectiveName The name of the retrospective to which to add the feedback
	 * @param feedback The feedback to add
	 * @throws RetrospectiveNotFoundException when the specified retrospective name is not found
	 */
	public void addFeedback(String retrospectiveName, FeedbackDto feedback) throws RetrospectiveNotFoundException {
		logger.debug("addFeedback method called");
		RetrospectiveDto retrospectiveDto = getRetrospective(retrospectiveName);
		if (retrospectiveDto == null) {
			throw new RetrospectiveNotFoundException("Retrospective \"" + retrospectiveName + "\" not found");
		}
		List<FeedbackDto> feedbackList = retrospectiveDto.getFeedbackItems();
		feedbackList.add(feedback);
	}

	/**
	 * Update feedback in a retrospective
	 * @param retrospectiveName The name of the retrospective to which the feedback belongs
	 * @param feedbackItemNumber The feedback item number
	 * @param feedback The new feedback to use
	 * @throws RetrospectiveNotFoundException When the speciied retrospective is not found
	 * @throws FeedbackItemNotFoundException When the feedback item is not found 
	 * @throws UnableToChangeFeedbackNameException When an attempt is made to change the name of the person
	 */
	public void updateFeedback(String retrospectiveName, int feedbackItemNumber, FeedbackDto feedback) throws RetrospectiveNotFoundException, FeedbackItemNotFoundException, UnableToChangeFeedbackNameException {
		logger.debug("updateFeedback method called");
		RetrospectiveDto retrospectiveDto = getRetrospective(retrospectiveName);
		if (retrospectiveDto == null) {
			throw new RetrospectiveNotFoundException("Retrospective \"" + retrospectiveName + "\" not found");
		}
		List<FeedbackDto> feedbackList = retrospectiveDto.getFeedbackItems();
		if (feedbackList.size() < feedbackItemNumber) {
			throw new FeedbackItemNotFoundException("Feedback item " + feedbackItemNumber + " not found");
		}
		FeedbackDto feedbackItemToChange = feedbackList.get(feedbackItemNumber -1);
		String feedbackName = feedback.getName();
		if (feedbackName != null 
				&& feedbackName.trim().length() > 0
				&& !feedbackName.equals(feedbackItemToChange.getName())) {
			throw new UnableToChangeFeedbackNameException("Not allowed to change feedback person's name to \"" + feedbackName + "\"");
		}
		feedbackItemToChange.setBody(feedback.getBody());
		feedbackItemToChange.setFeedbackType(feedback.getFeedbackType());
	}
	
	/**
	 * Get a list of retrospectives for a given date
	 * @param date The date of the retrospective or null to return all retrospectives
	 * @return The list of retrospectives
	 */
	public List<RetrospectiveDto> getRetrospectives(String date) {
		logger.debug("getRetrospectives(date) method called");
		List<RetrospectiveDto> valueToReturn = new ArrayList<>();
		if (date == null) {
			valueToReturn.addAll(retrospectiveDtos.values());
		}
		else {
			logger.debug("Date = " + date);
			LocalDate dateToFind = LocalDate.parse(date, JacksonConfiguration.DATE_FORMATTER);
			valueToReturn = retrospectiveDtos.values().stream()
				.filter(retrospective -> retrospective.getDate().isEqual(dateToFind))
				.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		}
		return valueToReturn;
	}
}
