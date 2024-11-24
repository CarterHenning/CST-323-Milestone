package com.gcu.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.CourseServiceInterface;
import com.gcu.model.CourseModel;


/**
 * Rest controller for managing courses.
 */
@RestController
@RequestMapping("/service")
public class CoursesRestService {
	
    @Autowired
	private CourseServiceInterface service;

    private final Logger logger = LoggerFactory.getLogger(CoursesRestService.class);
	
	
    /**
     * Retrieves a list of all courses.
     *
     * @return A list of {@link CourseModel} objects representing the courses.
     */
    @GetMapping(value="/courses", produces={MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE})
	public List<CourseModel> getCourses()
	{
        logger.info("Entering getCourses()");
        logger.info("Exiting getCourses()");
		return service.getCourses();
	}
	
	
    /**
     * Retrieves a course by its ID.
     *
     * @param id The ID of the course to retrieve.
     * @return A {@link ResponseEntity} containing the {@link CourseModel} object if found, or a NOT_FOUND status if not found.
     */
    @GetMapping("/getCourse/{id}")
    public ResponseEntity<CourseModel> getCourses(@PathVariable("id") int id) {
        logger.info("Entering getCourses()");
        try {
            CourseModel course = service.getCourseById(id);
            if (course == null) {
                logger.info("Exiting getCourses()");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
            } else {
                logger.info("Exiting getCourses()");
            	return new ResponseEntity<>(course, HttpStatus.OK);             
            }
        } catch (Exception e) {
            // Catch any exception and return with HttpStatus.INTERNAL_SERVER_ERROR
            logger.info("Exiting getCourses()");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
