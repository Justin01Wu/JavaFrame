package com.justa.springboot.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {
	
	public UserDetails loadUserByUsername(String userName) {
		return null; // TODO implement 
	}
	

}
