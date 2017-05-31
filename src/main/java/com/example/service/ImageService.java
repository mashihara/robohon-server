package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.ImageDao;

@Service
public class ImageService {
	@Autowired
	ImageDao imageDao;
	
	public void saveImage(MultipartFile file) {
		// TODO Auto-generated method stub
		imageDao.save(file);
	}
}