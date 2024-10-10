package com.gcu.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gcu.data.entity.CourseEntity;

/**
 * Repository interface for managing CourseEntity objects in the database.
 */
public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {

	

}
