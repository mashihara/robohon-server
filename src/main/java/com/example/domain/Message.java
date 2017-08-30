package com.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
	private int messageType; //0:通常メッセージ、1：開始、2：終了
	private boolean errorFlg=false; //true:エラー、false：正常
	private String errorCode; //errorFlgがtrueの場合のエラーの内容。
	//1000:すでに他の端末でルーム名が登録されています
	//1001:ロボホンで、ルーム名が登録されていません。先にロボホン側の設定を行ってください。
    private boolean syuwaFlg;
	private String key;
    private String message;
    private String serverTime;
    public Message(StartEndRequest startEndRequest){
    	if(startEndRequest.getStartEnd().equals("start")){
    		this.messageType=1;
    	}else if(startEndRequest.getStartEnd().equals("end")){
    		this.messageType=2;
    	}else{
    		this.errorFlg=true;
    	}
    	this.key=startEndRequest.getKey();
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS");
    	this.serverTime=LocalDateTime.now().format(dtf);
    }
}