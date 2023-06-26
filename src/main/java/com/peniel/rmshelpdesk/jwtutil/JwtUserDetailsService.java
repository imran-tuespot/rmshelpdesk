package com.peniel.rmshelpdesk.jwtutil;

	import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.peniel.rmshelpdesk.entity.Roles;
import com.peniel.rmshelpdesk.repository.UserRepository;
import com.peniel.rmshelpdesk.service.UserService;

	@Service
	public class JwtUserDetailsService implements UserDetailsService {
		@Autowired
		UserRepository userRepository;
		@Autowired
		UserService userservice;
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			System.out.println("===before=========");
			com.peniel.rmshelpdesk.entity.User user1=	validateUserName(username);
			System.out.println("===before========="+user1.getSTATUS().equalsIgnoreCase("D"));
		System.out.println(user1.toString()+"============");	
			if (null!=user1 && user1.getUSERNAME().equalsIgnoreCase(username)) {
				return new User(user1.getUSERNAME(),user1.getPASSWORD(),		 
						new ArrayList<>());

			} else {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
			
			
			
		}

		public com.peniel.rmshelpdesk.entity.User validateUserName(String username) {
			return	userRepository.getUser(username.toUpperCase());
			
		}
	}

