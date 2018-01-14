package com.bucharest.ag.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bucharest.ag.model.Document;



public interface FileManagerDao {
	public String upload(MultipartFile file);
}
