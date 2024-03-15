package sample.Library_spr.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sample.Library_spr.models.Book;
import sample.Library_spr.models.User;
import sample.Library_spr.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private List<Book> books = new ArrayList<>();

    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public String books(Model model) {
        // Загружаем все книги из базы данных и передаем их в представление
        model.addAttribute("title", "Books");
        model.addAttribute("books", userService.getAllBooks()); // userService.getAllBooks() должен возвращать список всех книг
        return "books";
    }

    @GetMapping("/my_books")
    public String UserBook(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authenticatedUser");
        model.addAttribute("title", "My Books");
        model.addAttribute("userBooks", user.getMyBooks());
        return "my_books";
    }

    @PostMapping("/books")
    // Need a function, which will control authorisation of user, if he is not authorised, then he will be redirected to login page
    // if he is authorised, then book will be added to his list of books, and he will be redirected to the page with his books (my_books)

    public String addBook(@RequestParam String title, @RequestParam String description, @RequestParam String imageUrl,
                          @RequestParam String author, @RequestParam String dateFrom, @RequestParam String dateTo,
                          HttpSession session) {
        User user = (User) session.getAttribute("authenticatedUser");
        if (user == null) {
            return "redirect:/login";
        } else {
            // Create a new book and save it to the database
            Book book = new Book(title, description, imageUrl, author, dateFrom, dateTo);
            userService.saveBook(book);
            // Add the book to the user's list of books
            user.getMyBooks().add(book);
            userService.save(user);
            return "redirect:/my_books";
        }
    }

    // method for removing books from the list of user's books in my_books page
    @PostMapping("/remove_book")
    public String removeBook(@RequestParam Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("authenticatedUser");
        if (user == null) {
            return "redirect:/login";
        } else {
            // Remove the book from the user's list of books
            user.getMyBooks().removeIf(book -> book.getId().equals(bookId));
            userService.save(user);
            return "redirect:/my_books";
        }
    }
}
