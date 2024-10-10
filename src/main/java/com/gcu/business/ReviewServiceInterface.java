package com.gcu.business;

import java.util.List;

import com.gcu.model.ReviewModel;



public interface ReviewServiceInterface 
{
    
    public boolean createReview(ReviewModel newModel);

    public boolean updateReview(ReviewModel model, int id);
   
    public List<ReviewModel> getReviewsByCourseId(int courseId);

    public ReviewModel getReviewByUserId(int userId);

    public boolean deleteReviews(int id);

    public int calculateAverageRating(int courseId);
}
