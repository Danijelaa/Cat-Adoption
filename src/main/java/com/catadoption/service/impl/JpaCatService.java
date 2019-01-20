package com.catadoption.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.catadoption.model.Cat;
import com.catadoption.repository.CatRepository;
import com.catadoption.service.CatService;

@Service
public class JpaCatService implements CatService {

	@Autowired
	private CatRepository catRepository;
	
	
	@Override
	public Page<Cat> allCats(int page) {
		return catRepository.findByApprovedByAdmin(true, new PageRequest(page, 6));
	}

	@Override
	public Page<Cat> search(String sex, Long colorId, Long locationId,
			Long breedId, Long ageId, int page) {
		return catRepository.search(sex, colorId, locationId, breedId, ageId, new PageRequest(page, 6));
	}

	@Override
	public void addCat(Cat cat) {
		catRepository.save(cat);
	}

	@Override
	public void deleteCat(Long id) {
		/*Optional<Maca> maca=predragaPoId(id);
		if(!maca.isPresent()){
			throw new IllegalArgumentException("Cannot delete non-existent content.");
		}*/
		catRepository.deleteById(id);
	}

	@Override
	public Cat searchByIdForInfo(Long id) {
		return catRepository.findByIdAndApprovedByAdmin(id, true);
	}

	@Override
	public List<Cat> findByUser(String username) {
		return catRepository.findByUser(username);
	}

	@Override
	public Optional<Cat> searchById(Long id) {
		return catRepository.findById(id);
	}

	@Override
	public List<Cat> contentToBeApproved() {
		return catRepository.findByApprovedByAdmin(false);
	}

	@Override
	public void approveContent(Long id) {
		Optional<Cat> maca=catRepository.findById(id);
		if(!maca.isPresent()){
			throw new IllegalArgumentException("Cannot aprove non-existent content.");
		}
		maca.get().setApprovedByAdmin(true);
		catRepository.save(maca.get());
	}

	@Override
	public Boolean existsById(Long id) {
		return catRepository.existsById(id);
	}

	

}
