package com.example.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private boolean syuwaFlg;
	private String serialId;
    private String message;
    private String serverTime;
}