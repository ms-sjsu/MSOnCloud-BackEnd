package com.msoncloud.mstorage.service;

import javax.xml.bind.ValidationException;

import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;

public interface UserService
{   
	UserDetailsDTO userSignupRequest(UserSignInRequest userRequest);
    
    UserDetailsDTO userSigninRequest(UserLoginRequest userLoginRequest) throws ValidationException;
	
    UserDetailsDTO isAdminUser(String emailId);
}