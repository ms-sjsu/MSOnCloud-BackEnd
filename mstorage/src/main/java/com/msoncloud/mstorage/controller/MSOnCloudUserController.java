package com.msoncloud.mstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.msoncloud.mstorage.dto.UserDetailsDTO;
import com.msoncloud.mstorage.dto.UserLoginRequest;
import com.msoncloud.mstorage.dto.UserSignInRequest;
import com.msoncloud.mstorage.service.UserService;

@RestController
@RequestMapping("/api/user")
public class MSOnCloudUserController {

    @Autowired
    private UserService userService;
    
    //User sign up request mapping
    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserDetailsDTO> userSignIn(@RequestBody UserSignInRequest userRequest) {
    	ResponseEntity<UserDetailsDTO> responseEntity = null;
		try {
			UserDetailsDTO response = this.userService.userSignupRequest(userRequest);
			responseEntity = new ResponseEntity<UserDetailsDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<UserDetailsDTO>(new UserDetailsDTO(), HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
    
    //User login request mapping
    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserDetailsDTO> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
		ResponseEntity<UserDetailsDTO> responseEntity = null;
		try {
			UserDetailsDTO response = this.userService.userSigninRequest(userLoginRequest);
			responseEntity = new ResponseEntity<UserDetailsDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<UserDetailsDTO>(new UserDetailsDTO(), HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
    
    // Check if user is admin or not
    @RequestMapping(value = "/isAdmin", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserDetailsDTO> isAdmin(@RequestParam String emailId) {
    	ResponseEntity<UserDetailsDTO> responseEntity = null;
		try {
			UserDetailsDTO response = this.userService.isAdminUser(emailId);
			responseEntity = new ResponseEntity<UserDetailsDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<UserDetailsDTO>(new UserDetailsDTO(), HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
}