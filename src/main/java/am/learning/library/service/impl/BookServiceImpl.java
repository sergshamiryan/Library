package am.learning.library.service.impl;

import am.learning.library.model.Book;
import am.learning.library.model.exceptions.AccessDeniedException;
import am.learning.library.model.exceptions.NotFoundException;
import am.learning.library.repositroy.BookRepository;
import am.learning.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookRepository bookRepository;


    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void takeBook(long bookId, long userId) throws NotFoundException, AccessDeniedException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Wrong book id"));
        AccessDeniedException.check(book.getTakerId() != null, "Book already taken");
        book.setTakerId(userId);
    }

    @Override
    @Transactional
    public void returnBook(long bookId, long userId) throws NotFoundException, AccessDeniedException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Wrong book id"));
        AccessDeniedException.check(book.getTakerId() == null, "Book already taken");
        AccessDeniedException.check(!book.getTakerId().equals(userId), "You cant return others books");
        book.setTakerId(null);
    }
}
