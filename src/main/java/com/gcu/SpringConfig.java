package com.gcu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gcu.business.LoginService;
import com.gcu.business.RegistrationService;


/**
 * Configuration class for defining Spring beans.
 */
@Configuration
public class SpringConfig 
{
    /**
     * Spring Bean for the LoginService
     * @return new loginService
     */
    @Bean(name="loginService", initMethod="init", destroyMethod="destroy")
    public LoginService getLogin()
    {
        return new LoginService();
    }

    /**
     * Spring Bean for the RegistrationService
     * @return new registrationService
     */
    @Bean(name="registrationService", initMethod="init", destroyMethod="destroy")
    public RegistrationService getSignUp()
    {
        return new RegistrationService();
    }
}
