package kfs.springframework.kfspetclinic.repositories;

import kfs.springframework.kfspetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends CrudRepository<Vet,Long> {
}
