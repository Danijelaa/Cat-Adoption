package com.catadoption.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.catadoption.model.User;
import com.catadoption.model.Cat;
import com.catadoption.service.ColorService;
import com.catadoption.service.LocationService;
import com.catadoption.service.CatService;
import com.catadoption.service.BreedService;
import com.catadoption.service.AgeService;
import com.catadoption.support.FormDataToCatDTO;
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
	@Autowired
	private FormDataToCatDTO formDataToCatDto;
	
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
	
	/*@RequestMapping(method=RequestMethod.GET, value="/{id}/izmjena")
	ResponseEntity<MacaDTO> pretragaPoIdZaIzmjenu(HttpSession sesija, @PathVariable Long id){
		Korisnik korisnik=(Korisnik)sesija.getAttribute("korisnik");
		if(korisnik==null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}		
		Maca maca=macaService.predragaPoId(id).get();
		if(maca==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(!maca.getKorisnik().getId().equals(korisnik.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(toMacaDto.convert(maca), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	ResponseEntity<MacaDTO> izbrisi(HttpSession sesija, @PathVariable Long id){
		Korisnik korisnik=(Korisnik)sesija.getAttribute("korisnik");
		if(korisnik==null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Maca maca=macaService.predragaPoId(id).get();
		if(maca==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!(korisnik.getKorisnickoIme().equals("admin") || maca.getKorisnik().getId().equals(korisnik.getId()))){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		macaService.izbrisiMacu(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	*/
	
	/*@RequestMapping(method=RequestMethod.POST)
	ResponseEntity<Object> dodajMacu(@RequestParam String ime, @RequestParam String opis, @RequestParam Long lokacijaId, @RequestParam Long rasaId, @RequestParam String pol, @RequestParam Long starostId, @RequestParam Long bojaId, @RequestParam MultipartFile slika){
		//System.out.println(dodataSlika.getOriginalFilename());
		Maca maca=new Maca();
		maca.setBoja(bojaService.pretragaPoid(bojaId));
		maca.setIme(ime);
		maca.setLokacija(lokacijaService.pretragaPoId(lokacijaId));
		maca.setOpis(opis);
		maca.setPol(pol);
		maca.setRasa(rasaservice.pretragaPoId(rasaId));
		try {
			maca.setSlika(slika.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maca.setStarost(starostService.pretragaPoId(starostId));
		macaService.unesiNovuMacu(maca);
		HttpHeaders hrs=new HttpHeaders();
		try {
			hrs.setLocation(new URI("/"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(hrs, HttpStatus.SEE_OTHER);
	}*/
	
	/*@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	ResponseEntity<MacaDTO> izmijeniMacu(HttpSession sesija, @PathVariable Long id, @Validated @RequestBody MacaDTO zaIzmjenu){
		Korisnik korisnik=(Korisnik)sesija.getAttribute("korisnik");
		if(korisnik==null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(!korisnik.getKorisnickoIme().equals(zaIzmjenu.getKorisnikKorisnickoIme())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(!id.equals(zaIzmjenu.getId())){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Maca maca=toMaca.convert(zaIzmjenu);
		macaService.unesiNovuMacu(maca);
		return new ResponseEntity<>(toMacaDto.convert(maca), HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	ResponseEntity<MacaDTO> dodajmacu(HttpSession sesija, @RequestParam(required=true) String novaMaca, @RequestPart(required=true) MultipartFile slika){
		Korisnik korisnik=(Korisnik)sesija.getAttribute("korisnik");
		if(korisnik==null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(slika.isEmpty() || !slika.getContentType().startsWith("image")){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		MacaDTO macaDto=formDataToMacaDto.convert(novaMaca);
		if(macaDto==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		macaDto.setKorisnikKorisnickoIme(korisnik.getKorisnickoIme());
		Maca maca=toMaca.convert(macaDto);
		if(!formDataToMacaDto.setImage(maca, slika)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		macaService.unesiNovuMacu(maca);
		return new ResponseEntity<>(toMacaDto.convert(maca), HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}/odobri")
	ResponseEntity<?> odobriSadrzaj(HttpSession sesija, @PathVariable Long id){
		Korisnik korisnik=(Korisnik)sesija.getAttribute("korisnik");
		if(korisnik==null || !korisnik.getKorisnickoIme().equals("admin")){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		macaService.odobriSadrzaj(id);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}*/
	
	
}
