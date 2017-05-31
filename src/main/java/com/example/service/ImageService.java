package com.example.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.ImageDao;

@Service
public class ImageService {
	@Autowired
	ImageDao imageDao;
	
	public void saveImage(File file) {
		imageDao.save(file);
	}
}