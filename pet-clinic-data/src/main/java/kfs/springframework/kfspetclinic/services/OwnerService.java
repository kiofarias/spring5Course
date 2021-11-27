package kfs.springframework.kfspetclinic.services;

import kfs.springframework.kfspetclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner,Long>{

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);

}
