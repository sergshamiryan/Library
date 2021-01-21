package am.learning.library.controller;


import am.learning.library.config.SecurityContextProvider;
import am.learning.library.model.Book;
import am.learning.library.model.User;
import am.learning.library.model.exceptions.AccessDeniedException;
import am.learning.library.model.exceptions.NotFoundException;
import am.learning.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private SecurityContextProvider securityContextProvider;

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity addBook(@RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity.ok(book);
    }


    @PostMapping("/{id}/take")
    public ResponseEntity takeBook(@PathVariable long id, OAuth2Authentication auth2Authentication) throws NotFoundException, AccessDeniedException {
        User user = securityContextProvider.getByAuthentication(auth2Authentication);
        bookService.takeBook(id,user.getId());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{id}/return")
    public ResponseEntity returnBook(@PathVariable long id, OAuth2Authentication auth2Authentication) throws NotFoundException, AccessDeniedException {
        User user = securityContextProvider.getByAuthentication(auth2Authentication);
        bookService.returnBook(id,user.getId());
        return ResponseEntity.ok().build();
    }
}
