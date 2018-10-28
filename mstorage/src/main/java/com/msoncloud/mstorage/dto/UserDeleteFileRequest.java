package com.msoncloud.mstorage.dto;

import java.io.Serializable;

public class UserDeleteFileRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String emailId;
	private String fileName;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
