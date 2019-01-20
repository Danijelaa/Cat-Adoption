package com.catadoption.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catadoption.model.Color;
import com.catadoption.repository.ColorRepository;
import com.catadoption.service.ColorService;

@Service
public class JpaColorService implements ColorService {

	@Autowired
	private ColorRepository colorRepository;
	@Override
	public List<Color> allColors() {
		return colorRepository.findAll();
	}

	@Override
	public void addColor(Color color) {
		colorRepository.save(color);
	}

	@Override
	public Optional<Color> searchById(Long id) {
		return colorRepository.findById(id);
	}

}
