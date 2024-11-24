package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.entity.ReviewEntity;
import com.gcu.data.repository.ReviewRepository;

/**
 * A service class for managing review data that implements our data service interface
 */
@Service
public class ReviewDataService implements DataAccessInterface<ReviewEntity> {

    @Autowired
    private ReviewRepository reviewRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs a new ReviewDataService with the given ReviewRepository.
     * 
     * @param reviewRepository The repository for reviews.
     */
    public ReviewDataService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Retrieves all reviews from the database.
     * 
     * @return A list of all reviews.
     */
    @Override
    public List<ReviewEntity> findAll() {
        logger.info("Entering findAll()");
        List<ReviewEntity> reviews = new ArrayList<>();
        try {
            Iterable<ReviewEntity> reviewsIterable = reviewRepository.findAll();
            reviews = new ArrayList<>();
            reviewsIterable.forEach(reviews::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Exiting findAll()");
        return reviews;
    }

    /**
     * Creates a new review.
     * 
     * @param review The review to create.
     * @return True if the review was successfully created, false otherwise.
     */
    @Override
    public boolean create(ReviewEntity review) {
        logger.info("Entering create()");
        try {
            this.reviewRepository.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exiting create()");
            return false;
        }
        logger.info("Exiting create()");
        return true;
    }

    /**
     * Finds a review by its ID.
     * 
     * @param id The ID of the review to find.
     * @return The found review, or null if not found.
     */
    @Override
    public ReviewEntity findById(int id) {
        logger.info("Entering findById()");
        logger.info("Exiting findById()");
        return this.reviewRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing review.
     * 
     * @param review The review to update.
     * @return True if the review was successfully updated, false otherwise.
     */
    @Override
    public boolean update(ReviewEntity review) {
        logger.info("Entering update()");
        try {
            this.reviewRepository.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exiting update()");
            return false;
        }
        logger.info("Exiting update()");
        return true;
    }

    /**
     * Deletes a review by its ID.
     * 
     * @param id The ID of the review to delete.
     * @return True if the review was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        logger.info("Entering delete()");
        try {
            this.reviewRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exiting delete()");
            return false;
        }
        logger.info("Exiting delete()");
        return true;
    }

    /**
     * Finds reviews by course ID.
     * 
     * @param courseId The ID of the course to find reviews for.
     * @return A list of reviews for the specified course.
     */
    public List<ReviewEntity> findByCourseId(int courseId) {
        logger.info("Entering findByCourseId()");
        logger.info("Exiting findByCourseId()");
        return this.reviewRepository.findByCourseId(courseId);
    }
    
    /**
     * Finds a review by user ID.
     * 
     * @param userId The ID of the user to find the review for.
     * @return The review of the specified user.
     */
    public ReviewEntity findByUserId(int userId) {
        logger.info("Entering findByUserId()");
        logger.info("Exiting findByUserId()");
        return this.reviewRepository.findByUserId(userId);
    }
}
