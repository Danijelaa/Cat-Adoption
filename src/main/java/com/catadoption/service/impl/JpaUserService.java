package com.catadoption.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catadoption.model.User;
import com.catadoption.repository.UserRepository;
import com.catadoption.service.UserService;

@Service
public class JpaUserService implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByUsernameAndPassword(String username,
			String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	
}
