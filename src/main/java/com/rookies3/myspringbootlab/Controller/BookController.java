package com.rookies3.myspringbootlab.Controller;

import com.rookies3.myspringbootlab.Controller.dto.BookDTO;
import com.rookies3.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO.BookResponse>> getAllBooks() {
        List<BookDTO.BookResponse> bookResponseList = bookService.getAllBooks();
        if(bookResponseList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bookResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> getBookById(@PathVariable Long id){
        BookDTO.BookResponse book = bookService.getBookById(id);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getBookByIsbn(@PathVariable String isbn) {
        BookDTO.BookResponse book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO.BookResponse>> getBooksByAuthor(@PathVariable String author){
        List<BookDTO.BookResponse> authorBookList = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(authorBookList);
    }

    @PostMapping
    public ResponseEntity<BookDTO .BookResponse> createBook(@Valid @RequestBody
                                                                BookDTO.BookCreateRequest request){
        BookDTO.BookResponse book  = bookService.createBook(request);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody
                                            BookDTO.BookUpdateRequest request){
        BookDTO.BookResponse updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
