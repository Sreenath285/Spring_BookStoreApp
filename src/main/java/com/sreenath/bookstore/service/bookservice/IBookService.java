package com.sreenath.bookstore.service.bookservice;

import com.sreenath.bookstore.dto.BookDTO;
import com.sreenath.bookstore.model.BookData;

import java.awt.print.Book;
import java.util.List;

public interface IBookService {
    BookData addBook(BookDTO bookDTO);

    List<BookData> getBookList();

    BookData getBookById(int bookId);

    List<BookData> sortBookAscendingOrder();

    List<BookData> sortBookDescendingOrder();

    BookData updateBookById(int bookId, BookDTO bookDTO);

    BookData updateBookQuantity(int bookId, int bookQuantity);

    void deleteBookById(int bookId);
}