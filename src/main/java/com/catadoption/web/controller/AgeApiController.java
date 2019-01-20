package com.catadoption.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.catadoption.model.Age;
import com.catadoption.service.AgeService;

@RestController
@RequestMapping(value="/api/ages")
public class AgeApiController {

	@Autowired
	private AgeService ageService;
	
/*	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<AgeDTO>> getAllAges(){
		List<Age> ages=ageService.allAges();
		return new ResponseEntity<>(toAgeDto.convert(ages), HttpStatus.OK);
		
	}*/
	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<Age>> getAllAges(){
		List<Age> ages=ageService.allAges();
		return new ResponseEntity<>(ages, HttpStatus.OK);
		
	}
}
