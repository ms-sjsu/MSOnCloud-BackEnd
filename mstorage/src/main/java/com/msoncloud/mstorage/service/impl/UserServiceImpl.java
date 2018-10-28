package com.msoncloud.mstorage.service.impl;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.msoncloud.mstorage.dao.UserInfoDAO;
import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;
import com.msoncloud.mstorage.service.UserService;

@Component
public class UserServiceImpl implements UserService
{
    @Autowired
    public UserServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket)
    {
        
    }
    
    @Autowired
    private UserInfoDAO userDAO;

	@Override
	public UserDetailsDTO userSignupRequest(UserSignInRequest userRequest) {
		
		UserLoginRequest loginReq = new UserLoginRequest();
		loginReq.setEmailId(userRequest.getEmailId());
		
		UserDetailsDTO userDetailsDTO = null;
		try {
			userDetailsDTO = userDAO.getUserDetails(loginReq);
		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
		if (null == userDetailsDTO) {
			userDAO.createUser(userRequest);
			try {
				userDetailsDTO = userDAO.getUserDetails(loginReq);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		} else {
			userDetailsDTO = new UserDetailsDTO("User with the provided email id already exists.");
		}

		return userDetailsDTO;
	}

	@Override
	public UserDetailsDTO userSigninRequest(UserLoginRequest userLoginRequest) throws ValidationException {
		
		if(userDAO.validateUser(userLoginRequest)) {
			UserDetailsDTO userDetailsDTO = userDAO.getUserDetails(userLoginRequest);
			if (null == userDetailsDTO) {
				throw new ValidationException("Invalid email Id/password");
			} else {
				return userDetailsDTO;
			}
		} else {
			throw new ValidationException("Invalid email Id/password");
		}
	}
	
	@Override
	public UserDetailsDTO isAdminUser(String emailId) {
		
			return userDAO.isAdminUser(emailId);
	}
}