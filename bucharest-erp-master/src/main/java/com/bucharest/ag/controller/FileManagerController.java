package com.bucharest.ag.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.service.DocumentService;
import com.bucharest.ag.service.FileManagerService;

@Controller
@RequestMapping(value = "/")
public class FileManagerController {

	@Autowired
	FileManagerService uploadFileService;

	@Autowired
	DocumentService documentService;

	private String filePath = "\\\\robucs601\\Public\\Temp\\Upload\\";

	@RequestMapping(value = "upload-file", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());
		System.out.println(mpf.getOriginalFilename() + " uploaded!");
		String fileName = uploadFileService.upload(mpf);
		return fileName;
	}

	@RequestMapping(value = "download-file", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFile(@RequestParam("docId") int docId,
			HttpServletResponse response) throws IOException {
		Document document = documentService.getDocumentById(docId);

		File file = new File(filePath + document.getFileName());
		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection
				.guessContentTypeFromName(file.getName());
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(
				file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}
