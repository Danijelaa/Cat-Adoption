package com.catadoption.web.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import com.catadoption.service.UserService;
import com.catadoption.service.CatService;
import com.catadoption.support.FormDataToCatDTO;
import com.catadoption.support.UserDTOToUser;
import com.catadoption.support.CatDTOToCat;
import com.catadoption.support.CatToCatDTO;
import com.catadoption.web.dto.CatDTO;
import com.catadoption.web.dto.UserDTOLogin;
import com.catadoption.web.dto.UserDTORegister;

@RestController
@RequestMapping(value="/api/users")
public class UserApiController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDTOToUser toUser;
	@Autowired
	private CatService catService;
	@Autowired
	private CatToCatDTO toCatDto;
	@Autowired
	private CatDTOToCat toCat;
	@Autowired
	private FormDataToCatDTO formDataToCatDto;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetalsService;
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	ResponseEntity<?> login(HttpServletRequest request, @Validated @RequestBody UserDTOLogin userDtoLogin){
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDtoLogin.getUsername(), userDtoLogin.getPassword());
			Authentication authentication=authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			try {
				userDetalsService.loadUserByUsername(userDtoLogin.getUsername());
			} catch (UsernameNotFoundException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("No user with given name and password.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/logout")
	ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/register")
	ResponseEntity<?> registration(@Validated @RequestBody UserDTORegister userDtoRegister){
		if(userService.existsByUsername(userDtoRegister.getUsername())){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		User korisnik=toUser.convert(userDtoRegister);
		userService.addUser(korisnik);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(method=RequestMethod.GET, value="/cats")
	ResponseEntity<List<CatDTO>> usersCats(HttpServletRequest request){
		Principal principal=request.getUserPrincipal();
		User user=userService.findByUsername(principal.getName());
		//HttpHeaders hrs=new HttpHeaders();
		//hrs.add("korisnik", user.getUsername());
		List<Cat> mace=catService.findByUser(user.getUsername());
		return new ResponseEntity<List<CatDTO>>(toCatDto.convert(mace), /*hrs, */HttpStatus.OK);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(method=RequestMethod.GET, value="/cats/{id}/update")
	ResponseEntity<CatDTO> getByIdForUpdate(HttpServletRequest request, @PathVariable Long id){
		Optional<Cat> catO=catService.searchById(id);
		if(!catO.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Principal principal=request.getUserPrincipal();
		String username=principal.getName();
		Cat cat=catO.get();
		if(!cat.getUser().getUsername().equals(username)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(toCatDto.convert(cat), HttpStatus.OK);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(method=RequestMethod.PUT, value="/cats/{id}")
	ResponseEntity<?> updateCat(HttpServletRequest request, @PathVariable Long id, @Validated @RequestBody CatDTO catDtoUpdate){
		if(!id.equals(catDtoUpdate.getId())){
			return new ResponseEntity<String>("Ids do not match.", HttpStatus.BAD_REQUEST);
		}
		
		Principal principal=request.getUserPrincipal();
		String username=principal.getName();
		Optional<Cat> catO=catService.searchById(id);
		if(!catO.isPresent()){
			return new ResponseEntity<String>("Can not update non-existent cat.", HttpStatus.BAD_REQUEST);
		}
		if(!username.equals(catO.get().getUser().getUsername())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		Cat cat=null;
		try {
			cat=toCat.convert(catDtoUpdate);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catService.addCat(cat);
		return new ResponseEntity<>(toCatDto.convert(cat), HttpStatus.OK);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(method=RequestMethod.POST, value="/cats")
	ResponseEntity<?> postCat(HttpServletRequest request, @RequestParam(required=true) String newCat, @RequestPart(required=true) MultipartFile image){
		if(image.isEmpty() || !image.getContentType().startsWith("image")){
			return new ResponseEntity<String>("Image content required.", HttpStatus.BAD_REQUEST);
		}
		CatDTO catDto=formDataToCatDto.convert(newCat);
		if(catDto==null){
			return new ResponseEntity<String>("Some parameters might be missing.", HttpStatus.BAD_REQUEST);
		}
		Cat cat=null;
		try {
			cat=toCat.convert(catDto);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if(!formDataToCatDto.setImage(cat, image)){
			return new ResponseEntity<String>("Error happened while adding image.", HttpStatus.BAD_REQUEST);
		}
		
		Principal principal=request.getUserPrincipal();
		String username=principal.getName();
		User user=userService.findByUsername(username);
		cat.setUser(user);
		
		catService.addCat(cat);
		return new ResponseEntity<>(toCatDto.convert(cat), HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(method=RequestMethod.DELETE, value="/cats/{id}")
	ResponseEntity<?> deleteCat(HttpServletRequest request, @PathVariable Long id){
		Optional<Cat> cat=catService.searchById(id);
		if(!cat.isPresent()){
			return new ResponseEntity<String>("Can not delete non-existent cat.", HttpStatus.BAD_REQUEST);
		}
		Principal principal=request.getUserPrincipal();
		String username=principal.getName();
		if(!(username.equals("admin") || cat.get().getUser().getUsername().equals(username))){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catService.deleteCat(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT, value="/cats/{id}/approve")
	ResponseEntity<?> approveContent(@PathVariable Long id){
		try {
			catService.approveContent(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.GET, value="/cats/unapproved-content")
	ResponseEntity<List<CatDTO>> getUnapprovedContent(){
		List<Cat> cats=catService.contentToBeApproved();
		return new ResponseEntity<List<CatDTO>>(toCatDto.convert(cats), HttpStatus.OK);
	}
	
}
