package com.catadoption.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.catadoption.enums.CatSex;

@Entity
public class Cat {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column(nullable=false)
	private String name;
	/*@Column(nullable=false)
	private String sex;*/
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private CatSex sex;
	@ManyToOne
	private Color color;
	@ManyToOne
	private Location location;
	@ManyToOne
	private Breed breed;
	@ManyToOne
	private Age age;
	@Column(nullable=false)
	private String description;
	@Lob
	@Column(nullable=false)
	private byte[] image;
	@ManyToOne
	private User user;
	@Column
	private Boolean approvedByAdmin;
	
	public Boolean getApprovedByAdmin() {
		return approvedByAdmin;
	}
	public void setApprovedByAdmin(Boolean approvedByAdmin) {
		this.approvedByAdmin = approvedByAdmin;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
		if(user!=null && !user.getAddedCats().contains(this)){
			user.getAddedCats().add(this);
		}
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
	
	public Color getColor() {
		return color;
	}
	public CatSex getSex() {
		return sex;
	}
	public void setSex(CatSex sex) {
		this.sex = sex;
	}
	public void setColor(Color color) {
		this.color = color;
		if(color!=null && !color.getCats().contains(this)){
			color.getCats().add(this);
		}
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
		if(location!=null && !location.getCats().contains(this)){
			location.getCats().add(this);
		}
	}
	public Breed getBreed() {
		return breed;
	}
	public void setBreed(Breed breed) {
		this.breed = breed;
		if(breed!=null && !breed.getCats().contains(this)){
			breed.getCats().add(this);
		}
	}
	public Age getAge() {
		return age;
	}
	public void setAge(Age age) {
		this.age = age;
		if(age!=null && !age.getCats().contains(this)){
			age.getCats().add(this);
		}
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
}
