package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.DAO.UserRepository;
import com.smart.entities.User;

public class UserDetailServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=repo.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		
		CustomUserDetails custom=new CustomUserDetails(user);
		
		
		return custom;
	}
	
	
	
}
