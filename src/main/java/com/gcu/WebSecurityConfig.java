package com.gcu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gcu.business.UserBusinessService;

@Configuration
public class WebSecurityConfig {
    private UserBusinessService service;

    /**
     * Sets the UserBusinessService to be used for authentication.
     *
     * @param service the UserBusinessService for authentication
     */
    @Autowired
    public void setService(UserBusinessService service) {
        this.service = service;
    }

    /**
     * Configures security filters and request authorization rules.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/doSignUp").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .permitAll()
                        .defaultSuccessUrl("/", true))
                .logout(lo -> lo
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                        .logoutSuccessUrl("/login"));

        return http.build();
    }

    /**
     * Configures the authentication manager to use the UserBusinessService and
     * password encoder.
     *
     * @param auth the AuthenticationManagerBuilder to configure
     * @throws Exception if an error occurs during configuration
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(service)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Creates and returns a BCryptPasswordEncoder.
     *
     * @return a BCryptPasswordEncoder instance
     */
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}