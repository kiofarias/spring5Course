package org.springframework.spring5webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.spring5webapp.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher,Long> {
}
