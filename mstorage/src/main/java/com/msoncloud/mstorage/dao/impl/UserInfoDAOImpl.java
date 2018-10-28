
package com.msoncloud.mstorage.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.msoncloud.mstorage.dao.UserInfoDAO;
import com.msoncloud.mstorage.dto.UserDeleteFileRequest;
import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserFilesDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;

@Repository("mysql")
public class UserInfoDAOImpl implements UserInfoDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void createUser(UserSignInRequest userRequest) {
		
		String sql = "INSERT INTO msoncloud.UserInfo (FirstName, LastName, EmailId, UserRole, Password) VALUES (?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, userRequest.getFirstName(), userRequest.getLastName(), 
				userRequest.getEmailId(), userRequest.getRole(), userRequest.getPassword());
	}
	
	@Override
	public boolean validateUser(UserLoginRequest userLoginRequest) throws ValidationException {
		
		String sql = "SELECT * FROM msoncloud.UserInfo WHERE EmailId = ? and Password = ?";

		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql, 
				userLoginRequest.getEmailId(), userLoginRequest.getPassword());

			if ( result.isEmpty() ){
			  return false;
			} else if ( result.size() == 1 ) { // list contains exactly 1 element
				return true;
				
			} else {  // list contains more than 1 elements
				throw new ValidationException("More than 1 user with same Email Id exists.");
			}
	}

	@Override
	public UserDetailsDTO getUserDetails(UserLoginRequest userLoginRequest) throws ValidationException {
		
		String sql = "SELECT * FROM msoncloud.UserInfo WHERE EmailId = ?";

		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql, userLoginRequest.getEmailId());

			if ( result.isEmpty() ){
			  return null;
			} else if ( result.size() == 1 ) { // list contains exactly 1 element
				UserDetailsDTO userDetails = new UserDetailsDTO();
				userDetails.setFirstName((String)result.get(0).get("FirstName"));
				userDetails.setLastName((String)result.get(0).get("LastName"));
				userDetails.setEmailId((String)result.get(0).get("EmailId"));
				userDetails.setRole((String)result.get(0).get("UserRole"));
			  
				return userDetails;
				
			} else {  // list contains more than 1 elements
				throw new ValidationException("More than 1 user with same Email Id exists.");
			}
	}
	
	@Override
	public UserDetailsDTO isAdminUser(String emailId) {
		
		String sql = "SELECT * FROM msoncloud.UserInfo WHERE EmailId = ?";

		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql, emailId);

			if ( result.isEmpty() ){
			  return null;
			} else if ( result.size() == 1 ) { // list contains exactly 1 element
				if((String)result.get(0).get("Role") == "1") {
				UserDetailsDTO userDetails = new UserDetailsDTO();
				userDetails.setFirstName((String)result.get(0).get("FirstName"));
				userDetails.setLastName((String)result.get(0).get("LastName"));
				userDetails.setEmailId((String)result.get(0).get("EmailId"));
				userDetails.setRole((String)result.get(0).get("UserRole"));
			  
				return userDetails;
				}
				
			}
			return null;
	}

	@Override
	public List<UserFilesDTO> getUserFiles(String emailId) {
		
		String sql = "SELECT * FROM msoncloud.UserFiles where EmailId = ?";
		
		List<UserFilesDTO> userFilesList = new ArrayList<UserFilesDTO>();
		
		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql, emailId);
		
		for(java.util.Map<String, Object> obj : result)
		{
			UserFilesDTO userFiles = new UserFilesDTO();
			userFiles.setFirstName((String)obj.get("FirstName"));
			userFiles.setLastName((String)obj.get("LastName"));
			userFiles.setEmailId((String)obj.get("EmailId"));
			userFiles.setFileName((String)obj.get("FileName"));
			userFiles.setFileDescription((String)obj.get("FileDescription"));
			userFiles.setFilecreatedTime((Timestamp)obj.get("FileCreatedTime"));
			userFiles.setFileUpdatedTime((Timestamp)obj.get("FileUpdatedTime"));
			userFilesList.add(userFiles);
		}
		
		return userFilesList;
	}

	@Override
	public void insertUserFile(String firstName, String lastName, String emailId, String fileName,
			String fileDescription) {
		
		String sql = "INSERT INTO msoncloud.UserFiles (FirstName, LastName, EmailId, FileName, FileDescription, FileCreatedTime, FileUpdatedTime)"
				+ " VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";
		
		jdbcTemplate.update(sql, firstName, lastName, emailId, fileName, fileDescription);
	}

	@Override
	public void updateUserFile(String emailId, String fileName, String fileDescription) {
		
		String sql = "UPDATE msoncloud.UserFiles SET FileDescription = ?, FileUpdatedTime = CURRENT_TIMESTAMP() WHERE EmailId = ? AND FileName = ?";
		
		jdbcTemplate.update(sql, fileDescription, emailId, fileName);
	}

	@Override
	public void deleteUserFile(UserDeleteFileRequest deleteRequest) {
		
		String sql = "DELETE FROM msoncloud.UserFiles WHERE EmailId = ? AND FileName = ?";
		
		jdbcTemplate.update(sql, deleteRequest.getEmailId(), deleteRequest.getFileName());
		
	}

	@Override
	public List<UserFilesDTO> getAdminFiles() {
		
		String sql = "SELECT * FROM msoncloud.UserFiles";
		
		List<UserFilesDTO> userFilesList = new ArrayList<UserFilesDTO>();
		
		List<java.util.Map<String, Object>> result = jdbcTemplate.queryForList(sql);
		
		for(java.util.Map<String, Object> obj : result)
		{
			UserFilesDTO userFiles = new UserFilesDTO();
			userFiles.setFirstName((String)obj.get("FirstName"));
			userFiles.setLastName((String)obj.get("LastName"));
			userFiles.setEmailId((String)obj.get("EmailId"));
			userFiles.setFileName((String)obj.get("FileName"));
			userFiles.setFileDescription((String)obj.get("FileDescription"));
			userFiles.setFilecreatedTime((Timestamp)obj.get("FileCreatedTime"));
			userFiles.setFileUpdatedTime((Timestamp)obj.get("FileUpdatedTime"));
			userFilesList.add(userFiles);
		}
		
		return userFilesList;
	}
	
	

}
