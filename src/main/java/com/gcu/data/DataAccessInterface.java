package com.gcu.data;

import java.util.List;

/**
 * Interface for all data service classes to use.
 *
 * @param <T> the type of entity managed by the data service
 */
public interface DataAccessInterface<T> {
    /**
     * Retrieves all entities of type T from the data source.
     *
     * @return a list of all entities
     */
    public List<T> findAll();

    /**
     * Retrieves an entity of type T from the data source based on its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the given ID, or null if not found
     */
    public T findById(int id);

    /**
     * Creates a new entity in the data source.
     *
     * @param t the entity to create
     * @return true if the entity was successfully created, false otherwise
     */
    public boolean create(T t);

    /**
     * Updates an existing entity in the data source.
     *
     * @param t the entity to update
     * @return true if the entity was successfully updated, false otherwise
     */
    public boolean update(T t);

    /**
     * Deletes an entity from the data source based on its ID.
     *
     * @param id the ID of the entity to delete
     * @return true if the entity was successfully deleted, false otherwise
     */
    public boolean delete(int id);
}

