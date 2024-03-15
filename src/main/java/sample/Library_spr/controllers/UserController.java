package sample.Library_spr.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sample.Library_spr.models.User;
import sample.Library_spr.services.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Make a new user object available to the form
        return "register"; // Return the registration view
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user); // Register the user
        return "redirect:/login"; // Redirect to the login page after successful registration
    }

    // Add the login methods here
    @GetMapping("/loginIN")
    public String showLoginForm() {
        return "login"; // Возвращаем представление с формой входа
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        // Retrieve user from the database based on the provided username
        User user = userService.getUserByUsername(username);

        // Check if the user exists and the password matches
        if (user != null && user.getPassword().equals(password)) {
            // Add the authenticated user to the session
            session.setAttribute("authenticatedUser", user);
            model.addAttribute("user", user);
            return "profile"; // Redirect to the profile page upon successful login
        } else {
            model.addAttribute("error", "Invalid username or password"); // Add error message
            return "redirect:/login"; // Redirect back to login page in case of failure
        }
    }


    // Add the logout method here
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login"; // Redirect to the login page
    }

    @GetMapping("/user/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("authenticatedUser");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        model.addAttribute("user", user);
        return "profile"; // Return profile view
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("authenticatedUser");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        model.addAttribute("user", user);
        return "userInfo"; // Return edit profile view
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute("user") User updatedUser, HttpSession session) {
        User currentUser = (User) session.getAttribute("authenticatedUser");
        if (currentUser == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        // Update user information
        currentUser.setName(updatedUser.getName());
        currentUser.setSurname(updatedUser.getSurname());
        currentUser.setPhone(updatedUser.getPhone());
        currentUser.setEmail(updatedUser.getEmail());
        userService.save(currentUser); // Save updated user
        return "redirect:/profile"; // Redirect back to profile page
    }

}

