package com.msoncloud.mstorage.dto;

import java.io.Serializable;

public class UserLoginRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	String emailId;
	String password;
	
	public UserLoginRequest() {
	}

	public UserLoginRequest(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
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

	@Override
	public String toString() {
		return "UserLoginRequest [emailId=" + emailId + ", password=" + password + "]";
	}

}
