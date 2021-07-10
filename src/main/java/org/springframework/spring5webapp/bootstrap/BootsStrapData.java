package org.springframework.spring5webapp.bootstrap;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.spring5webapp.domain.Author;
import org.springframework.spring5webapp.domain.Book;
import org.springframework.spring5webapp.domain.Publisher;
import org.springframework.spring5webapp.repository.AuthorRepository;
import org.springframework.spring5webapp.repository.BookRepository;
import org.springframework.spring5webapp.repository.PublisherRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BootsStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Publisher publisher = new Publisher();
        publisher.setName("SFG Publishing");
        publisher.setCity("St Petersburg");
        publisher.setState("FL");

        publisherRepository.save(publisher);

        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");
        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123123");
        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("29618863");

        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(publisher);

        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        noEJB.setPublisher(publisher);

        authorRepository.save(eric);
        authorRepository.save(rod);
        bookRepository.save(ddd);
        bookRepository.save(noEJB);

        publisher.getBooks().add(ddd);
        publisher.getBooks().add(noEJB);
        publisherRepository.save(publisher);

        System.out.println("Number of books: "+bookRepository.count());
        System.out.println("Publisher count: "+ publisherRepository.count());
        System.out.println("Books in publisher: "+ publisher.getBooks().size());
    }
}
