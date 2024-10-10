package com.gcu.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.gcu.model.ReviewModel;

/**
 * Represents a course entity, mapping to the "POSTS" table in the database.
 */
@Table("userreview")
public class ReviewEntity {

    // Default constructor
    public ReviewEntity() {
    }

    // Parameterized constructor
    public ReviewEntity(int userId, int courseId, int rating, String description) {
        this.userId = userId;
        this.courseId = courseId;
        this.rating = rating;
        this.description = description;
    }

    // Construct from ReviewModel object (if applicable)
    public ReviewEntity(ReviewModel review) {
        this.reviewId = review.getReviewId();
        this.userId = review.getUserId();
        this.courseId = review.getCourseId();
        this.rating = review.getRating();
        this.description = review.getDescription();
    }

    @Id
    @Column("reviewId")
    private int reviewId;

    @Column("userId")
    private int userId;

    @Column("courseId")
    private int courseId;

    @Column("rating")
    private int rating;

    @Column("description")
    private String description;

    // Getters and setters (if needed)
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

