package br.com.regaliatec.springframework.repositories;

import br.com.regaliatec.springframework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
