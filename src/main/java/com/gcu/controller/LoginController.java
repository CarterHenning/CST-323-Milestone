package com.gcu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller class for handling login-related requests.
 */
@Controller
public class LoginController 
{    

    private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

   /**
     * Displays the login form view.
     * @param model the Spring MVC model for rendering the view
     * @return the view name for the login page
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        logger.info("Entering showLoginPage()");
        
        // Display Login Form View
        model.addAttribute("title", "Login");
        logger.info("Exiting showLoginPage()");
        return "login";
    }
    
}
