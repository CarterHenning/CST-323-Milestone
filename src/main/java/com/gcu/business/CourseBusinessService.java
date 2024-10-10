package com.gcu.business;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.CoursesDataService;
import com.gcu.data.entity.CourseEntity;
import com.gcu.model.CourseModel;

/**
 * A service class for managing courses.
 */
@Service
public class CourseBusinessService implements CourseServiceInterface
{    
    @Autowired
	private CoursesDataService service;

    /**
     * Retrieves the list of all courses.
     *
     * @return the list of courses
     */
    @Override
    public List<CourseModel> getCourses() {
        List<CourseEntity> courseEntities = service.findAll();
        
        List<CourseModel> coursesDomain = new ArrayList<>();
        for (CourseEntity entity : courseEntities) {
            CourseModel courseModel = new CourseModel(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getRating());
            courseModel.setId(entity.getId());
            coursesDomain.add(courseModel);
        }
        return coursesDomain;
    }

    /**
     * creates a new course.
     *
     * @param newCourse the new course to create
     * @return true if the course is created successfully, false otherwise
     */
    @Override
    public boolean createCourse(CourseModel newCourse) {
        CourseEntity entity = new CourseEntity(newCourse.getId(), newCourse.getTitle(), newCourse.getDescription(), newCourse.getRating());
        return service.create(entity);
    }

    /**
     * updates a course
     *
     * @param course the course to update
     * @return true if the course is saved successfully, false otherwise
     */
    @Override
    public boolean updateCourse(CourseModel course, int id) {
        CourseEntity entity = service.findById(id);
        entity.setTitle(course.getTitle());
        entity.setDescription(course.getDescription());
        entity.setRating(course.getRating());
        return service.update(entity);
    }   

    /**
     * get course by its id
     * 
     * @param id course id
     * @return the course correlating with the id
     */
    @Override
    public CourseModel getCourseById(int id) 
    {
        CourseEntity entity = service.findById(id);
        if (entity != null) {
            CourseModel model = new CourseModel(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getRating());
            model.setId(entity.getId());
            
            return model;
        }
        return null;
    }

    /**
     * Deletes a course based on the provided ID.
     *
     * @param id the ID of the course to be deleted
     * @return true if the course was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteCourse(int id)
    {
        return service.delete(id);
    }
}