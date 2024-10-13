package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.CourseServiceInterface;
import com.gcu.business.RegistrationService;
import com.gcu.business.ReviewServiceInterface;
import com.gcu.data.UsersDataService;
import com.gcu.data.entity.UserEntity;
import com.gcu.model.CourseModel;
import com.gcu.model.ReviewModel;

/**
 * Controller class for handling account-related requests.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private RegistrationService rs;

	@Autowired
	private CourseServiceInterface service;

	@Autowired
	private ReviewServiceInterface reviewService;

	@Autowired
	private UsersDataService usersDataService;

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
				if (review.getUserId() == user.getId())
				{
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

} // end AccountController
