package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gcu.data.UsersDataService;
import com.gcu.data.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

// import com.gcu.business.LoginService;
import com.gcu.business.RegistrationService;
import com.gcu.business.ReviewServiceInterface;
import com.gcu.business.CourseServiceInterface;
// import com.gcu.model.LoginModel;
import com.gcu.model.CourseModel;
import com.gcu.model.ReviewModel;
import com.gcu.model.SignUpModel;

import jakarta.validation.Valid;

/**
 * Handles requests related to the home page, sign-up, login, and user sign-in.
 */
@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private RegistrationService rs;

	@Autowired
	private CourseServiceInterface service;

	@Autowired
	private ReviewServiceInterface reviewService;

	@Autowired
	private UsersDataService usersDataService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Displays the home page with a list of course models.
	 *
	 * @param model   the Spring MVC model for rendering the view
	 * @param request the HTTP servlet request
	 * @return the view name for the home page
	 */
	@GetMapping("/")
	public String showHomePage(Model model, HttpServletRequest request) {
		logger.info("Entering showHomePage()");

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername();
			UserEntity user = usersDataService.findByUsername(username);

			// Store user details in session
			request.getSession().setAttribute("user", user);

			// Now you can get the user ID
			int userId = user.getId();

			// Store user ID in session
			request.getSession().setAttribute("userId", userId);
		} catch (Exception e) {
			logger.info("Exiting showHomePage() with an error");
			// Handle the exception, e.g., log it or show a user-friendly error message
			e.printStackTrace(); // Example: print the stack trace
		}

		List<CourseModel> courses = service.getCourses();
		model.addAttribute("courses", courses);
		logger.info("Exiting showHomePage()");
		return "courses";
	}

	/**
	 * Retrieves a list of courses from a service and adds them to the model for
	 * display.
	 *
	 * @param model the model to which course data and title will be added
	 * @return the name of the view to be rendered, in this case, "courses"
	 */
	@GetMapping("/getcourses")
	public String getCourses(Model model) {
		logger.info("Entering getCourses()");
		// get all courses
		List<CourseModel> courses = service.getCourses();
		// pass courses in key value
		model.addAttribute("title", "List of Courses");
		model.addAttribute("courses", courses);
		// return courses
		logger.info("Exiting getCourses()");
		return "courses";
	}

	/**
	 * Displays the
	 * 
	 * @param model Course Model for returned courses
	 * @param id    grabs the id to search from the url
	 * @return courses html page
	 */
	@GetMapping("/findcourse/{id}")
	public String getCourse(@PathVariable("id") int id, Model model) {
		logger.info("Entering getCourse()");
		// get model by id
		CourseModel course = service.getCourseById(id);

		// pass course in key value pairs
		model.addAttribute("title", "List of Courses");
		model.addAttribute("courses", course);
		// return
		logger.info("Exiting getCourse()");
		return "courses";
	}

	/**
	 * Displays the sign-up form view.
	 * 
	 * @param model the Spring MVC model for rendering the view
	 * @return the view name for the sign-up page
	 */
	@GetMapping("/signup")
	public String showSignUpPage(Model model) {
		logger.info("Entering showSignUpPage()");
		// Display Sign Up Form View
		model.addAttribute("title", "Sign Up Here!");
		model.addAttribute("signUpModel", new SignUpModel(null, null, null));
		logger.info("Exiting showSignUpPage()");
		return "signup";
	}

	/**
	 * Handles the sign-up form submission.
	 *
	 * @param signUpModel   the model representing the sign-up form data
	 * @param bindingResult the Spring MVC binding result for validation
	 * @param model         the Spring MVC model for rendering the view
	 * @return the view name for redirection after sign-up
	 */
	@PostMapping("/doSignUp")
	public String doSignUp(@Valid SignUpModel signUpModel, BindingResult bindingResult, Model model) {
		// check for validation errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Sign Up Here!");
			logger.info("Exiting doSignUp()");
			return "signup";
		}

		// Utilize the Registration Service to initialize the user
		int initializationResult = rs.initializeUser(signUpModel);
		switch (initializationResult) {
			case 1: // successful
				// Authenticate the user after successful sign-up
				logger.info("Exiting doSignUp()");
				return "redirect:/";
			case -1: // same username
				model.addAttribute("title", "Username already taken!");
				logger.info("Exiting doSignUp()");
				return "signup";
			case -2: // same email
				model.addAttribute("title", "Email already taken!");
				logger.info("Exiting doSignUp()");
				return "signup";
			default:
				model.addAttribute("title", "Error during sign-up!");
				logger.info("Exiting doSignUp()");
				return "signup";
		}
	}

	/**
	 * Displays the starter page for user sign-in.
	 * 
	 * @param model the Spring MVC model for rendering the view
	 * @return the view name for the sign-in page
	 */
	@GetMapping("/signIn")
	public String showStarterPage(Model model) {
		logger.info("Entering showStarterPage()");
		model.addAttribute("title", "RateMyCourse");
		logger.info("Exiting showStarterPage()");
		return "signIn";
	}

	/**
	 * Processes the creation of a new course.
	 * 
	 * @param courseModel   the course model containing the new course's data
	 * @param bindingResult the binding result for validation errors
	 * @param model         the model to be populated with attributes
	 * @return the view template to redirect to, either the createCourse template if
	 *         there are errors or the home page
	 */
	@PostMapping("/doCreateCourse")
	public String doCreateCourse(@Valid CourseModel courseModel, BindingResult bindingResult, Model model) {
		logger.info("Entering doCreateCourse()");
		if (bindingResult.hasErrors()) {
			System.out.println("Validation errors:");
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println(error.getField() + ": " + error.getDefaultMessage());
			}
			model.addAttribute("title", "Create Course Here!");
			logger.info("Exiting doCreateCourse()");
			return "createCourse";
		}
		// Save the new course
		service.createCourse(courseModel);

		// Get all courses including the newly added one
		List<CourseModel> courses = service.getCourses();

		model.addAttribute("courses", courses);

		logger.info("Exiting doCreateCourse()");
		return "redirect:/";
	}

	/**
	 * Retrieves details of a specific course by its ID.
	 *
	 * @param id    The ID of the course to retrieve details for.
	 * @param model The model to which course details will be added.
	 * @return The view name for displaying course details.
	 */
	@GetMapping("/course/{id}")
	public String showCourseDetails(@PathVariable int id, Model model) {
		logger.info("Entering showCourseDetails()");
		// Logic to retrieve the course details by id
		CourseModel course = service.getCourseById(id);
		List<ReviewModel> reviews = reviewService.getReviewsByCourseId(id);
		model.addAttribute("courseModel", course);
		model.addAttribute("reviews", reviews);

		// Get user details:
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		UserEntity user = usersDataService.findByUsername(username);
		model.addAttribute("user", user);

		logger.info("Exiting showCourseDetails()");
		return "courseDetails";
	}

	/**
	 * Deletes a course based on its ID.
	 *
	 * @param id The ID of the course to delete.
	 * @return Redirects to the home page after deletion.
	 */
	@PostMapping("/course/delete/{id}")
	public String deleteCourse(@PathVariable int id) {
		logger.info("Entering deleteCourse()");
		service.deleteCourse(id);
		logger.info("Exiting deleteCourse()");
		return "redirect:/";
	}

	/**
	 * Prepares the update form for a specific course.
	 *
	 * @param id    The ID of the course to update.
	 * @param model The model to which course details will be updated.
	 * @return The view name for updating the course.
	 */
	@PostMapping("/course/update/{id}")
	public String updateCourse(@PathVariable int id, Model model) {
		logger.info("Entering updateCourse()");
		CourseModel course = service.getCourseById(id);
		model.addAttribute("courseModel", course);
		logger.info("Exiting updateCourse()");
		return "updateCourse";
	}

	/**
	 * Performs the update operation for a course.
	 *
	 * @param id            grabbing from the url path
	 * @param courseModel   The updated course model.
	 * @param bindingResult The result of the validation.
	 * @param model         The model to which attributes will be updated.
	 * @return Redirects to the home page after updating the course.
	 */
	@PostMapping("/course/update/doUpdate/{id}")
	public String doUpdateCourse(@PathVariable int id, @Valid CourseModel courseModel, BindingResult bindingResult,
			Model model) {

		logger.info("Entering doUpdateCourse()");
		if (bindingResult.hasErrors()) {
			System.out.println("Validation errors:");
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println(error.getField() + ": " + error.getDefaultMessage());
			}
			model.addAttribute("title", "Update Course Here!");
			logger.info("Exiting doUpdateCourse()");
			return "updateCourse";
		}

		// updates course
		service.updateCourse(courseModel, id);

		// Get all courses including the updated one
		List<CourseModel> courses = service.getCourses();

		model.addAttribute("courses", courses);

		logger.info("Exiting doUpdateCourse()");
		return "redirect:/";
	}

	@GetMapping("/addReview/{courseId}")
	public String showAddReviewForm(@PathVariable("courseId") int courseId, Model model, HttpServletRequest request) {
		logger.info("Entering showAddReviewForm()");
		// Retrieve the course by ID
		CourseModel course = service.getCourseById(courseId);

		// Retrieve the user ID from the session
		Integer userId = (Integer) request.getSession().getAttribute("userId");

		// Create a new ReviewModel and set courseId and userId
		ReviewModel review = new ReviewModel();
		review.setCourseId(courseId);
		if (userId != null) {
			review.setUserId(userId); // Set user ID only if available
		}

		// Add the necessary attributes to the model
		model.addAttribute("courseModel", course);
		model.addAttribute("reviewModel", review);

		// Return the name of the Thymeleaf template (e.g., addUserReview.html)
		logger.info("Exiting showAddReviewForm()");
		return "addUserReview";
	}

	@PostMapping("/doCreateReview")
	public String doCreateReview(@Valid ReviewModel reviewModel, @SessionAttribute("userId") int userId,
			BindingResult bindingResult, Model model) {

		logger.info("Entering doCreateReview()");
		try {
			reviewModel.setUserId(userId);
			
			// Save the review using the service
			reviewService.createReview(reviewModel);
			
			int averageRating = reviewService.calculateAverageRating(reviewModel.getCourseId());
			
			service.updateCourseRating(reviewModel.getCourseId(), averageRating);

			logger.info("Exiting doCreateReview()");
			return "redirect:/course/" + reviewModel.getCourseId();
			
		} catch (Exception e) {
			// Handle any exceptions that might occur
			
			logger.info("Exiting doCreateReview()");
			return "redirect:/addReview/" + reviewModel.getCourseId(); // Redirect back to the form
		}
	}
}
