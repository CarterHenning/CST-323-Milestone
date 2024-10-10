package com.gcu.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gcu.data.entity.ReviewEntity;

/**
 * ReviewRepository interface for accessing review data.
 */
@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer> {


    List<ReviewEntity> findByCourseId(int courseId);

    /**
     * Find a review by the user ID.
     *
     * @param userId the ID of the user
     * @return the review associated with the user
     */
    ReviewEntity findByUserId(int userId);
}
