package com.example.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.ShuwaApiResult;

@RestController //(1)Restのエンドポイントとなる
@RequestMapping("/") //(2)パスのルートを記載
public class RobohonShuwaRestController {
	
	@PostMapping //@RequestBodyとしてバイナリデータを受け取る
	public ShuwaApiResult getShuwaApiResult(){
        //RestTemplate restTemplate = new RestTemplate();
        //shuwaApiResult result = restTemplate.getForObject("http://localhost:1598/api/customers/test", shuwaApiResult.class);
		ShuwaApiResult result = new ShuwaApiResult(1,2);
		return result;
	}
}
