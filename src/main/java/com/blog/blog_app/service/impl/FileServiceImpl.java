package com.blog.blog_app.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blog_app.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) {
		
		String fileName = file.getOriginalFilename();
		
		String randomID = UUID.randomUUID().toString();
		String randomFileName = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		String filePath = path+ File.separator+randomFileName;
		
		//create file if not exists
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randomFileName;
	}

	@Override
	public InputStream getResources(String path, String fileName) throws FileNotFoundException  {
		String fullPath = path+File.separator+fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
