package com.sreenath.bookstore.service.bookservice;

import com.sreenath.bookstore.dto.BookDTO;
import com.sreenath.bookstore.exceptions.BookStoreCustomException;
import com.sreenath.bookstore.model.BookData;
import com.sreenath.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService{
    @Autowired
    private BookRepository bookRepository;

    /***
     * Implemented addBook method to add book
     * @param bookDTO - passing bookDTO param
     * @return
     */
    @Override
    public BookData addBook(BookDTO bookDTO) {
        BookData bookData = new BookData(bookDTO);
        return bookRepository.save(bookData);
    }

    /***
     * Implemented getBookList method to find all books
     * @return
     */
    @Override
    public List<BookData> getBookList() {
        return bookRepository.findAll();
    }

    /***
     * Implemented getBookById method to find book by id
     * @param bookId - passing bookId param
     * @return
     */
    @Override
    public BookData getBookById(int bookId) {
        return bookRepository.findById(bookId)
                             .orElseThrow(() -> new BookStoreCustomException("Book with id " + bookId + " not found"));
    }

    /***
     * Implemented getBookByAuthor method to find book by author
     * @param bookAuthor - passing bookAuthor param
     * @return
     */
    @Override
    public List<BookData> getBookByAuthor(String bookAuthor) {
        return bookRepository.findBookDataByBookAuthor(bookAuthor);
    }

    /***
     * Implemented sortBookAscendingOrder to sort books in ascending order by book name
     * @return
     */
    @Override
    public List<BookData> sortBookAscendingOrder() {
        return bookRepository.sortBookAscendingOrder();
    }

    /***
     * Implemented sortBookDescendingOrder to sort books in descending order by book name
     * @return
     */
    @Override
    public List<BookData> sortBookDescendingOrder() {
        return bookRepository.sortBookDescendingOrder();
    }

    /***
     * Implemented updateBookById method to update book by id
     * @param bookId - passing bookId param
     * @param bookDTO - passing bookDTO param
     * @return
     */
    @Override
    public BookData updateBookById(int bookId, BookDTO bookDTO) {
        BookData bookData = this.getBookById(bookId);
        bookData.updateBookData(bookDTO);
        return bookRepository.save(bookData);
    }

    /***
     * Implemented updateBookQuantity to update book quantity
     * @param bookId - passing bookId param
     * @param quantity - passing quantity param
     * @return
     */
    @Override
    public BookData updateBookQuantity(int bookId, int quantity) {
        BookData bookData = this.getBookById(bookId);
        int bookQuantity = bookData.getQuantityLeft() + quantity;
        bookData.setQuantityLeft(bookQuantity);
        return bookRepository.save(bookData);
    }

    /***
     * Implemented deleteBookById method to delete book by id
     * @param bookId - passing bookId param
     */
    @Override
    public void deleteBookById(int bookId) {
        BookData bookData = this.getBookById(bookId);
        bookRepository.delete(bookData);
    }
}
