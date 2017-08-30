package com.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {
	private boolean errorFlg=false; //true:エラー、false：正常
	private String errorCode; //errorFlgがtrueの場合のエラーの内容。
	private String key;
    private String serverTime;
	public LoginResult(String key){
		this.key=key;
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS");
    	this.serverTime=LocalDateTime.now().format(dtf);
	}
}
