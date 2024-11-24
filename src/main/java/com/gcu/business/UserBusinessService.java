package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcu.data.UsersDataService;
import com.gcu.data.entity.UserEntity;


/**
 * Service class for loading user details during authentication.
 */
@Service
public class UserBusinessService implements UserDetailsService {

	
	/**
	 * Instantiates a UserDataService
	 */
    private final UsersDataService service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs a new UserBusinessService with the provided UsersDataService.
     *
     * @param service the data service for retrieving user details
     */
    @Autowired
    public UserBusinessService(UsersDataService service) {
        this.service = service;
    }

    /**
     * Loads user details by the given userName.
     * <p>
     * This method is called by the authentication provider to retrieve user details
     * for authentication.
     *
     * @param userName the userName to load user details for
     * @return a {@link UserDetails} object representing the loaded user
     * @throws UsernameNotFoundException if the user with the given userName is not found
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("Entering loadUserByUsername()");
        UserEntity user = service.findByUsername(userName);
        
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            // Add any necessary roles or authorities based on your application logic
            authorities.add(new SimpleGrantedAuthority("USER"));
            logger.info("Exiting loadUserByUsername()");
            return new User(user.getUsername(), user.getPassword(), authorities);
        } else {
            logger.info("Exiting loadUserByUsername()");
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
    }
}

