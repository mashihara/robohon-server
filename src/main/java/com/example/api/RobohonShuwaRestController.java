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

@RestController //(1)Restのエンドポイントとなる
@RequestMapping("/") //(2)パスのルートを記載
public class RobohonShuwaRestController {
	//private String imageFileDir = "/Users/birdman/mashihara/tmp/";
	private static final String imageFileDir = "/tmp/";
	private static final String repalceName = "dockerDir";

	@Autowired	
	private ImageService imageService;

	//@Value("http://sign_recog:19999/signRecognition?{requestParam}")
	@Value("http://sign_recog:19999/signRecognition?{requestParam}")
	URI uri;
	
	@PostMapping //@RequestBodyとしてバイナリデータを受け取る
	public ShuwaApiResult getShuwaApiResult(@RequestParam("file1") MultipartFile requestFile,Model model){
		StringBuilder requestParam = new StringBuilder();
		ShuwaApiResult result=null;
		if(requestFile!=null){
			String imageFileName = requestFile.getOriginalFilename();
			Path path = Paths.get(imageFileDir+imageFileName);
			File file = path.toFile();
			try {
				requestFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			requestParam.append("img1=");
			requestParam.append(imageFileDir);
			requestParam.append(requestFile.getOriginalFilename());
			for(int i=2;i<=10;i++){
				requestParam.append("&img");
				requestParam.append(i);
				requestParam.append("=");
				requestParam.append(imageFileDir);

				requestParam.append(requestFile.getOriginalFilename());
			}
	        RestTemplate restTemplate = new RestTemplate();
	        //result = restTemplate.getForObject(uri.getPath(),ShuwaApiResult.class,requestParam.toString());
	        result = restTemplate.getForObject("http://sign_recog:19999/signRecognition?"+requestParam.toString(),ShuwaApiResult.class);
		}else{
			result = new ShuwaApiResult(2,"2");
		}
		return result;
	}
	@PostMapping("finish/{param}")
	public ShuwaApiResult getAnswer(@PathVariable String param ){
		String requestParam = param.replaceAll(repalceName, imageFileDir);
        RestTemplate restTemplate = new RestTemplate();
        String urlPath = "http://sign_recog:19999/signRecognition?"+ requestParam;
        ShuwaApiResult result = restTemplate.getForObject(urlPath,ShuwaApiResult.class);
        return result;
	}
}
