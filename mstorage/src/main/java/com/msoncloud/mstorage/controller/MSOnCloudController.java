package com.msoncloud.mstorage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msoncloud.mstorage.dto.APIResponse;
import com.msoncloud.mstorage.dto.UserDeleteFileRequest;
import com.msoncloud.mstorage.dto.UserFilesDTO;
import com.msoncloud.mstorage.service.AmazonClientService;

@RestController
@RequestMapping("/api/files")
public class MSOnCloudController {

    @Autowired
    private AmazonClientService s3ClientService;

    //upload and insert or update files
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserFilesDTO> uploadFile(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "emailId", required = true) String emailId,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "fileDescription", required = true) String fileDescription) {

		ResponseEntity<UserFilesDTO> responseEntity = null;
		try {
			UserFilesDTO response = this.s3ClientService.uploadFileToS3Bucket(file, firstName, lastName, emailId, fileName, fileDescription, true);
			responseEntity = new ResponseEntity<UserFilesDTO>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<UserFilesDTO>(new UserFilesDTO(),
					HttpStatus.EXPECTATION_FAILED);
		}

		return responseEntity;
	}
    
    //delete file from S3 and DB
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<APIResponse> deleteFile(@RequestBody UserDeleteFileRequest deleteRequest) {
		ResponseEntity<APIResponse> responseEntity = null;
		try {
			s3ClientService.deleteFileAndUpdateDB(deleteRequest);
			responseEntity = new ResponseEntity<APIResponse>(new APIResponse("SUCCESS"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<APIResponse>(new APIResponse(e.getMessage()),
					HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
    
    //get the list of user files information
    @RequestMapping(value = "/getUserFiles", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<UserFilesDTO>> getUserFiles(@RequestParam String emailId) {
    	ResponseEntity<List<UserFilesDTO>> responseEntity = null;
		try {
			
			List<UserFilesDTO> userFilesListDTO = this.s3ClientService.getUserFiles(emailId);
			responseEntity = new ResponseEntity<List<UserFilesDTO>>(userFilesListDTO, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<List<UserFilesDTO>>(HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
    
    //get the list of admin files information
    @RequestMapping(value = "/getAdminFiles", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<UserFilesDTO>> getAdminFiles() {
    	ResponseEntity<List<UserFilesDTO>> responseEntity = null;
		try {
			
			List<UserFilesDTO> adminFilesList = this.s3ClientService.getAdminFiles();
			responseEntity = new ResponseEntity<List<UserFilesDTO>>(adminFilesList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<List<UserFilesDTO>>(HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}
    
}