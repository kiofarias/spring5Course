package br.com.regaliatec.springframework.repositories;

import br.com.regaliatec.springframework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
