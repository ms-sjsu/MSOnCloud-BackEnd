package com.msoncloud.mstorage.dto;

import java.io.Serializable;

public class UserSignInRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String emailId;
	private String role;
	private String password;

	public UserSignInRequest() {
	}

	public UserSignInRequest(String firstName, String lastName, String emailId, String role, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		if(role == "") {
			this.role = "0";
		} else {
			this.role = role;
		}
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "UserSignInRequest [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				 + ", userRole=" + role + ", password=" + password + "]";
	}
}
