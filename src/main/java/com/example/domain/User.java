package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	String userId;
	String password;
	String serialId;
	
	@Override
	public String toString(){
		return userId + password + serialId;
	}
}
