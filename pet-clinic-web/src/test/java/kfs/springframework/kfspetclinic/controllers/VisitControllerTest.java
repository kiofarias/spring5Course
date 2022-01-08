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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM="pets/createOrUpdateVisitForm";
    private static final String REDIRECT_OWNERS_1="redirect:/owners/{ownerId}";
    private static final String YET_ANOTHER_VISIT_DESCRIPTION="yet another visit";

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    private final UriTemplate visitUriTemplate= new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String,String> uriVariables = new HashMap<>();
    private URI visitUri;

    @BeforeEach
    void setUp() {
        Long petId = 1L;
        Long ownerId = 1L;
        when(petService.findById(anyLong()))
                .thenReturn(
                        Pet.builder()
                                .id(petId)
                                .birthDate(LocalDate.of(2022,1,8))
                                .name("Ddd").visits(new HashSet<>())
                                .owner(Owner.builder()
                                        .id(ownerId)
                                        .lastName("Farias")
                                        .firstName("Francisco")
                                        .build())
                                .petType(PetType.builder().name("Dog").build())
                                .build()
                );
        uriVariables.clear();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
        visitUri = visitUriTemplate.expand(uriVariables);
        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }


    @Test
    void initNewVisitForm() throws Exception{
        mockMvc.perform(get(visitUri))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));
        verify(petService,times(1)).findById(anyLong());
    }

    @Test
    void processNewVisitForm() throws Exception {
        mockMvc.perform(post(visitUri)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("date", "2022-01-08")
                        .param("description", YET_ANOTHER_VISIT_DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name(REDIRECT_OWNERS_1));
        verify(petService,times(1)).findById(anyLong());
        verify(visitService,times(1)).save(any());
    }
}