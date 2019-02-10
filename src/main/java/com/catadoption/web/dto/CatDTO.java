package com.catadoption.web.dto;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.catadoption.enums.CatSex;



public class CatDTO {

	private Long id;
	@NotBlank
	private String name;
	@Enumerated(EnumType.STRING)
	private CatSex sex;
	/*@NotBlank
	private String sex;*/
	@NotNull
	private Long colorId;
	private String colorTitle;
	@NotNull
	private Long locationId;
	private String locationCity;
	@NotNull
	private Long breedId;
	private String breedTitle;
	@NotNull
	private Long ageId;
	private String ageTitle;
	@NotBlank
	private String description;
	//@NotBlank
	private String userUsername;
	private Long userPhone;
	private byte[] image;
	private Boolean approvedByAdmin;
	
	
	public Boolean getApprovedByAdmin() {
		return approvedByAdmin;
	}
	public void setApprovedByAdmin(Boolean approvedByAdmin) {
		this.approvedByAdmin = approvedByAdmin;
	}
	public Long getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(Long userPhone) {
		this.userPhone = userPhone;
	}
	
	
	public String getUserUsername() {
		return userUsername;
	}
	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
/*	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}*/
	
	public Long getColorId() {
		return colorId;
	}
	public CatSex getSex() {
		return sex;
	}
	public void setSex(CatSex sex) {
		this.sex = sex;
	}
	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}
	public String getColorTitle() {
		return colorTitle;
	}
	public void setColorTitle(String colorTitle) {
		this.colorTitle = colorTitle;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public Long getBreedId() {
		return breedId;
	}
	public void setBreedId(Long breedId) {
		this.breedId = breedId;
	}
	public String getBreedTitle() {
		return breedTitle;
	}
	public void setBreedTitle(String breedTitle) {
		this.breedTitle = breedTitle;
	}
	public Long getAgeId() {
		return ageId;
	}
	public void setAgeId(Long ageId) {
		this.ageId = ageId;
	}
	public String getAgeTitle() {
		return ageTitle;
	}
	public void setAgeTitle(String ageTitle) {
		this.ageTitle = ageTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/*public MultipartFile getSlika() {
		return slika;
	}
	public void setSlika(MultipartFile slika) {
		this.slika = slika;
	}*/
	
	
}
