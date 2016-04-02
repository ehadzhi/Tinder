package com.tinder.model.pojo;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 4137562350260262508L;
	
	private int id;
	private String username;
	private String passwordHash;
	private int age;
	private boolean genderIsMale;
	private String avatarName;
	private String email;
	private boolean wantsMale;
	private boolean wantsFemale;
	private double latitude;
	private double longitude;
	private int searchDistance;
	private int maxDesiredAge;
	private int minDesiredAge;
	private String fullName;
	private String description;

	public User(int id, String username, String passwordHash, int age, boolean genderIsMale, String avatarName,
			String email, boolean wantsMale, boolean wantsFemale, double latitude, double longitude, int searchDistance,
			int maxDesiredAge, int minDesiredAge,String fullName,String description) {
		super();
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.age = age;
		this.genderIsMale = genderIsMale;
		this.avatarName = avatarName;
		this.email = email;
		this.wantsMale = wantsMale;
		this.wantsFemale = wantsFemale;
		this.latitude = latitude;
		this.longitude = longitude;
		this.searchDistance = searchDistance;
		this.maxDesiredAge = maxDesiredAge;
		this.minDesiredAge = minDesiredAge;
		this.fullName=fullName;
		this.description=description;
	}

	
	
	public String getFullName() {
		return fullName;
	}


	public String getDescription() {
		return description;
	}


	public int getSearchDistance() {
		return searchDistance;
	}

	public void setSearchDistance(int searchDistance) {
		this.searchDistance = searchDistance;
	}

	public int getMaxDesiredAge() {
		return maxDesiredAge;
	}

	public void setMaxDesiredAge(int maxDesiredAge) {
		this.maxDesiredAge = maxDesiredAge;
	}

	public int getMinDesiredAge() {
		return minDesiredAge;
	}

	public void setMinDesiredAge(int minDesiredAge) {
		this.minDesiredAge = minDesiredAge;
	}

	public String getAvatarName() {
		return avatarName;
	}

	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}

	public boolean isWantsMale() {
		return wantsMale;
	}

	public void setWantsMale(boolean wantsMale) {
		this.wantsMale = wantsMale;
	}

	public boolean isWantsFemale() {
		return wantsFemale;
	}

	public void setWantsFemale(boolean wantsFemale) {
		this.wantsFemale = wantsFemale;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public int getAge() {
		return age;
	}

	public boolean isGenderIsMale() {
		return genderIsMale;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", age=" + age
				+ ", genderIsMale=" + genderIsMale + ", avatarName=" + avatarName + ", email=" + email + ", wantsMale="
				+ wantsMale + ", wantsFemale=" + wantsFemale + ", latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}

}