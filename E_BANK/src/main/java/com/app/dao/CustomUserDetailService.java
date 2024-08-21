package com.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.entity.customer.Customer;

@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	private CustomerDao custDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Customer customer = custDao.findByEmail(username).orElseThrow(()-> new RuntimeException("User Not Found Exception"));
		return customer;
	}

}
