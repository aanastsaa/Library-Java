package sample.Library_spr.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import sample.Library_spr.models.User;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        return "about";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        // 1Receive the authenticated user from the session
        User authentificatedUser = (User) session.getAttribute("authenticatedUser");

        if (authentificatedUser == null) {
            return "redirect:/login";
        }
        else {
            // If the user is authenticated, add the username to the model and return the profile view
            model.addAttribute("title", "Profile");
            model.addAttribute("username", authentificatedUser.getUsername()); // Add the username to the model
            return "profile";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        return "register";
    }

}
