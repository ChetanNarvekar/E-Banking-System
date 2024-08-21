package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.dao.CustomerDao;
import com.app.entity.customer.Customer;
import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find the customer by email first
        Optional<Customer> customerOpt = customerDao.findByEmail(username);

        // If not found by email, try finding by phone number
        if (!customerOpt.isPresent()) {
            customerOpt = customerDao.findByPhoneNumber(username);
        }

        // If still not found, throw an exception
        Customer customer = customerOpt.orElseThrow(() -> 
                new UsernameNotFoundException("User not found with email or phone number: " + username));
        
        // Return UserDetails object populated with customer details and identifier
        return new CustomUserDetails(customer, username);
    }
}
