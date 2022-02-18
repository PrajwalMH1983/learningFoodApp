package com.learning.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EpisodeRequest {
	
	@NotBlank
	private String id;
	@NotBlank
	private String name;
	@NotNull
	private float length;
	@NotBlank
	private String location;
	private String trailer;
	@NotBlank
	private String seriesId;

}
