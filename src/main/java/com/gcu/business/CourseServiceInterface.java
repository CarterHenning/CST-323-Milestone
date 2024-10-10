package com.gcu.business;

import java.util.List;

import com.gcu.model.CourseModel;


/**
 * An interface for managing courses.
 */
public interface CourseServiceInterface 
{
    /**
     * Retrieves the list of all courses.
     * @return the list of courses
     */
    public List<CourseModel> getCourses();

    /**
     * Saves a new course.
     * @param newModel the new course to save
     * @return the updated list of courses
     */
    public boolean createCourse(CourseModel newModel);

    /**
     * Updates a course.
     * @param model the course to update
     * @param id id of the course
     * @return the updated list of courses
     */
    public boolean updateCourse(CourseModel model, int id);

    
	/**
	 * Retrieves a course by its ID.
	 *
	 * @param id the ID of the course to retrieve
	 * @return the course with the specified ID, or null if not found
	 */
    public CourseModel getCourseById(int id);

    
    /**
     * Deletes a course by its ID.
     *
     * @param id the ID of the course to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteCourse(int id);
}
