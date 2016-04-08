package com.tinder.model.pojo.chat;

import java.time.LocalDateTime;

public class OutgoingMessage {
	private String sender;
	private LocalDateTime timeOfSending;
	private String message;
	
	public OutgoingMessage(String message, String sender, LocalDateTime timeOfSending) {
		this.message = message;
		this.sender = sender;
		this.timeOfSending = timeOfSending;
	}

	public OutgoingMessage() {
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public LocalDateTime getTimeOfSending() {
		return timeOfSending;
	}

	public void setTimeOfSending(LocalDateTime timeOfSending) {
		this.timeOfSending = timeOfSending;
	}
}
