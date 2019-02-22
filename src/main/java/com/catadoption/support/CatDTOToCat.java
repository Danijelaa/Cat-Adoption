package com.catadoption.support;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.catadoption.model.Location;
import com.catadoption.model.Cat;
import com.catadoption.model.Age;
import com.catadoption.service.ColorService;
import com.catadoption.service.UserService;
import com.catadoption.service.LocationService;
import com.catadoption.service.CatService;
import com.catadoption.service.BreedService;
import com.catadoption.service.AgeService;
import com.catadoption.web.dto.CatDTO;

@Component
public class CatDTOToCat/* implements Converter<CatDTO, Cat> */{

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
	
	//@Override
	public Cat convert(CatDTO catDto, String userUsername) throws IllegalArgumentException, SecurityException{
		Optional<Cat> catO=catService.searchById(catDto.getId());
		if(!catO.isPresent()){
			throw new IllegalArgumentException("Can not update non-existent cat.");
		}
		Cat cat=catO.get();
		if(!userUsername.equals(catO.get().getUser().getUsername())){
			throw new SecurityException();
		}

		cat.setName(catDto.getName());
		Optional<Location> location=locationService.searchById(catDto.getLocationId());
		if(!location.isPresent()){
			throw new IllegalArgumentException("Can not set non-existent location.");
		}
		cat.setLocation(location.get());
		cat.setDescription(catDto.getDescription());
		Optional<Age> age=ageService.searchById(catDto.getAgeId());
		if(!age.isPresent()){
			throw new IllegalArgumentException("Can not set non-existent age.");
		}
		cat.setAge(age.get());
		cat.setApprovedByAdmin(false);
		return cat;
	}

}
