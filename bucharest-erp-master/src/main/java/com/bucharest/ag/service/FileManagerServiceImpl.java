package com.bucharest.ag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bucharest.ag.dao.FileManagerDao;

@Service
public class FileManagerServiceImpl implements FileManagerService {

	@Autowired
	private FileManagerDao uploadFileDao;

	@Override
	public String upload(MultipartFile file) {
		return uploadFileDao.upload(file);
		
	}
}