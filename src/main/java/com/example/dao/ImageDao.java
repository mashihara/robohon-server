package com.example.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;



@Repository
public class ImageDao {
	//フルパスでファイルの格納先を定義する
	private String imageFileDir = "/Users/birdman/mashihara/tmp/";
	
	public void save(MultipartFile imageFile) {
		if (imageFile.getSize() > 0) {
			String imageFileName = imageFile.getOriginalFilename();
			
			Path path = Paths.get(imageFileDir+imageFileName);
			File file = path.toFile();
			try {
				imageFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}