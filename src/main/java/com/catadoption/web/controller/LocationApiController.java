package com.catadoption.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.catadoption.model.Location;
import com.catadoption.service.LocationService;

@RestController
@RequestMapping(value="/api/locations")
public class LocationApiController {

	@Autowired
	private LocationService locationService;
	
/*	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<LocationDTO>> getColors(){
		List<Location> locations=locationService.allLocations();
		return new ResponseEntity<>(toLocationDto.convert(locations), HttpStatus.OK);
		
	}*/
	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<Location>> getColors(){
		List<Location> locations=locationService.allLocations();
		return new ResponseEntity<>(locations, HttpStatus.OK);
		
	}
}
