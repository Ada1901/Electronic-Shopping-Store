package com.lcwa.electonic.store.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lcwa.electonic.store.exception.BadRequestAPI;
import com.lcwa.electonic.store.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	// abc.png
	public String uploadFile(MultipartFile file, String path) throws IOException {
		String originaFilename = file.getOriginalFilename();
		logger.info("FileName: {}", originaFilename);
		String filename = UUID.randomUUID().toString();
		String extension = originaFilename.substring(originaFilename.lastIndexOf("."));
		String fileNameWithExtension = filename + extension;
		String fullPathWithFileNamee = path + fileNameWithExtension;

		if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")
				|| extension.equalsIgnoreCase(".jpeg")) {

			// File Save
			File folder = new File(path);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			// upload
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileNamee));
			return fileNameWithExtension;
		} else {
			throw new BadRequestAPI("File with this" + extension + " is not allowed");
		}
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {

		String fullPath=path+File.separator+name;
		InputStream inputStream=new FileInputStream(fullPath);
		return inputStream;
	}

}
