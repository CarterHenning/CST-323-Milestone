package com.gcu.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.data.UsersDataService;
import com.gcu.model.SignUpModel;

/**
 * The RegistrationService class provides methods for user registration.
 */
@Service
public class RegistrationService 
{
    @Autowired
    private UsersDataService service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    /**
     * Initializes a user.
     * 
     * @param user The SignUpModel object representing the user to be initialized.
     * @return true if the user is successfully initialized, false otherwise.
     */
    public int initializeUser(SignUpModel user)
    {
        logger.info("Entering initializeUser()");
        // create a list using the SignUp model and populate it with the users database table
        List<SignUpModel> users = service.findAll();
        
        for(SignUpModel signUp : users)
        {
            if(user.getUserName().equals(signUp.getUserName()))
            {
                // throw clear exception
                logger.info("Exiting initializeUser()");
                return -1;
            }
            else if (user.getEmail().equals(signUp.getEmail()))
            {
                logger.info("Exiting initializeUser()");
                return -2;
            }
        }
		
        
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

        // Set the encrypted password to the signUpModel
        user.setPassword(encodedPassword);
        
        service.create(user);
        // verify in database, eventually
        logger.info("Exiting initializeUser()");
        return 1;
    }
    
    /**
     * Initializes the RegistrationService.
     */
    public void init() {
        logger.info("Entering init()");
        System.out.println("in RS.init");
        logger.info("Exiting init()");
    }

    /**
     * Destroys the RegistrationService.
     */
    public void destroy() {
        logger.info("Entering destroy()");
        System.out.println("in RS.des");
        logger.info("Exiting destroy()");
    }
}
