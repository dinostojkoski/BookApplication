package com.book.inventory.app.controller;

import com.book.inventory.app.model.Book;
import com.book.inventory.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/add-book")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/show-books";
    }

    @GetMapping("/show-books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "show-books";
    }

    @GetMapping("/edit-book/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "add-book";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/show-books";
    }

    // Search endpoint
    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        return "show-books"; // Or whatever page you want to display the results on
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the name of your login HTML file (without .html)
    }
}
