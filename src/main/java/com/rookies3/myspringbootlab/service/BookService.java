package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.Controller.dto.BookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO.BookResponse> getAllBooks(){
//        List<Book> findBook = bookRepository.findAll();
//        List<BookDTO.BookResponse> allBookList = new ArrayList<>();
//        for(Book book: findBook)
//            allBookList.add(BookDTO.BookResponse.from(book));
//
//        return allBookList;

//        return bookRepository.findAll().stream()
//                .map(book->BookDTO.BookResponse.from(book))
//                .toList();

        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .toList(); //Stream<BookResponse> => List<BookResponse>
    }

    public BookDTO.BookResponse getBookById(Long id){
        Book existBook = getExistBook(bookRepository.findById(id));
        return BookDTO.BookResponse.from(existBook);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book existBook = getExistBook(bookRepository.findByIsbn(isbn));
        return BookDTO.BookResponse.from(existBook);
    }

    public List<BookDTO.BookResponse> getBooksByAuthor(String author){
        List<Book> authorBookList = bookRepository.findByAuthor(author);
        if(authorBookList.isEmpty()) {
            throw new BusinessException("Book Not Found", HttpStatus.NOT_FOUND);
        }
//        List<BookDTO.BookResponse> bookList = new ArrayList<>();
//        for(Book book: authorBookList)
//            bookList.add(BookDTO.BookResponse.from(book));
//        return bookList;
        return bookRepository.findByAuthor(author).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    //등록
    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {

        //Isbn 중복 검사
        bookRepository.findByIsbn(request.getIsbn()) //Optional<Book>
                .ifPresent(book ->{
                    throw new BusinessException("Book with this Isbn already Exists", HttpStatus.CONFLICT);
                });
        //BookCreateRequest = >Entity 변환
        Book book = request.toEntity();
        //등록 처리
        Book savedBook = bookRepository.save(book);
        //Book =>BookResponse 변환
        return BookDTO.BookResponse.from(savedBook);
    }

    //수정
    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request){
        Book existingBook = getExistBook(bookRepository.findById(id));
        // 변경이 필요한 필드만 업데이트
        if (request.getPrice() != null) {
            existingBook.setPrice(request.getPrice());
        }

        // 확장성을 위한 추가 필드 업데이트
        if (request.getTitle() != null) {
            existingBook.setTitle(request.getTitle());
        }

        if (request.getAuthor() != null) {
            existingBook.setAuthor(request.getAuthor());
        }

        if (request.getPublishDate() != null) {
            existingBook.setPublishDate(request.getPublishDate());
        }

        Book updatedBook = bookRepository.save(existingBook);
        return BookDTO.BookResponse.from(updatedBook);
    }

    //삭제
    @Transactional
    public void deleteBook(Long id){
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("Book Not Found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
//        Book existBook = getExistBook(bookRepository.findById(id));
//        bookRepository.delete(existBook);
    }

    private static Book getExistBook(Optional<Book> optionalBook) {
        Book existBook = optionalBook
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return existBook;
    }
}
