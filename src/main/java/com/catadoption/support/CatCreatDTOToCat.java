package com.catadoption.support;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.catadoption.model.Age;
import com.catadoption.model.Breed;
import com.catadoption.model.Cat;
import com.catadoption.model.Color;
import com.catadoption.model.Location;
import com.catadoption.service.AgeService;
import com.catadoption.service.BreedService;
import com.catadoption.service.CatService;
import com.catadoption.service.ColorService;
import com.catadoption.service.LocationService;
import com.catadoption.service.UserService;
import com.catadoption.web.dto.CatCreateDTO;
@Component
public class CatCreatDTOToCat implements Converter<CatCreateDTO, Cat> {

	@Autowired
	private CatService catService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private BreedService breedService;
	@Autowired
	private AgeService ageService;
	@Autowired
	private UserService userService;
	
	@Override
	public Cat convert(CatCreateDTO newCat) {
		Cat cat=new Cat();
		Optional<Color> color=colorService.searchById(newCat.getColorId());
		if(!color.isPresent()){
			throw new IllegalArgumentException("Can not set non-existent color.");
		}
		cat.setColor(color.get());
		cat.setSex(newCat.getSex());
		Optional<Breed> breed=breedService.searchById(newCat.getBreedId());
		if(!breed.isPresent()){
			throw new IllegalArgumentException("Can not set non-existent bried.");
		}
		cat.setBreed(breed.get());
		cat.setName(newCat.getName());
		Optional<Location> location=locationService.searchById(newCat.getLocationId());
		if(!location.isPresent()){
			throw new IllegalArgumentException("Can not set non-existent location.");
		}
		cat.setLocation(location.get());
		cat.setDescription(newCat.getDescription());
		Optional<Age> age=ageService.searchById(newCat.getAgeId());
		if(!age.isPresent()){
			throw new IllegalArgumentException("Can not set non-existent age.");
		}
		cat.setAge(age.get());
		cat.setApprovedByAdmin(false);
		cat.setImage(Base64.getDecoder().decode(newCat.getImageBase64()));
		return cat;
	}

}
