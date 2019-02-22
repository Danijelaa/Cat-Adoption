package com.catadoption.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catadoption.model.Cat;
import com.catadoption.service.ColorService;
import com.catadoption.service.LocationService;
import com.catadoption.service.CatService;
import com.catadoption.service.BreedService;
import com.catadoption.service.AgeService;
import com.catadoption.support.CatDTOToCat;
import com.catadoption.support.CatToCatDTO;
import com.catadoption.web.dto.CatDTO;

@RestController
@RequestMapping(value="/api/cats")
public class CatApiController {

	@Autowired
	private CatService catService;
	@Autowired
	private CatToCatDTO toCatDto;
	@Autowired
	private CatDTOToCat toCat;
	@Autowired
	private LocationService locationService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private BreedService breedService;
	@Autowired
	private AgeService ageService;
	
	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<CatDTO>> getAllCats(@RequestParam(required=false) String sex, @RequestParam(required=false) Long colorId, @RequestParam(required=false) Long locationId, @RequestParam(required=false) Long breedId, @RequestParam(required=false) Long ageId, @RequestParam(defaultValue="0") int page){
		Page<Cat> catsPage;
		if(sex!=null || colorId!=null || locationId!=null || breedId!=null || ageId!=null){
			catsPage=catService.search(sex, colorId, locationId, breedId, ageId, page);
		}
		else{
			catsPage=catService.allCats(page);
		}
		HttpHeaders hrs=new HttpHeaders();
		hrs.add("totalPages", Integer.toString(catsPage.getTotalPages()));
		List<Cat> cats=catsPage.getContent();
		return new ResponseEntity<>(toCatDto.convert(cats),hrs, HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}/info")
	ResponseEntity<?> getByIdForInfo(@PathVariable Long id){
		Cat cat=catService.searchByIdForInfo(id);
		if(cat==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(toCatDto.convert(cat), HttpStatus.OK);
	}
	
	
}
