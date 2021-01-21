package am.learning.library.service;

import am.learning.library.model.Book;
import am.learning.library.model.exceptions.AccessDeniedException;
import am.learning.library.model.exceptions.NotFoundException;

public interface BookService {

    void save(Book book);

    void takeBook(long bookId, long userId) throws NotFoundException, AccessDeniedException;

    void returnBook(long bookId, long userId) throws NotFoundException, AccessDeniedException;
}
