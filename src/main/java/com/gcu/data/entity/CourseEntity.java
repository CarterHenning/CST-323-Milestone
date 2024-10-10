package com.gcu.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.gcu.model.CourseModel;

/**
 * Represents a course entity, mapping to the "POSTS" table in the database.
 */
@Table("course")
public class CourseEntity {

    /**
     * Default constructor.
     */
    public CourseEntity() {

    }


    public CourseEntity(int id, String title, String description, int rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    /**
     * Constructs a CourseEntity from a CourseModel object.
     *
     * @param course The CourseModel object to construct the entity from.
     */
    public CourseEntity(CourseModel course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.rating = course.getRating();
    }

    @Id
    @Column("id")
    int id;

    @Column("title")
    String title;

    @Column("description")
    String description;

    @Column("rating")
    int rating;

    /**
     * Retrieves the title of the course.
     *
     * @return The title of the course.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the course.
     *
     * @param title The title of the course to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the course.
     *
     * @return The description of the course.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the course.
     *
     * @param description The description of the course to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the ID of the user who created the course.
     *
     * @return The ID of the user who created the course.
     */
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }
}
