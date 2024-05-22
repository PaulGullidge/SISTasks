package com.gullidge.retrospective.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class RetrospectiveDto {

	@NotBlank(message = "The name of the retrospective is required.")
	private String name;
	private String summary;
	@NotNull(message = "The date of the retrospective is required.")
	private LocalDate date;
	@NotEmpty(message = "There must be some participants supplied.")
	private List<String> participants;
	private List<FeedbackDto> feedbackItems = new ArrayList<>();

	/**
	 * get the name of the retrospective
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the retrospective
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the summary
	 * @return
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * Set the summary
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * Get the date that the retrospective occurred
	 * @return
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Set the date thet the retrospective occurred
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * Get a list of the participants
	 * @return
	 */
	public List<String> getParticipants() {
		return participants;
	}
	
	/**
	 * Set the list of the participants
	 * @param participants
	 */
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
	/**
	 * Get a list of the feedback items from the retrospective
	 * @return
	 */
	public List<FeedbackDto> getFeedbackItems() {
		return feedbackItems;
	}
	
	/**
	 * Set a list of the feedback items from the retrospective
	 * @param feedbackItems
	 */
	public void setFeedbackItems(List<FeedbackDto> feedbackItems) {
		this.feedbackItems = feedbackItems;
	}

	@Override
	public String toString() {
		return "RetrospectiveDto [name=" + name + ", summary=" + summary + ", date=" + date + ", participants="
				+ participants + ", feedbackItems=" + feedbackItems + "]";
	}
	
	
	
}
