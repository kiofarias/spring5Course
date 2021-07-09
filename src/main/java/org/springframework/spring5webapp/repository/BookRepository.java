package org.springframework.spring5webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.spring5webapp.domain.Book;

public interface BookRepository extends CrudRepository<Book,Long> {
}
