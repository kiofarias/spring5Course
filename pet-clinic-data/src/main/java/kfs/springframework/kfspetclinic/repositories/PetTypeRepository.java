package kfs.springframework.kfspetclinic.repositories;

import kfs.springframework.kfspetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends CrudRepository<PetType,Long> {
}
