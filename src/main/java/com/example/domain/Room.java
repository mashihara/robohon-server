package com.example.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //jpaのエンティティ
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
	@Id //主キー
	@GeneratedValue(strategy=GenerationType.IDENTITY) //postgersの場合、GeneratedValueにこれがいる
	Integer roomId;
	String roomName;
	String serialId;
	Room(String roomName,String serialId){
		this.roomName=roomName;
		this.serialId=serialId;
	}
}
