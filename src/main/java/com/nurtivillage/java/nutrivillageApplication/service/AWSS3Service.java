package com.nurtivillage.java.nutrivillageApplication.service;

import java.io.ByteArrayOutputStream;

import org.springframework.web.multipart.MultipartFile;

import comnurtivillage.java.nutrivillageApplication.model.UserProfile;

public interface AWSS3Service {

	void uploadFile(MultipartFile multipartFile,String userId)  throws Exception;
	
	void uploadGenericFile(MultipartFile multipartFile)  throws Exception;
	
	public ByteArrayOutputStream downloadFile(String keyName,UserProfile profile);
	
	public ByteArrayOutputStream downloadFile(String keyName);
	
	
}
