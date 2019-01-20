package com.catadoption.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.catadoption.model.User;
import com.catadoption.service.UserService;
import com.catadoption.web.dto.UserDTOLogin;
import com.catadoption.web.dto.UserDTORegister;

@Component
public class UserDTOToUser implements Converter<UserDTORegister, User> {

	@Autowired
	private UserService userService;
	@Override
	public User convert(UserDTORegister userDto) {
		User user=new User();
		user.setPhone(userDto.getPhone());;
		user.setUsername(userDto.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
		user.setAuthority("ROLE_USER");
		return user;
	}

}
