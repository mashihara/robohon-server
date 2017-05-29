package com.example;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Image;


@Service
public class ImageDao {
	//フルパスでファイルの格納先を定義する
	private String imageSaveDir;
	
	//htdocsの下のパスを設定する
	private String imageAbsoluteSaveDir;
	
	private List<String> imageUrls;
	
	public List<String> getImageUrls() {
		List<String> urls = new ArrayList<String>();
		
		File dir = new File(imageSaveDir);
		File[] files = dir.listFiles();
		
		for (File file : files) {
			String fileName = file.getName();
			String url = "http://localhost/" +imageAbsoluteSaveDir + fileName;
			urls.add(url);
		}
		return urls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getImageAbsoluteSaveDir() {
		return imageAbsoluteSaveDir;
	}

	public void setImageAbsoluteSaveDir(String imageAbsoluteSaveDir) {
		this.imageAbsoluteSaveDir = imageAbsoluteSaveDir;
	}

	public String getImageSaveDir() {
		return imageSaveDir;
	}

	public void setImageSaveDir(String imageSaveDir) {
		this.imageSaveDir = imageSaveDir;
	}
	
	public Image save(MultipartFile imageFile) {
		Image image = new Image();
		
		Date postDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String dateStr = sdf.format(postDate);
		
		
		if (imageFile.getSize() > 0) {
			String imageFileName = dateStr + "_" +imageFile.getOriginalFilename();
			
			image.setName(imageFileName);
			image.setSize(imageFile.getSize());
			image.setPostDate(postDate);
			image.setUrl(imageAbsoluteSaveDir + imageFileName);
			
			//本来はこのimageをDBにつっこむけど、今回は実装しない
			File file = new File(imageSaveDir, imageFileName);
			if (!file.exists()) {
				File dir = new File(imageSaveDir);
				dir.mkdir();
			}
			try {
				imageFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	public List<Image> getAllImages() {
		//別に画像ファイルを送る必要はない
		return null;
	}

	public List<String> getAllImageUrls() {
		
		return getImageUrls();
	}

}