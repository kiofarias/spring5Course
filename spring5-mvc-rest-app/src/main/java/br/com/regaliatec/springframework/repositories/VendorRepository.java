package br.com.regaliatec.springframework.repositories;

import br.com.regaliatec.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
