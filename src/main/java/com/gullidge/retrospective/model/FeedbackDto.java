package com.gullidge.retrospective.model;

public class FeedbackDto {

	private String name;
	private String body;
	private FeedbackType feedbackType;

	/**
	 * Get the name of the person giving the feedback
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the person giving the feedback
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the content of the feedback
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set the content of the feedback
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Get the feedback type
	 * @return
	 */
	public FeedbackType getFeedbackType() {
		return feedbackType;
	}

	/**
	 * Set the feedback type
	 * @param feedbackType
	 */
	public void setFeedbackType(FeedbackType feedbackType) {
		this.feedbackType = feedbackType;
	}

	@Override
	public String toString() {
		return "FeedbackDto [name=" + name + ", body=" + body + ", feedbackType=" + feedbackType + "]";
	}
	
	
}
