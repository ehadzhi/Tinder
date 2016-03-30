package com.tinder.model.pojo;

import java.time.LocalDateTime;

public class Message {
	private int id;
	private int senderId;
	private int chatId;
	private String message;
	private LocalDateTime createdAt;

	public Message(int id, int senderId, int chatId, String message, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.chatId = chatId;
		this.message = message;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public int getSenderId() {
		return senderId;
	}

	public int getChatId() {
		return chatId;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

}
