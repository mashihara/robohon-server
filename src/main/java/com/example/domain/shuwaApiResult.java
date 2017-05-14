package com.example.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //(3)JPAの仕様で引数なしのコンストラクタをつける必要がある
@AllArgsConstructor //これはまー、便利なので
public class shuwaApiResult {
	@NotNull
	@Size(min=1,max=100)
	private Integer label;
	private Integer prob;
}
