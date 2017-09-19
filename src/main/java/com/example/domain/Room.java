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
	String roomName;
	@Id //主キー
	String serialId;
}
