package com.catadoption.support;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.catadoption.model.Color;
import com.catadoption.model.Location;
import com.catadoption.model.Cat;
import com.catadoption.model.Breed;
import com.catadoption.model.Age;
import com.catadoption.service.ColorService;
import com.catadoption.service.UserService;
import com.catadoption.service.LocationService;
import com.catadoption.service.CatService;
import com.catadoption.service.BreedService;
import com.catadoption.service.AgeService;
import com.catadoption.web.dto.CatDTO;

@Component
public class CatDTOToCat implements Converter<CatDTO, Cat> {

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
	public Cat convert(CatDTO catDto) throws IllegalArgumentException{
		Cat cat;
		if(catDto.getId()==null){
			cat=new Cat();
			Optional<Color> color=colorService.searchById(catDto.getColorId());
			if(!color.isPresent()){
				throw new IllegalArgumentException("Can not set non-existent color.");
			}
			cat.setColor(color.get());
			cat.setSex(catDto.getSex());
			Optional<Breed> breed=breedService.searchById(catDto.getBreedId());
			if(!breed.isPresent()){
				throw new IllegalArgumentException("Can not set non-existent bried.");
			}
			cat.setBreed(breed.get());
			//maca.setKorisnik(korisnikService.nadjiPoKorisnickomImenu(macaDto.getKorisnikKorisnickoIme()));
			
		}
		else{
			Optional<Cat> catO=catService.searchById(catDto.getId());
			if(!catO.isPresent()){
				throw new IllegalArgumentException("Can not update non-existent cat.");
			}
			cat=catO.get();
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
