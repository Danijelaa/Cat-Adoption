package com.catadoption.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import com.catadoption.model.User;
import com.catadoption.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService  {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.catadoption.model.User korisnik=userRepository.findByUsername(username);
		if(korisnik==null){
			throw new UsernameNotFoundException("No user with given username.");
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(korisnik.getAuthority()));
		
		return new User(username, korisnik.getPassword(), grantedAuthorities);
		}

	}


