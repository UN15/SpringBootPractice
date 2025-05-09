package com.rookies3.myspringbootlab.Controller;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    private static Book getExistBook(Optional<Book> optionalBook) {
        Book existBook = optionalBook
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return existBook;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        ResponseEntity<Book> responseEntity = optionalBook
                .map(book -> ResponseEntity.ok(book))
                .orElse(new ResponseEntity("Book Not Found", HttpStatus.NOT_FOUND));
        return responseEntity;
    }

    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        Book existBook = getExistBook(optionalBook);
        return existBook;
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author){
        List<Book> authorBookList = bookRepository.findByAuthor(author);
        if(authorBookList.isEmpty()) {
            throw new BusinessException("Book Not Found", HttpStatus.NOT_FOUND);
        }
        return authorBookList;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Book newBook = bookRepository.save(book);
//        return ResponseEntity.ok(newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Book existBook = getExistBook(bookRepository.findById(id));
        existBook.setTitle(book.getTitle());
        existBook.setAuthor(book.getAuthor());
        Book updateBook = bookRepository.save(existBook);
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        Book existBook = getExistBook(bookRepository.findById(id));
        bookRepository.delete(existBook);
        return ResponseEntity.noContent().build();
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
//        if (!bookRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        bookRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}
