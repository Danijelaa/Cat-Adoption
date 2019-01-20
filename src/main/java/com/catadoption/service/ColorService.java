package com.catadoption.service;

import java.util.List;
import java.util.Optional;

import com.catadoption.model.Color;

public interface ColorService {

	List<Color> allColors();
	void addColor(Color color);
	Optional<Color> searchById(Long id);
}
