package com.transbank.restaurante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transbank.restaurante.model.RestaurantUserDetails;
import com.transbank.restaurante.model.User;
import com.transbank.restaurante.repository.UserRepository;

@Service
public class RestaurantUserDetailsService implements UserDetailsService{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found " + username));
		
		return new RestaurantUserDetails(user.get(), passwordEncoder.encode(user.get().getPassword()));
	}

}
