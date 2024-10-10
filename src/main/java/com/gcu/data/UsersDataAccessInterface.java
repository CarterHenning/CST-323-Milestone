package com.gcu.data;

/**
 * Interface for accessing user data.
 *
 * @param <T> The type of the entity being accessed.
 */
public interface UsersDataAccessInterface<T> {
	/**
	 * Finds a user by their userName.
	 *
	 * @param userName The userName of the user to find.
	 * @return The object corresponding to the given userName, or null if not found.
	 */
	T findByUsername(String userName);
}
