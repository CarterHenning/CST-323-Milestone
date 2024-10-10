package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

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
        List<ReviewEntity> reviews = new ArrayList<>();
        try {
            Iterable<ReviewEntity> reviewsIterable = reviewRepository.findAll();
            reviews = new ArrayList<>();
            reviewsIterable.forEach(reviews::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            this.reviewRepository.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        try {
            this.reviewRepository.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        try {
            this.reviewRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Finds reviews by course ID.
     * 
     * @param courseId The ID of the course to find reviews for.
     * @return A list of reviews for the specified course.
     */
    public List<ReviewEntity> findByCourseId(int courseId) {
        return this.reviewRepository.findByCourseId(courseId);
    }

    /**
     * Finds a review by user ID.
     * 
     * @param userId The ID of the user to find the review for.
     * @return The review of the specified user.
     */
    public ReviewEntity findByUserId(int userId) {
        return this.reviewRepository.findByUserId(userId);
    }
}
