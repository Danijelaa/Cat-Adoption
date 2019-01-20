package com.catadoption.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catadoption.model.Location;
import com.catadoption.repository.LocationRepository;
import com.catadoption.service.LocationService;

@Service
public class JpaLocationService implements LocationService{

	@Autowired
	private LocationRepository locationRepository;
	@Override
	public List<Location> allLocations() {
		return locationRepository.findAll();
	}

	@Override
	public void addLocation(Location location) {
		locationRepository.save(location);
	}

	@Override
	public Optional<Location> searchById(Long id) {
		return locationRepository.findById(id);
	}

	
}
