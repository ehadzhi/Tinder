package com.tinder.model.pojo;

public class UnconfirmedUser {

	private String uuid;
	private String username;
	private String passwordHash;
	private int age;
	private boolean genderIsMale;
	private String email;
	private String fullName;

	public UnconfirmedUser(String uuid, String username, String passwordHash, int age, boolean genderIsMale, String email,
			String fullName) {
		super();
		this.uuid = uuid;
		this.username = username;
		this.passwordHash = passwordHash;
		this.age = age;
		this.genderIsMale = genderIsMale;
		this.email = email;
		this.fullName = fullName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isGenderIsMale() {
		return genderIsMale;
	}

	public void setGenderIsMale(boolean genderIsMale) {
		this.genderIsMale = genderIsMale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "UnconfirmedUser [uuid=" + uuid + ", username=" + username + ", passwordHash=" + passwordHash + ", age="
				+ age + ", genderIsMale=" + genderIsMale + ", email=" + email + ", fullName=" + fullName + "]";
	}

}
