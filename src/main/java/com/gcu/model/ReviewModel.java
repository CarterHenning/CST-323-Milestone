package com.gcu.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * Represents a PostModel with imageUrl, title, description, date, and userId attributes.
 */
@JacksonXmlRootElement
public class ReviewModel {
    private int reviewId;
    private int userId;
    private int courseId;
    private int rating;
    private String description;


    public ReviewModel() {
    }


    public ReviewModel(int reviewId, int userId, int courseId, int rating, String description) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.courseId = courseId;
        this.rating = rating;
        this.description = description;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

