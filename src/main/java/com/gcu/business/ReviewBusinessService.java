package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

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

    public int calculateAverageRating(int courseId) {
        List<ReviewEntity> reviews = reviewRepository.findByCourseId(courseId);
        if (reviews.isEmpty()) {
            return 10; // or handle it according to your needs
        }
        int total = reviews.stream().mapToInt(ReviewEntity::getRating).sum();
        return total / reviews.size();
    }

    @Override
    public boolean createReview(ReviewModel newModel) {
        ReviewEntity entity = new ReviewEntity(newModel.getUserId(), newModel.getCourseId(), newModel.getRating(), newModel.getDescription());
        return service.create(entity);
    }

    @Override
    public boolean updateReview(ReviewModel model, int id) {
        ReviewEntity entity = service.findById(id);
        if (entity != null) {
            entity.setRating(model.getRating());
            entity.setDescription(model.getDescription());
            return service.update(entity);
        }
        return false;
    }

    @Override
    public List<ReviewModel> getReviewsByCourseId(int courseId) {
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
            return models;
        }
        
        // Return an empty list if there are no reviews for the course
        return new ArrayList<>();
    }

    @Override
    public ReviewModel getReviewByUserId(int userId) {
        ReviewEntity entity = service.findByUserId(userId);
        if (entity != null) {
            return new ReviewModel(entity.getReviewId(), entity.getUserId(), entity.getCourseId(), entity.getRating(), entity.getDescription());
        }
        return null;
    }

    @Override
    public boolean deleteReviews(int id) {
        return service.delete(id);
    }
}
