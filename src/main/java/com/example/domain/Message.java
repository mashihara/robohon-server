package com.example.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private boolean syuwaFlg;
	private String roomName;
    private String message;
    private String serverTime;
    public Message(boolean syuwaFlg,String roomName,String message){
    	this.syuwaFlg=syuwaFlg;
    	this.roomName=roomName;
    	this.message=message;
    }
}