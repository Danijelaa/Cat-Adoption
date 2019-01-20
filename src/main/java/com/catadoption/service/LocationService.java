package com.catadoption.service;

import java.util.List;
import java.util.Optional;

import com.catadoption.model.Location;

public interface LocationService {

	List<Location> allLocations();
	void addLocation(Location location);
	Optional<Location> searchById(Long id);
}
