package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.ReviewDataService;
import com.gcu.data.entity.ReviewEntity;
import com.gcu.data.repository.ReviewRepository;
import com.gcu.model.ReviewModel;

@Service
public class ReviewBusinessService implements ReviewServiceInterface {

    @Autowired
    private ReviewDataService service;

    @Autowired
    private ReviewRepository reviewRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public int calculateAverageRating(int courseId) {
        logger.info("Entering calculateAverageRating()");
        List<ReviewEntity> reviews = reviewRepository.findByCourseId(courseId);
        if (reviews.isEmpty()) {
            logger.info("Exiting calculateAverageRating()");
            return 10; // or handle it according to your needs
        }
        int total = reviews.stream().mapToInt(ReviewEntity::getRating).sum();
        logger.info("Exiting calculateAverageRating()");
        return total / reviews.size();
    }

    @Override
    public boolean createReview(ReviewModel newModel) {
        logger.info("Entering createReview()");
        ReviewEntity entity = new ReviewEntity(newModel.getUserId(), newModel.getCourseId(), newModel.getRating(), newModel.getDescription());
        logger.info("Exiting createReview()");
        return service.create(entity);
    }

    @Override
    public boolean updateReview(ReviewModel model, int id) {
        logger.info("Entering updateReview()");
        ReviewEntity entity = service.findById(id);
        if (entity != null) {
            entity.setRating(model.getRating());
            entity.setDescription(model.getDescription());
            logger.info("Exiting updateReview()");
            return service.update(entity);
        }
        logger.info("Exiting updateReview()");
        return false;
    }

    @Override
    public List<ReviewModel> getReviewsByCourseId(int courseId) {
        logger.info("Entering getReviewsByCourseId()");
        List<ReviewEntity> entities = service.findByCourseId(courseId);
        
        // Check if the list is not null and not empty
        if (entities != null && !entities.isEmpty()) {
            List<ReviewModel> models = new ArrayList<>();
            
            // Iterate through the list of ReviewEntity and convert each one to a ReviewModel
            for (ReviewEntity entity : entities) {
                models.add(new ReviewModel(
                    entity.getReviewId(),
                    entity.getUserId(),
                    entity.getCourseId(),
                    entity.getRating(),
                    entity.getDescription()
                ));
            }
            
            // Return the list of ReviewModel
            logger.info("Exiting getReviewsByCourseId()");
            return models;
        }
        
        // Return an empty list if there are no reviews for the course
        logger.info("Exiting getReviewsByCourseId()");
        return new ArrayList<>();
    }

    @Override
    public ReviewModel getReviewByUserId(int userId) {
        logger.info("Entering getReviewByUserId()");
        ReviewEntity entity = service.findByUserId(userId);
        if (entity != null) {
            logger.info("Exiting getReviewByUserId()");
            return new ReviewModel(entity.getReviewId(), entity.getUserId(), entity.getCourseId(), entity.getRating(), entity.getDescription());
        }
        logger.info("Exiting getReviewByUserId()");
        return null;
    }
    
    @Override
    public ReviewModel getReviewById(int reviewId) {
        logger.info("Entering getReviewById()");
        ReviewEntity entity = service.findById(reviewId);
        if (entity != null) {
            logger.info("Exiting getReviewById()");
            return new ReviewModel(entity.getReviewId(), entity.getUserId(), entity.getCourseId(), entity.getRating(), entity.getDescription());
        }
        logger.info("Exiting getReviewById()");
        return null;
    }

    @Override
    public boolean deleteReviews(int id) {
        logger.info("Entering deleteReviews()");
        logger.info("Exiting deleteReviews()");
        return service.delete(id);
    }
}
