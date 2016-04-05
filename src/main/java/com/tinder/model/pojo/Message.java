package com.tinder.model.pojo;

import java.time.LocalDateTime;

public class Message {
	private int id;
	private int senderId;
	private int chatId;
	private String message;
	private String senderUsername;
	private LocalDateTime createdAt;



	public Message(int id, int senderId, int chatId, String message, String senderUsername, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.chatId = chatId;
		this.message = message;
		this.senderUsername = senderUsername;
		this.createdAt = createdAt;
	}


	public String getSenderUsername() {
		return senderUsername;
	}


	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
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
