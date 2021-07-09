package org.springframework.spring5webapp.bootstrap;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.spring5webapp.domain.Author;
import org.springframework.spring5webapp.domain.Book;
import org.springframework.spring5webapp.repository.AuthorRepository;
import org.springframework.spring5webapp.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BootsStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {

        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");
        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");
        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("29618863");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        authorRepository.save(rod);
        bookRepository.save(noEJB);

        eric.getBooks().add(noEJB);
        noEJB.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(noEJB);

        System.out.println("Started in Bootstrap");
        System.out.println("Number of books: "+bookRepository.count());
    }
}
