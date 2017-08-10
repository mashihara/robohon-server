package com.example.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.*;
import com.example.service.ImageService;

@RestController // (1)Restのエンドポイントとなる
@RequestMapping("/") // (2)パスのルートを記載
public class RobohonShuwaRestController {
	// private String imageFileDir = "/Users/birdman/mashihara/tmp/";
	private static final String imageFileDir = "/tmp/";
	private static final String repalceName = "dockerDir";

	@Autowired
	private ImageService imageService;

	// @Value("http://sign_recog:19999/signRecognition?{requestParam}")
	@Value("http://sign_recog:19999/signRecognition?{requestParam}")
	URI uri;

	@PostMapping // @RequestBodyとしてバイナリデータを受け取る
	public ShuwaApiResult getShuwaApiResult(@RequestParam("file1") MultipartFile requestFile, Model model) {
		String imageFileName = requestFile.getOriginalFilename();
		Path path = Paths.get(imageFileDir + imageFileName);
		File file = path.toFile();
		try {
			requestFile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ShuwaApiResult result = new ShuwaApiResult(11, "2");
		return result;
	}

	@PostMapping("finish")
	public ShuwaApiResult getAnswer(
			@RequestParam("img1") String img1
			,@RequestParam("img2") String img2
			,@RequestParam("img3") String img3
			,@RequestParam("img4") String img4
			,@RequestParam("img5") String img5
			,@RequestParam("img6") String img6
			,@RequestParam("img7") String img7
			,@RequestParam("img8") String img8
			,@RequestParam("img9") String img9
			,@RequestParam("img10") String img10
			) {
		StringBuilder urlPath= new StringBuilder("http://sign_recog:19999/signRecognition?");
		createFirstUrl(urlPath,1,img1);
		createUrl(urlPath,2,img2);
		createUrl(urlPath,3,img3);
		createUrl(urlPath,4,img4);
		createUrl(urlPath,5,img5);
		createUrl(urlPath,6,img6);
		createUrl(urlPath,7,img7);
		createUrl(urlPath,8,img8);
		createUrl(urlPath,9,img9);
		createUrl(urlPath,10,img10);
		RestTemplate restTemplate = new RestTemplate();
		ShuwaApiResult result = restTemplate.getForObject(urlPath.toString(), ShuwaApiResult.class);
		return result;
	}
	private void createFirstUrl(StringBuilder urlPath,int i,String imgName){
		urlPath.append("img");
		createCommonUrl(urlPath,i,imgName);
	}

	private void createUrl(StringBuilder urlPath,int i,String imgName){
		urlPath.append("&img");
		createCommonUrl(urlPath,i,imgName);
	}
	private void createCommonUrl(StringBuilder urlPath,int i,String imgName){
		urlPath.append(i);
		urlPath.append("=");
		urlPath.append(imageFileDir);
		urlPath.append(imgName);
	}

}
