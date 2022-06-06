package com.sreenath.bookstore.controller;

import com.sreenath.bookstore.dto.BookDTO;
import com.sreenath.bookstore.dto.ResponseDTO;
import com.sreenath.bookstore.model.BookData;
import com.sreenath.bookstore.service.bookservice.IBookService;
import com.sreenath.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class BookRestController {
    @Autowired
    private IBookService iBookService;

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> getBooksList() {
        List<BookData> bookDataList = iBookService.getBookList();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get_by_id/{token}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        BookData bookData = iBookService.getBookById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Id", bookData, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get_by_author/{bookAuthor}")
    public ResponseEntity<ResponseDTO> getBookByAuthor(@PathVariable("bookAuthor") String bookAuthor) {
        List<BookData> bookDataList = iBookService.getBookByAuthor(bookAuthor);
        ResponseDTO responseDTO = new ResponseDTO("Getting books by author", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/order_ascend")
    public ResponseEntity<ResponseDTO> sortBookAscendingOrder() {
        List<BookData> bookDataList = iBookService.sortBookAscendingOrder();
        ResponseDTO responseDTO = new ResponseDTO("Getting books in ascending order", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/order_descend")
    public ResponseEntity<ResponseDTO> sortBookDescendingOrder() {
        List<BookData> bookDataList = iBookService.sortBookDescendingOrder();
        ResponseDTO responseDTO = new ResponseDTO("Getting books in descending order", bookDataList, null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/add_book")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookData bookData = iBookService.addBook(bookDTO);
        String token = tokenUtil.createToken(bookData.getBookId());
        ResponseDTO responseDTO = new ResponseDTO("Book added successfully", bookData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateBookById(@PathVariable("token") String token,
                                                      @RequestBody BookDTO bookDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        BookData bookData = iBookService.updateBookById(tokenId, bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated book for Id " + tokenId, bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseDTO> deleteBookById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        iBookService.deleteBookById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Delete call success for Id", "Deleted Id : " + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
