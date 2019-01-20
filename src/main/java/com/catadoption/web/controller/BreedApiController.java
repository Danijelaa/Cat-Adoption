package com.catadoption.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.catadoption.model.Breed;
import com.catadoption.service.BreedService;

@RestController
@RequestMapping(value="/api/breeds")
public class BreedApiController {

	@Autowired
	private BreedService breedService;
	
	
/*	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<BreedDTO>> getAllBreeds(){
		List<Breed> breeds=breedService.allBreeds();
		return new ResponseEntity<>(toBreedDto.convert(breeds), HttpStatus.OK);
		
	}*/
	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<Breed>> getAllBreeds(){
		List<Breed> breeds=breedService.allBreeds();
		return new ResponseEntity<>(breeds, HttpStatus.OK);
		
	}
}
