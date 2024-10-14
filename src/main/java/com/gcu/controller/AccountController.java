package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.gcu.business.CourseBusinessService;
import com.gcu.business.CourseServiceInterface;
import com.gcu.business.ReviewServiceInterface;
import com.gcu.data.CoursesDataService;
import com.gcu.data.UsersDataService;
import com.gcu.data.entity.UserEntity;
import com.gcu.model.CourseModel;
import com.gcu.model.ReviewModel;

import jakarta.validation.Valid;

/**
 * Controller class for handling account-related requests.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private CourseServiceInterface service;

	@Autowired
	private ReviewServiceInterface reviewService;

	@Autowired
	private UsersDataService usersDataService;

	/**
	 * Shows the current user's account page
	 * 
	 * @param model Model for display purposes
	 * @return The view for the account page
	 */
	@GetMapping("")
	public String showAccountPage(Model model) {
		// Get current User
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		UserEntity user = usersDataService.findByUsername(username);
		model.addAttribute("user", user);

		// Get all courses
		List<CourseModel> courses = service.getCourses();
		// To save user's reviews
		List<ReviewModel> userReviews = new ArrayList<ReviewModel>();

		// Get all user's reviews
		for (CourseModel course : courses) {
			for (ReviewModel review : reviewService.getReviewsByCourseId(course.getId())) {
				if (review.getUserId() == user.getId()) {
					userReviews.add(review);
				} // end if
			} // end for
		} // end for

		// Give user's reviews to model
		model.addAttribute("userReviews", userReviews);
		// Give courses back to model
		model.addAttribute("courses", courses);
		// Give page's title to the model
		model.addAttribute("title", "Account");
		// Return view
		return "account";
	} // end showAccountPage

	/**
	 * Shows the edit review page
	 * 
	 * @param id    The ID of the review to edit
	 * @param model Model for display purposes
	 * @return The view for the edit review page
	 */
	@GetMapping("/review/{id}/edit")
	public String showEditReviewPage(@PathVariable int id, Model model) {
		// Get review:
		ReviewModel review = reviewService.getReviewById(id);
		// Give review to model
		model.addAttribute("review", review);
		// Get course review was for
		CourseModel course = service.getCourseById(review.getCourseId());
		// Give course to model
		model.addAttribute("course", course);

		// Give page's title to the model
		model.addAttribute("title", "Edit Review");
		// Return view
		return "editReview";
	}

	/**
	 * Edits an existing review
	 * 
	 * @param review The edited review
	 * @param model  Model for display purposes
	 * @return Redirects to account page after editing
	 */
	@PostMapping("/doEditReview")
	public String doEditReview(@Valid ReviewModel review, Model model) {
		try {
			// Update review using the service
			reviewService.updateReview(review, review.getReviewId());

			// Update the average course rating now that there was a new review
			int averageRating = reviewService.calculateAverageRating(review.getCourseId());

			service.updateCourseRating(review.getCourseId(), averageRating);

			return "redirect:/account";

		} catch (Exception e) {
			// Handle any exceptions that might occur

			return "redirect:/account/" + review.getReviewId() + "/edit"; // Redirect back to the form
		}
	}

	/**
	 * Deletes an existing review
	 * 
	 * @param id    The ID of the review to delete
	 * @param model Model for display purposes
	 * @return Redirects to the account page after deletion
	 */
	@GetMapping("/doDeleteReview/{id}")
	public String doDeleteReview(@PathVariable int id, Model model) {
		try {
			reviewService.deleteReviews(id);

			// Update the average course rating now that there was a new review (for every course)
			for (CourseModel course : service.getCourses())
			{
				int averageRating = reviewService.calculateAverageRating(course.getId());
				service.updateCourseRating(course.getId(), averageRating);
			}

			return "redirect:/account";

		} catch (Exception e) {
			// Handle any exceptions that might occur

			return "redirect:/account"; // Redirect back to the account page
		}
	}

} // end AccountController
