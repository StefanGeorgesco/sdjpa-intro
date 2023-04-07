package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by jt on 7/4/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Test
    void testMySQLOnBook() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

    }

    @Test
    void testMySQLOnAuthorUuid() {
        long countBefore = authorUuidRepository.count();
        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("John");
        authorUuid.setLastName("Smith");
        AuthorUuid savedAuthorUuid = authorUuidRepository.save(authorUuid);
        long countAfter = authorUuidRepository.count();
        assertThat(countAfter - countBefore).isEqualTo(1);
        UUID id = savedAuthorUuid.getId();
        AuthorUuid fetchedAuthorUuid = authorUuidRepository.findById(id).orElseThrow();
        assertThat(fetchedAuthorUuid.getId()).isEqualTo(id);
    }

    @Test
    void testMySQLOnBookUuid() {
        long countBefore = bookUuidRepository.count();
        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("Les Fleurs du Mal");
        bookUuid.setIsbn("123456789");
        bookUuid.setPublisher("Book Editions");
        BookUuid savedBookUuid = bookUuidRepository.save(bookUuid);
        long countAfter = bookUuidRepository.count();
        assertThat(countAfter - countBefore).isEqualTo(1);
        UUID id = savedBookUuid.getId();
        BookUuid fetchedBookUuid = bookUuidRepository.findById(id).orElseThrow();
        assertThat(fetchedBookUuid.getId()).isEqualTo(id);
    }
}


