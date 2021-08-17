package kfs.springframework.kfspetclinic.services.springdatajpa;

import kfs.springframework.kfspetclinic.model.Owner;
import kfs.springframework.kfspetclinic.repositories.OwnerRepository;
import kfs.springframework.kfspetclinic.repositories.PetRepository;
import kfs.springframework.kfspetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    private Owner returnOwner;
    private Set<Owner>  returnOwnersSet = new HashSet<>();

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
        returnOwnersSet.add(Owner.builder().id(1L).build());
        returnOwnersSet.add(Owner.builder().id(2L).build());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
        Owner ownerReturned = ownerSDJpaService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME,ownerReturned.getLastName());
        verify(ownerRepository,times(1)).findByLastName(any());
    }

    @Test
    void findAll() {
        when(ownerRepository.findAll()).thenReturn(returnOwnersSet);
        Set<Owner> ownersSetReturned = ownerSDJpaService.findAll();
        assertNotNull(ownersSetReturned);
        assertEquals(2, ownersSetReturned.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner ownerReturned= ownerSDJpaService.findById(1L);
        assertNotNull(ownerReturned);
        verify(ownerRepository,times(1)).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner ownerReturned= ownerSDJpaService.findById(1L);
        assertNull(ownerReturned);
        verify(ownerRepository,times(1)).findById(anyLong());
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner savedOwner = ownerSDJpaService.save(ownerToSave);
        assertNotNull(savedOwner);
        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository,times(1)).deleteById(anyLong());
    }


}