package com.catadoption.service;

import com.catadoption.model.User;

public interface UserService {

	void addUser(User user);
	User findByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
	Boolean existsByUsername(String username);
}
