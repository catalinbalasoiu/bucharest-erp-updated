package com.bucharest.ag.dao;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


@Repository
@Transactional
public class FileManagerDaoImpl implements FileManagerDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String upload(MultipartFile file) {
		List<String> fileNames = new ArrayList<String>();
		
		//CommonsMultipartFile file = document.getFile();
		String originalFileName = file.getOriginalFilename().replaceAll("\\s+","");
		String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
		String fileNoExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
		String serverPath = "\\\\robucs601\\Public\\Temp\\Upload\\";
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String dateString = dateFormat.format(new Date()).replaceAll("[:/ ]", "");
		String serverUploadPath= serverPath + fileNoExtension + dateString + extension;
		String finalFileName = serverUploadPath.replaceAll("[^A-Za-z0-9]","");
			try {
				FileCopyUtils.copy(file.getBytes(), new File(serverUploadPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileNames.add(finalFileName);
			String fileName = fileNoExtension + dateString + extension;
			return fileName;
	}

}	
