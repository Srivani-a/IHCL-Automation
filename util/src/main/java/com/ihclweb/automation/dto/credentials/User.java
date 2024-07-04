package com.ihclweb.automation.dto.credentials;

import java.util.Objects;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class User {

	private String id;
	private String name;
	private String type;
	private String email;
	private String password;
	private String location;
	private String mobileno;

	public User(String id, String name, String type, String user, String password, String mobilenum) {
		this(type, user, mobilenum, password);
		this.id = Objects.requireNonNull(id, "Login ID cannot be null for LoginDetails");
		this.name = name;
	}

	public User(String type, String email, String password, String mobilenum) {
		super();
		this.type = Objects.requireNonNull(type, "Type cannot be null for LoginDetails");
		this.email = Objects.requireNonNull(email, "Email cannot be null for LoginDetails");
		this.mobileno = Objects.requireNonNull(mobilenum, "Password cannot be null for LoginDetails");
		this.password = Objects.requireNonNull(password, "Password cannot be null for LoginDetails");
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", type=" + type + ", email=" + email + ", password=" + password
				+ ", location=" + location + "]";
	}

	
}