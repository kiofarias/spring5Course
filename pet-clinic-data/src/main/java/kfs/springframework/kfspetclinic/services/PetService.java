package kfs.springframework.kfspetclinic.services;

import kfs.springframework.kfspetclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
