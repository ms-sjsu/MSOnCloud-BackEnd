package com.msoncloud.mstorage.dto;

import java.io.Serializable;
import java.util.List;

public class UserFilesListDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	List<UserFilesDTO> userFilesDTO;

	public List<UserFilesDTO> getUserFilesDTO() {
		return userFilesDTO;
	}

	public void setUserFilesDTO(List<UserFilesDTO> userFilesDTO) {
		this.userFilesDTO = userFilesDTO;
	}

	
}
