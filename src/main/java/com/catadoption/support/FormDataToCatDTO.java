package com.catadoption.support;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.catadoption.model.Cat;
import com.catadoption.web.dto.CatDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FormDataToCatDTO {
	
	public CatDTO convert(String jsonCat){
		CatDTO catDto=null;
		ObjectMapper om=new ObjectMapper();
		try {
			catDto = om.readValue(jsonCat, CatDTO.class);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		if(catDto.getColorId()==null || catDto.getLocationId()==null || catDto.getSex().equals("") 
				|| catDto.getAgeId()==null || catDto.getBreedId()==null || catDto.getDescription().equals(null)){
			return null;
		}
		return catDto;
	}
	
	public boolean setImage(Cat cat, MultipartFile image){
		if(image.isEmpty()){
			return false;
		}
		try {
			cat.setImage(image.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
