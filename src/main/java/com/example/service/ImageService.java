package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ImageDao;
import com.example.domain.Image;

public class ImageService {
	@Autowired
	Image image;
	@Autowired
	ImageDao imageDao;
	
	List<Image> getAllImages(){
		
	}
	Image save(Image image){
		imageDao.save
	}
	ImageDao getImageDao(){
		return imageDao;
	}
}