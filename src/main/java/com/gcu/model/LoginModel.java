package com.gcu.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a data model for login information.
 */
public class LoginModel {

    /**
     * User name for login.
     * Annotated with @NotNull to specify that the field cannot be null.
     * Annotated with @Size to specify the minimum and maximum size of the string.
     */
    @NotNull(message = "User name is a required field")
    @Size(min = 1, max = 32, message = "User name must be between 1 and 32 characters")
    private String userName = "";

    /**
     * Password for login.
     * Annotated with @NotNull to specify that the field cannot be null.
     * Annotated with @Size to specify the minimum and maximum size of the string.
     */
    @NotNull(message = "Password is a required field")
    @Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters")
    private String password = "";

    /**
     * Gets the user name for login.
     *
     * @return The user name.
     */
    public String getUsername() {
        return userName;
    }

    /**
     * Sets the user name for login.
     *
     * @param userName The user name to set.
     */
    public void setUsername(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password for login.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for login.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
