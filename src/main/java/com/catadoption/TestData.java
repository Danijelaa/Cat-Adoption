package com.catadoption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.catadoption.model.Color;
import com.catadoption.model.User;
import com.catadoption.model.Location;
import com.catadoption.model.Cat;
import com.catadoption.model.Breed;
import com.catadoption.model.Age;
import com.catadoption.service.ColorService;
import com.catadoption.service.UserService;
import com.catadoption.service.LocationService;
import com.catadoption.service.CatService;
import com.catadoption.service.BreedService;
import com.catadoption.service.AgeService;

@Component
public class TestData {

	@Autowired
	private CatService catService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private BreedService breedService;
	@Autowired
	private AgeService ageService;
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void init(){
		
		List<Color> colors=new ArrayList<Color>();
		for(int i=1; i<=5; i++){
			Color color=new Color();
			color.setTitle("color"+i);
			colorService.addColor(color);
			colors.add(color);
		}
		
		List<Location> locations=new ArrayList<>();
		for(int i=1; i<=10; i++){
			Location location=new Location();
			location.setCity("city"+i);
			locationService.addLocation(location);
			locations.add(location);
		}
		List<Breed> breeds=new ArrayList<>();
		for(int i=1; i<=3; i++){
			Breed breed=new Breed();
			breed.setTitle("breed"+i);
			breedService.addBreed(breed);
			breeds.add(breed);
		}
		List<Age> ages=new ArrayList<>();
		for(int i=1; i<=4; i++){
			Age age=new Age();
			age.setTitle("age"+i);
			ageService.addAge(age);
			ages.add(age);
		}
		
		User admin=new User();
		admin.setUsername("admin");
		admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
		admin.setPhone(Long.parseLong("0641111111"));
		admin.setAuthority("ROLE_ADMIN");
		userService.addUser(admin);
		
		List<User> users=new ArrayList<>();
		for(int i=1; i<=3; i++){
			User user=new User();
			user.setPhone(Long.parseLong("065111111"+i));
			user.setUsername("user"+i);
			user.setPassword(new BCryptPasswordEncoder().encode("password"+i));
			user.setAuthority("ROLE_USER");
			userService.addUser(user);
			users.add(user);
		}
		
		String pol;
		for(int j=1; j<=12; j++){
			Cat cat=new Cat();
			cat.setColor(colors.get((int) Math.floor(Math.random()*5)));
			cat.setLocation(locations.get((int) Math.floor(Math.random()*10)));
			cat.setBreed(breeds.get((int) Math.floor(Math.random()*3)));
			cat.setAge(ages.get((int) Math.floor(Math.random()*4)));
			cat.setName("Cat"+j);
			cat.setDescription(j+"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Fusce dui leo, imperdiet in, aliquam sit amet, feugiat eu, orci.");
			cat.setUser(users.get((int) Math.floor(Math.random()*3)));
			int n=(int) Math.round(Math.random()+1);
			if(n==1){
				pol="male";
			}
			else {
				pol="female";
			}
			cat.setSex(pol);
			File slika=new File("./src/main/resources/static/pictures_of_cats/unnamed ("+j+").jpg");
			byte[] slikaData = new byte[(int) slika.length()];
			try {
				FileInputStream fis = new FileInputStream(slika);
				fis.read(slikaData);
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cat.setImage(slikaData);
			cat.setApprovedByAdmin(true);
			catService.addCat(cat);
		}
		
		
		
	}
	
}
