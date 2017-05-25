package com.example.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShuwaApiRequest {
	@NotNull
	private Integer img1;
	private Integer img2;
	private Integer img3;
	private Integer img4;
	private Integer img5;
	private Integer img6;
	private Integer img7;
	private Integer img8;
	private Integer img9;
	private Integer img10;
}
