package com.gcu.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.entity.CourseEntity;
import com.gcu.data.repository.CourseRepository;

/**
 * A service class for managing course data that implements our data service
 * interface
 */
@Service
public class CoursesDataService implements DataAccessInterface<CourseEntity> {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Constructs a new CoursesDataService with the given CourseRepository.
     * 
     * @param courseRepository The repository for courses.
     */
    public CoursesDataService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Retrieves all courses from the database.
     * 
     * @return A list of all courses.
     */
    @Override
    public List<CourseEntity> findAll() {
        List<CourseEntity> courses = new ArrayList<>();
        try {
            Iterable<CourseEntity> coursesIterable = courseRepository.findAll();
            courses = new ArrayList<>();
            coursesIterable.forEach(courses::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Creates a new course.
     * 
     * @param course The course to create.
     * @return True if the course was successfully created, false otherwise.
     */
    @Override
    public boolean create(CourseEntity course) {
        try {
            this.courseRepository.save(course);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Finds a course by its ID.
     * 
     * @param id The ID of the course to find.
     * @return The found course, or null if not found.
     */
    @Override
    public CourseEntity findById(int id) {
        return this.courseRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing course.
     * 
     * @param course The course to update.
     * @return True if the course was successfully updated, false otherwise.
     */
    @Override
    public boolean update(CourseEntity course) {
        try {
            this.courseRepository.save(course);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes a course by its ID.
     * 
     * @param id The ID of the course to delete.
     * @return True if the course was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        try {
            this.courseRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
