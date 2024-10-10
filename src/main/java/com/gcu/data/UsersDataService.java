package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.gcu.data.entity.UserEntity;
import com.gcu.data.repository.UsersRepository;
import com.gcu.model.SignUpModel;

/**
 * This class provides data access methods for managing users in a database.
 */
@Service
public class UsersDataService implements DataAccessInterface<SignUpModel>, UsersDataAccessInterface {
    private final UsersRepository usersRepository;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    /**
     * Constructor to set the data source and initialize the JDBC template.
     * 
     * @param dataSource the data source to be used for database access
     * @param usersRepository takes in the usersRepository
     */
    public UsersDataService(DataSource dataSource, UsersRepository usersRepository) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        this.usersRepository = usersRepository;
    }

    /**
     * Retrieves all users from the database.
     * 
     * @return a list of all users in the database
     */
    @Override
    public List<SignUpModel> findAll() {
        String sql = "SELECT * FROM users"; // Correct table name
        // Perform database query to fetch users
        List<SignUpModel> users = new ArrayList<>();
        try {
            SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
            while (srs.next()) {
                users.add(new SignUpModel(srs.getString("email"),
                        srs.getString("userName"),
                        srs.getString("password")));
            }
        } catch (Exception e) {
            e.printStackTrace(); // For now, just print stack trace
        }

        // Populate users list from database
        return users;
    }

    /**
     * Retrieves one user by id from the database.
     * 
     * @param id the id of the user specified
     * @return the user with the specified ID, or null if not found
     */
    @Override
    public SignUpModel findById(int id) {
        // Implement logic to find user by id from the database
        return null;
    }

    /**
     * Creates a new post in the database
     * 
     * @param user the user to create
     * @return true if the user was created successfully, false otherwise
     */
    @Override
    public boolean create(SignUpModel user) {
        String sql = "INSERT INTO users(EMAIL, USERNAME, PASSWORD) VALUES(?, ?, ?)";
        try {
            int rows = jdbcTemplateObject.update(sql, user.getEmail(),user.getUserName(), user.getPassword());
            return rows == 1 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates user in the database.
     * 
     * @param user the user to update
     * @return true if the user was updated successfully, false otherwise
     */
    @Override
    public boolean update(SignUpModel user) {
        // Implement logic to update a user in the database
        // Example:
        String sql = "UPDATE users SET userName=?, email=? WHERE id=?";
        int updatedRows = jdbcTemplateObject.update(sql, user.getUserName(), user.getEmail());
        return updatedRows > 0;
    }

    /**
     * Deletes an existing user in the database.
     * 
     * @param id the id of user to delete
     * @return true if the user was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(int id) {
        // Implement logic to delete a user from the database
        return false;
    }

    /**
     * Finds a user by their userName using the repository.
     *
     * @param userName the userName of the user to find
     * @return the UserEntity corresponding to the userName, or null if not found
     */
    @Override
    public UserEntity findByUsername(String userName) {
        return usersRepository.findByUserName(userName);
    }
}
