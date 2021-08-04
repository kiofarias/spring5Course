package kfs.springframework.kfspetclinic.repositories;

import kfs.springframework.kfspetclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends CrudRepository<Speciality,Long> {
}
