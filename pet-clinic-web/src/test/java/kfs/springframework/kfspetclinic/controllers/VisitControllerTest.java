package kfs.springframework.kfspetclinic.controllers;

import kfs.springframework.kfspetclinic.model.Owner;
import kfs.springframework.kfspetclinic.model.Pet;
import kfs.springframework.kfspetclinic.model.PetType;
import kfs.springframework.kfspetclinic.services.OwnerService;
import kfs.springframework.kfspetclinic.services.PetService;
import kfs.springframework.kfspetclinic.services.PetTypeService;
import kfs.springframework.kfspetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    Pet pet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        pet = Pet.builder().id(2L).build();
        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }


    @Test
    void initNewVisitForm() throws Exception{
        when(petService.findById(anyLong())).thenReturn(pet);
        mockMvc.perform(get("/owners/1/pets/2/visits/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
        verify(petService,times(1)).findById(anyLong());
        verifyNoInteractions(visitService);
    }

    @Test
    void processNewVisitForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);
        mockMvc.perform(post("/owners/1/pets/2/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name("redirect:/owners/1"));
        verify(petService,times(1)).findById(anyLong());
        verify(visitService,times(1)).save(any());
    }
}