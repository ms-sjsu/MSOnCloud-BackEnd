package com.msoncloud.mstorage.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.CollectionUtils;
import com.msoncloud.mstorage.dao.UserInfoDAO;
import com.msoncloud.mstorage.dto.APIResponse;
import com.msoncloud.mstorage.dto.UserDeleteFileRequest;
import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserFilesDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;
import com.msoncloud.mstorage.service.AmazonClientService;

@Component
public class AmazonClientServiceImpl implements AmazonClientService
{
    private String awsS3AudioBucket;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonClientServiceImpl.class);

    @Autowired
    public AmazonClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket)
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }
    
    @Autowired
    private UserInfoDAO userDAO;

    @Async
    public UserFilesDTO uploadFileToS3Bucket(MultipartFile multipartFile, String firstName, String lastName, String emailId, 
    		String fileName, String fileDescription, boolean enablePublicReadAccess)
    {
    	// Check if file size is greater than 10MB
    			int maxSizeInBytes = 10485760;
    			if (multipartFile.getSize() > maxSizeInBytes) {
    				try {
						throw new ValidationException("File Size larger than 10MB");
					} catch (ValidationException e) {
						e.printStackTrace();
					}
    			}
        String uploadFileName = multipartFile.getOriginalFilename();
        
        UserFilesDTO response = null;

        try {
            //creating the file in the server (temporarily)
            File file = new File(uploadFileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            
            //Map<String, String> fileProperties = new HashMap<String, String>();
            String key = emailId + "/" + uploadFileName;

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, key, file);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
            
            //code to update the db 
            response = insertAndUpdateFileDetails(firstName, lastName, emailId, fileName, fileDescription);
            
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + uploadFileName + "] ");
        }
		return response;
    }
    
    private UserFilesDTO insertAndUpdateFileDetails(String firstName, String lastName, String emailId, String fileName, String fileDescription) {
    	
    	boolean fileMatch = false;
    	UserFilesDTO uploadedFile = new UserFilesDTO();
    	uploadedFile.setFirstName(firstName);
    	uploadedFile.setLastName(lastName);
    	uploadedFile.setEmailId(emailId);
    	uploadedFile.setFileName(fileName);
    	uploadedFile.setFileDescription(fileDescription);
    	
    	List<UserFilesDTO> userFiles = userDAO.getUserFiles(emailId);
    	
    	if (!CollectionUtils.isNullOrEmpty(userFiles)) {
    		
    		for(UserFilesDTO userFilesDTO : userFiles)
        	{
        		fileMatch = userFilesDTO.getFileName().equalsIgnoreCase(fileName);
        	}
    		if (fileMatch) {
    			//update the files table with new data
    			userDAO.updateUserFile(emailId, fileName, fileDescription);
    		}
    		else {
    			//insert the new file data for the existing user 
    			userDAO.insertUserFile(firstName, lastName, emailId, fileName, fileDescription);
    		}
    	}
    	else{
    		//insert query goes here to for files and new user
    		userDAO.insertUserFile(firstName, lastName, emailId, fileName, fileDescription);
    	}
    	
		return uploadedFile;
    	
    }

    @Async
    public void deleteFileFromS3Bucket(String fileName)
    {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }

	@Override
	public APIResponse newUserSignInRequest(UserSignInRequest userRequest) {
		
		userDAO.createUser(userRequest);
		APIResponse response = new APIResponse("SUCCESS");
		return response;
	}

	@Override
	public UserDetailsDTO userLogin(UserLoginRequest userLoginRequest) throws ValidationException {
		UserDetailsDTO userDetailsDTO = userDAO.getUserDetails(userLoginRequest);
		if (null == userDetailsDTO) {
			throw new ValidationException("Invalid email Id/password");
		}
		return userDetailsDTO;
	}

	@Override
	public List<UserFilesDTO> getUserFiles(String emailId) {
		
		List<UserFilesDTO> userfiles = userDAO.getUserFiles(emailId);
		
		return userfiles;
	}

	@Override
	public void deleteFileAndUpdateDB(UserDeleteFileRequest deleteRequest) {
		String filePath = deleteRequest.getEmailId() + "/" + deleteRequest.getFileName();
		try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, filePath));
            
            userDAO.deleteUserFile(deleteRequest);
            
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + filePath + "] ");
        }
	}
	
	@Override
	public APIResponse adminLogin(UserLoginRequest userLoginRequest) throws ValidationException {
		UserDetailsDTO userDetailsDTO = userDAO.getUserDetails(userLoginRequest);
		APIResponse response = null;
		if (null == userDetailsDTO) {
			throw new ValidationException("Invalid email Id/password");
		}
		else {
			response = new APIResponse("SUCCESS");
		}
		return response;
	}

	@Override
	public List<UserFilesDTO> getAdminFiles() {
		
		List<UserFilesDTO> adminFiles = userDAO.getAdminFiles();
		
		return adminFiles;
	}
	
}