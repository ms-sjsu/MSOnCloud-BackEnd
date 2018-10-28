package com.msoncloud.mstorage.service;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.springframework.web.multipart.MultipartFile;

import com.msoncloud.mstorage.dto.APIResponse;
import com.msoncloud.mstorage.dto.UserDeleteFileRequest;
import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserFilesDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;

public interface AmazonClientService
{
	UserFilesDTO uploadFileToS3Bucket(MultipartFile multipartFile, String firstName, String lastName, String emailId, 
    		String fileName, String fileDescription, boolean enablePublicReadAccess);
    
    void deleteFileFromS3Bucket(String fileName);
    
    APIResponse newUserSignInRequest(UserSignInRequest userRequest);
    
    UserDetailsDTO userLogin(UserLoginRequest userLoginRequest) throws ValidationException;
    
    List<UserFilesDTO> getUserFiles(String emailId);
    
    void deleteFileAndUpdateDB(UserDeleteFileRequest deleteRequest);
    
    APIResponse adminLogin(UserLoginRequest userLoginRequest) throws ValidationException;
    
    List<UserFilesDTO> getAdminFiles();
}