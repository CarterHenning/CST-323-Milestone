package com.gcu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.data.UsersDataService;
import com.gcu.data.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

// import com.gcu.business.LoginService;
import com.gcu.business.RegistrationService;
import com.gcu.business.CourseServiceInterface;
// import com.gcu.model.LoginModel;
import com.gcu.model.CourseModel;
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
    private UsersDataService usersDataService;

    /**
     * Displays the home page with a list of course models.
     *
     * @param model   the Spring MVC model for rendering the view
     * @param request the HTTP servlet request
     * @return the view name for the home page
     */
    @GetMapping("/")
    public String showHomePage(Model model, HttpServletRequest request) {
    

        try 
        {
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
        } catch (Exception e) 
        {
            // Handle the exception, e.g., log it or show a user-friendly error message
            e.printStackTrace(); // Example: print the stack trace
        }
    
        List<CourseModel> courses = service.getCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }
    
    /**
     * Retrieves a list of courses from a service and adds them to the model for display.
     *
     * @param model the model to which course data and title will be added
     * @return the name of the view to be rendered, in this case, "courses"
     */
    @GetMapping("/getcourses")
	public String getCourses(Model model)
	{
        // get all courses
		List<CourseModel> courses = service.getCourses();
        // pass courses in key value
        model.addAttribute("title", "List of Courses");
		model.addAttribute("courses", courses);
        // return courses
		return "courses";
	}

    /**
     * Displays the 
     * @param model Course Model for returned courses
     * @param id grabs the id to search from the url
     * @return courses html page
     */
    @GetMapping("/findcourse/{id}")
    public String getCourse(@PathVariable("id") int id, Model model)
    {
        // get model by id
        CourseModel course = service.getCourseById(id);

        // pass course in key value pairs
        model.addAttribute("title", "List of Courses");
		model.addAttribute("courses", course);
        // return
		return "courses";
    }

    /**
     * Displays the sign-up form view.
     * @param model the Spring MVC model for rendering the view
     * @return the view name for the sign-up page
     */
    @GetMapping("/signup")
    public String showSignUpPage(Model model) 
    {
        // Display Sign Up Form View
        model.addAttribute("title", "Sign Up Here!");
        model.addAttribute("signUpModel", new SignUpModel(null, null, null));
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
    public String doSignUp(@Valid SignUpModel signUpModel, BindingResult bindingResult, Model model) 
    {
        // check for validation errors 
        if(bindingResult.hasErrors())
        {
            model.addAttribute("title", "Sign Up Here!");
            return "signup";
        }

        // Utilize the Registration Service to initialize the user
        int initializationResult = rs.initializeUser(signUpModel);
        switch(initializationResult)
        {
            case 1:             // successful
                // Authenticate the user after successful sign-up
                return "redirect:/";
            case -1:            // same username
                model.addAttribute("title", "Username already taken!");
                return "signup";
            case -2:            // same email
                model.addAttribute("title", "Email already taken!");
                return "signup";
            default:
                model.addAttribute("title", "Error during sign-up!");
                return "signup";
        }        
    }

     /**
     * Displays the starter page for user sign-in.
     * @param model the Spring MVC model for rendering the view
     * @return the view name for the sign-in page
     */
    @GetMapping("/signIn")
    public String showStarterPage(Model model) {
        model.addAttribute("title", "RateMyCourse");
        return "signIn";
    }



    /**
     * Processes the creation of a new course.
     * @param courseModel      the course model containing the new course's data
     * @param bindingResult  the binding result for validation errors
     * @param model          the model to be populated with attributes
     * @return the view template to redirect to, either the createCourse template if there are errors or the home page
     */
    @PostMapping("/doCreateCourse")
    public String doCreateCourse(@Valid CourseModel courseModel, BindingResult bindingResult, Model model) 
    {
        if(bindingResult.hasErrors())
        {
            System.out.println("Validation errors:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("title", "Create Course Here!");
            return "createCourse";
        }        
        // Save the new course
        service.createCourse(courseModel);

        // Get all courses including the newly added one
        List<CourseModel> courses = service.getCourses();
        
        model.addAttribute("courses", courses);

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
        // Logic to retrieve the course details by id
        CourseModel course = service.getCourseById(id);
        model.addAttribute("courseModel", course);
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
        service.deleteCourse(id);
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
        CourseModel course = service.getCourseById(id);
        model.addAttribute("courseModel", course);
        return "updateCourse";
    }

    /**
     * Performs the update operation for a course.
     *
     * @param id     grabbing from the url path
     * @param courseModel     The updated course model.
     * @param bindingResult The result of the validation.
     * @param model         The model to which attributes will be updated.
     * @return Redirects to the home page after updating the course.
     */
    @PostMapping("/course/update/doUpdate/{id}")
    public String doUpdateCourse(@PathVariable int id, @Valid CourseModel courseModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("title", "Update Course Here!");
            return "updateCourse";
        }

        // updates course
        service.updateCourse(courseModel, id);

        // Get all courses including the updated one
        List<CourseModel> courses = service.getCourses();

        model.addAttribute("courses", courses);

        return "redirect:/";
    }
}   
