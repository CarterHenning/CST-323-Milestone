package com.gcu.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * Represents a PostModel with imageUrl, title, description, date, and userId attributes.
 */
@JacksonXmlRootElement
public class CourseModel {
    private int id;
    private String title;
    private String description;
    private int rating;


    public CourseModel() {
    }


    public CourseModel(int id, String title, String description, int rating) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    /**
     * Retrieves the ID of the post.
     *
     * @return The ID of the post.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the post.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter methods for other attributes...
    

    /**
     * Retrieves the title of the post.
     *
     * @return The title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description or content of the post.
     *
     * @return The description of the post.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description or content of the post.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the date when the post was created or published.
     *
     * @return The date of the post.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the date when the post was created or published.
     *
     * @param date The date to set.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}

