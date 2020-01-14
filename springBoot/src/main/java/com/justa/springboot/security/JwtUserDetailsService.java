package com.justa.springboot.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {
	
	public UserDetails loadUserByUsername(String userName) {
		
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority("UserRole");
		List<GrantedAuthority> auths = new ArrayList<>();
		auths.add(auth);
		return new User(userName, "pp", auths); // TODO implement 
	}
	

}
