package com.msoncloud.mstorage.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserFilesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String emailId;
	private String fileName;
	private String fileDescription;
	private Timestamp filecreatedTime;
	private Timestamp fileUpdatedTime;
	
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public Timestamp getFilecreatedTime() {
		return filecreatedTime;
	}
	public void setFilecreatedTime(Timestamp filecreatedTime) {
		this.filecreatedTime = filecreatedTime;
	}
	public Timestamp getFileUpdatedTime() {
		return fileUpdatedTime;
	}
	public void setFileUpdatedTime(Timestamp fileUpdatedTime) {
		this.fileUpdatedTime = fileUpdatedTime;
	}

}
