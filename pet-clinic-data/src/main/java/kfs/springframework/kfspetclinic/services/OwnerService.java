package kfs.springframework.kfspetclinic.services;

import kfs.springframework.kfspetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner,Long>{

    Owner findByLastName(String lastName);

}
