package org.springframework.spring5webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.spring5webapp.domain.Author;

public interface AuthorRepository extends CrudRepository<Author,Long> {
}
