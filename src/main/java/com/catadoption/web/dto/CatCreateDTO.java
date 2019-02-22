package com.catadoption.web.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.catadoption.enums.CatSex;

public class CatCreateDTO {

	@NotBlank
	private String name;
	@Enumerated(EnumType.STRING)
	@NotNull
	private CatSex sex;
	@NotNull(message="Id of cat's color must not be null.")
	private Long colorId;
	@NotNull(message="Id of cat's location must not be null.")
	private Long locationId;
	@NotNull(message="Id of cat's breed must not be null.")
	private Long breedId;
	@NotNull(message="Id of cat's age must not be null.")
	private Long ageId;
	@NotBlank
	private String description;
	@NotBlank(message="Cat's image must not be null and must be in base64 format.")
	private String imageBase64;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CatSex getSex() {
		return sex;
	}
	public void setSex(CatSex sex) {
		this.sex = sex;
	}
	public Long getColorId() {
		return colorId;
	}
	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getBreedId() {
		return breedId;
	}
	public void setBreedId(Long breedId) {
		this.breedId = breedId;
	}
	public Long getAgeId() {
		return ageId;
	}
	public void setAgeId(Long ageId) {
		this.ageId = ageId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
	
}
