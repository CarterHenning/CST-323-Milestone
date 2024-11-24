package com.gcu.business;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.CoursesDataService;
import com.gcu.data.entity.CourseEntity;
import com.gcu.data.repository.CourseRepository;
import com.gcu.model.CourseModel;

/**
 * A service class for managing courses.
 */
@Service
public class CourseBusinessService implements CourseServiceInterface
{    
    @Autowired
	private CoursesDataService service;

    private final Logger logger = LoggerFactory.getLogger(CourseBusinessService.class);


    @Override
    public void updateCourseRating(int courseId, int newRating) {
        logger.info("Entering updateCourseRating()");
        CourseModel course = getCourseById(courseId);
        course.setRating(newRating);
        updateCourse(course, course.getId());
        logger.info("Entering updateCourseRating()");
    }

    /**
     * Retrieves the list of all courses.
     *
     * @return the list of courses
     */
    @Override
    public List<CourseModel> getCourses() {
        logger.info("Entering getCourses()");
        List<CourseEntity> courseEntities = service.findAll();
        
        List<CourseModel> coursesDomain = new ArrayList<>();
        for (CourseEntity entity : courseEntities) {
            CourseModel courseModel = new CourseModel(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getRating());
            courseModel.setId(entity.getId());
            coursesDomain.add(courseModel);
        }
        logger.info("Exiting getCourses()");
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
        logger.info("Entering createCourse()");
        CourseEntity entity = new CourseEntity(newCourse.getId(), newCourse.getTitle(), newCourse.getDescription(), newCourse.getRating());
        logger.info("Exiting createCourse()");
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
        logger.info("Entering updateCourse()");
        CourseEntity entity = service.findById(id);
        entity.setTitle(course.getTitle());
        entity.setDescription(course.getDescription());
        entity.setRating(course.getRating());
        logger.info("Exiting updateCourse()");
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
        logger.info("Entering getCourseById()");
        CourseEntity entity = service.findById(id);
        if (entity != null) {
            CourseModel model = new CourseModel(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getRating());
            model.setId(entity.getId());
            
            logger.info("Exiting getCourseById()");
            return model;
        }

        logger.info("Exiting getCourseById()");
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
        logger.info("Entering deleteCourse()");
        logger.info("Exiting deleteCourse()");
        return service.delete(id);
    }
}