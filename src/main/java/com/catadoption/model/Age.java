package com.catadoption.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Age {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String title;
	@OneToMany(mappedBy="age")
	@JsonIgnore
	private List<Cat> cats=new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Cat> getCats() {
		return cats;
	}
	public void setCats(List<Cat> cats) {
		this.cats = cats;
	}
	
}
