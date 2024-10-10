package com.gcu.business;

import java.util.List;

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

    
    /**
     * Initializes a user.
     * 
     * @param user The SignUpModel object representing the user to be initialized.
     * @return true if the user is successfully initialized, false otherwise.
     */
    public int initializeUser(SignUpModel user)
    {
        // create a list using the SignUp model and populate it with the users database table
        List<SignUpModel> users = service.findAll();

        for(SignUpModel signUp : users)
        {
            if(user.getUserName().equals(signUp.getUserName()))
            {
                // throw clear exception
                return -1;
            }
            else if (user.getEmail().equals(signUp.getEmail()))
            {
                return -2;
            }
        }
		
        
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

        // Set the encrypted password to the signUpModel
        user.setPassword(encodedPassword);

        service.create(user);
        // verify in database, eventually
        return 1;
    }
    
    /**
     * Initializes the RegistrationService.
     */
    public void init() {
        System.out.println("in RS.init");
    }

    /**
     * Destroys the RegistrationService.
     */
    public void destroy() {
        System.out.println("in RS.des");
    }
}
