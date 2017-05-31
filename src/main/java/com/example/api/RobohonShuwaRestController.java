package com.example.api;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Autowired	
	private ImageService imageService;

	@Value("http://sign_recog:19999/signRecognition?{parameter}")
	URI uri;
	
	@PostMapping //@RequestBodyとしてバイナリデータを受け取る
	public ShuwaApiResult getShuwaApiResult(@RequestBody() MultipartFile file,Model model){
		if(file!=null){
			imageService.saveImage(file);
		}
        //RestTemplate restTemplate = new RestTemplate();
        //ShuwaApiResult result = restTemplate.getForObject(uri.getPath(),ShuwaApiResult.class,"");
		ShuwaApiResult result = new ShuwaApiResult(1,2);
		return result;
	}
	
}
