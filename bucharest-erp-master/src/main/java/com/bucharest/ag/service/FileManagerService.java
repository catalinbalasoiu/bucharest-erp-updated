package com.bucharest.ag.service;


import org.springframework.web.multipart.MultipartFile;

import com.bucharest.ag.model.Document;


public interface FileManagerService {
	public String upload(MultipartFile file);
}
