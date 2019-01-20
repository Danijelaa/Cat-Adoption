package com.catadoption.support;


import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.catadoption.model.Cat;
import com.catadoption.web.dto.CatDTO;

@Component
public class CatToCatDTO implements Converter<Cat, CatDTO>{

	@Override
	public CatDTO convert(Cat cat) {
		CatDTO catDto=new CatDTO();
		catDto.setColorId(cat.getColor().getId());
		catDto.setColorTitle(cat.getColor().getTitle());
		catDto.setId(cat.getId());
		catDto.setName(cat.getName());
		catDto.setLocationCity(cat.getLocation().getCity());
		catDto.setLocationId(cat.getLocation().getId());
		catDto.setDescription(cat.getDescription());
		catDto.setSex(cat.getSex());
		catDto.setBreedId(cat.getBreed().getId());
		catDto.setBreedTitle(cat.getBreed().getTitle());
		catDto.setAgeId(cat.getAge().getId());
		catDto.setAgeTitle(cat.getAge().getTitle());
		catDto.setUserUsername(cat.getUser().getUsername());
		catDto.setImage(cat.getImage());
		catDto.setUserPhone(cat.getUser().getPhone());
		catDto.setApprovedByAdmin(cat.getApprovedByAdmin());
		return catDto;
	}

	public List<CatDTO> convert(List<Cat> cats){
		List<CatDTO> catsDto=new ArrayList<>();
		for(Cat c:cats){
			catsDto.add(convert(c));
		}
		return catsDto;
	}
	
}
