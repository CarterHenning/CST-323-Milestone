package com.gcu.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class SignUpModel {

    public SignUpModel(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    @NotNull(message = "Email is Required")
    @Size(min = 1, max = 32, message = "Email must be between 1 and 32 characters")
    private String email = "";

    @NotNull(message = "Username is Required")
    @Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters")
    private String userName = "";

    @NotNull(message = "Password is Required")
    @Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters")
    private String password = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() { // Corrected method name
        return userName;
    }

    public void setUserName(String userName) { // Corrected method name
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

