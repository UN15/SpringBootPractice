package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    @Disabled
    @Rollback(value = false) //Rollback 처리하지 마세요!!
    void testCreateBook(){
        //도서 등록 테스트
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.of(2025, 5, 7));

        Book book2 = new Book();
        book2.setTitle("JPA 프로그래밍");
        book2.setAuthor("박둘리");
        book2.setIsbn("9788956746432");
        book2.setPrice(35000);
        book2.setPublishDate(LocalDate.of(2025, 4, 30));

        Book addbook = bookRepository.save(book);
        Book addbook2 = bookRepository.save(book2);
//        bookRepository.saveAll(List.of(book, book2)); 한번에 saveAll할 수 있음
        assertThat(addbook).isNotNull();
        assertThat(addbook2).isNotNull();
        assertThat(addbook.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(addbook2.getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    void testFindByIsbn(){
        //ISBN으로 도서 조회 테스트
        Book findIsbnBook = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(() -> new RuntimeException("Book Not Found"));
    }

    @Test
    void testFindByAuthor(){
        //저자명으로 도서 목록 조회 테스트
        List<Book> findAuthorBook = bookRepository.findByAuthor("홍길동");
        if (findAuthorBook.isEmpty()) {
            throw new RuntimeException("Book Not Found");
        }
    }

    @Test
    @Rollback(value = false) //Rollback 처리하지 마세요!!
    void testUpdateBook(){
        //도서 정보 수정 테스트
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(() -> new RuntimeException("Book not Found"));

        book.setPrice(25000);
        bookRepository.save(book);
        assertThat(book.getPrice()).isEqualTo(25000);
    }

    @Test
    @Rollback(value = false) //Rollback 처리하지 마세요!!
    void testDeleteBook(){
        //도서 삭제 테스트
        Book book = bookRepository.findByIsbn("9788956746432")
                .orElseThrow(() -> new RuntimeException("Book not Found"));

        bookRepository.delete(book);
    }
}
