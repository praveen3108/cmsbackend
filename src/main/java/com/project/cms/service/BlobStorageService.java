package com.project.cms.service;

import java.net.URI;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {
	public URI uploadPicture(MultipartFile multipartFile);
}
