package com.gcu.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represents a user entity, mapping to the "USERS" table in the database.
 */
@Table("users")
public class UserEntity {

    @Id
    @Column("id")
    int id;

    @Column("userName")
    String userName;

    @Column("email")
    String email;

    @Column("password")
    String password;

    /**
     * Default constructor.
     */
    public UserEntity() {

    }


    public UserEntity(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The ID of the user to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address of the user to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the userName of the user.
     *
     * @return The userName of the user.
     */
    public String getUsername() {
        return userName;
    }

    /**
     * Sets the userName of the user.
     *
     * @param userName The userName of the user to set.
     */
    public void setUsername(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
