package com.catadoption.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.catadoption.model.Color;
import com.catadoption.service.ColorService;

@RestController
@RequestMapping(value="/api/colors")
public class ColorApiController {

	@Autowired
	private ColorService colorService;
	
/*	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<ColorDTO>> getColors(){
		List<Color> colors=colorService.allColors();
		return new ResponseEntity<>(toColorDto.convert(colors), HttpStatus.OK);
		
	}*/
	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<List<Color>> getColors(){
		List<Color> colors=colorService.allColors();
		return new ResponseEntity<>(colors, HttpStatus.OK);
		
	}
}
