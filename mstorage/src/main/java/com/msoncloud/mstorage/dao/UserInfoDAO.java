
package com.msoncloud.mstorage.dao;

import java.util.List;

import javax.xml.bind.ValidationException;

import com.msoncloud.mstorage.dto.UserDeleteFileRequest;
import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserFilesDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;

public interface UserInfoDAO {
	
	void createUser(UserSignInRequest userRequest);

	boolean validateUser(UserLoginRequest userLoginRequest) throws ValidationException;
	
	UserDetailsDTO getUserDetails(UserLoginRequest userLoginRequest) throws ValidationException;

	UserDetailsDTO isAdminUser(String emailId);
	
	List<UserFilesDTO> getUserFiles(String emailId);
	
	void insertUserFile(String firstName, String lastName, String emailId, String fileName, String fileDescription);
	
	void updateUserFile(String emailId, String fileName, String fileDescription);
	
	void deleteUserFile(UserDeleteFileRequest deleteRequest);
	
	List<UserFilesDTO> getAdminFiles();

}
